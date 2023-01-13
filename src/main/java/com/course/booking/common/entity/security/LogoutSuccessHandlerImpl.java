package com.course.booking.common.entity.security;

import com.alibaba.fastjson2.JSONObject;
import com.course.booking.common.entity.user.LoginUser;
import com.course.booking.common.response.Result;
import com.course.booking.common.utils.RespUtils;
import com.course.booking.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {


    @Resource
    private TokenService tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        /**
         * 推出成功，删除token
         */
        LoginUser user = tokenService.getLoginUser(httpServletRequest);
        if (user != null){
            //删除token信息和缓存信息
            if (StringUtils.isNotEmpty(user.getToken())){
                tokenService.deleteLoginUserFromRedis(user.getToken());
            }
        }
        RespUtils.resp(httpServletResponse, JSONObject.toJSONString(Result.success("退出登录成功")));
    }
}
