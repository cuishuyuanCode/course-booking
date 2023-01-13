package com.course.booking.service.impl;

import cn.hutool.core.util.IdUtil;
import com.course.booking.common.entity.security.SecurityConstants;
import com.course.booking.common.entity.user.LoginUser;
import com.course.booking.common.utils.RedisUtils;
import com.course.booking.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {


    protected static final long MILLIS_SECOND = 1000;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    protected Integer expireTime = 30;

    @Resource
    private RedisUtils redisUtils;


    @Override
    public LoginUser getLoginUser(HttpServletRequest request) {
        //获取token信息
        String token = request.getHeader(SecurityConstants.header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
            if (StringUtils.isNotEmpty(token)) {
                Claims claims = parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(SecurityConstants.LOGIN_USER_KEY);
                String userKey = SecurityConstants.LOGIN_USER_KEY + uuid;
                return redisUtils.getCacheObject(userKey);
            }
        }
        return null;
    }


    /**
     * 从缓存中删除用户信息
     *
     * @param token
     */
    @Override
    public void deleteLoginUserFromRedis(String token) {
        redisUtils.deleteObject(SecurityConstants.LOGIN_TOKEN_KEY + token);
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    public void checkToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }


    @Override
    public String createToken(LoginUser loginUser) {
        String token = IdUtil.fastSimpleUUID();
        loginUser.setToken(token);
        refreshToken(loginUser);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(SecurityConstants.LOGIN_USER_KEY, token);
        return Jwts.builder()
                .setClaims(map)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECURITY_KEY).compact();
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = SecurityConstants.LOGIN_TOKEN_KEY + loginUser.getToken();
        redisUtils.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SecurityConstants.TOKEN_SECURITY_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
