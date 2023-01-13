package com.course.booking.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.course.booking.common.entity.CacheConstants;

import com.course.booking.common.entity.user.LoginUser;
import com.course.booking.common.response.Result;
import com.course.booking.common.utils.RedisUtils;
import com.course.booking.controller.dto.LoginDTO;
import com.course.booking.controller.vo.CheckImageVO;
import com.course.booking.controller.vo.LoginVO;
import com.course.booking.service.LoginService;
import com.google.code.kaptcha.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {


    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisUtils redisUtils;

    @Resource
    private AuthenticationManager authenticationManager;


    /**
     * 存入验证码缓存，用于登录时的校验判断
     */
    private CheckImageVO setCheckImageCache() {
        return null;
    }

    /**
     * 获取验证码
     *
     * @return 验证码信息
     */
    public Result<CheckImageVO> getCheckImage() {
        logger.info("验证码信息获取开始...");
        CheckImageVO checkImageVO = new CheckImageVO();
        // 保存验证码信息
        String uuid = IdUtil.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        String capText = captchaProducerMath.createText();
        capStr = capText.substring(0, capText.lastIndexOf("@"));
        code = capText.substring(capText.lastIndexOf("@") + 1);
        image = captchaProducerMath.createImage(capStr);

        redisUtils.setCacheObject(verifyKey, code, 2, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            logger.error("获取验证码信息异常:{}",e.getMessage());
            return Result.failure("获取验证码信息失败");
        }
        checkImageVO.setUuid(uuid);
        checkImageVO.setImageBase64Url(Base64.encode(os.toByteArray()));
        return Result.success("获取验证码成功", checkImageVO);
    }

    @Override
    public Result<LoginVO> login(LoginDTO loginDTO) {
        logger.info("开始登录...");
        //校验验证码
        boolean passFlag = checkImage(loginDTO);
        if (passFlag){
            // 用户验证
            Authentication authentication = null;
            try {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
//                AuthenticationContextHolder.setContext(authenticationToken);
                authentication = authenticationManager.authenticate(authenticationToken);
            }catch (Exception e){
                return Result.failure("登录发生异常:"+e.getMessage());
            }
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        }
        return Result.failure("请输入正确的验证码");
    }


    private boolean checkImage(LoginDTO loginDTO) {
        if (StringUtils.isEmpty(loginDTO.getUuid())){
            return false;
        }
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + loginDTO.getUuid();
        Object captcha = redisUtils.getCacheObject(verifyKey);
        redisUtils.deleteObject(verifyKey);
        return captcha != null;
    }
}
