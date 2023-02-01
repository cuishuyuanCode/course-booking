package com.course.booking.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import com.course.booking.common.entity.CacheConstants;
import com.course.booking.common.entity.user.LoginUser;
import com.course.booking.common.response.Result;
import com.course.booking.common.utils.RedisUtils;
import com.course.booking.controller.dto.LoginDTO;
import com.course.booking.controller.dto.RegisterDTO;
import com.course.booking.controller.vo.CheckImageVO;
import com.course.booking.controller.vo.LoginVO;
import com.course.booking.dao.LoginMapper;
import com.course.booking.service.LoginService;
import com.course.booking.service.TokenService;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;


import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {


    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisUtils redisUtils;

    @Resource
    private LoginMapper loginMapper;


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
            logger.error("获取验证码信息异常:{}", e.getMessage());
            return Result.failure("获取验证码信息失败");
        }
        checkImageVO.setUuid(uuid);
        checkImageVO.setImageBase64Url(Base64.encode(os.toByteArray()));
        return Result.success("获取验证码成功", checkImageVO);
    }

    @Override
    public Result<LoginVO> login(LoginDTO loginDTO) {
        logger.info("开始登录...");
        LoginVO loginVO = null;
        //校验验证码
        boolean passFlag = checkImage(loginDTO);
        if (passFlag) {
            Boolean userPass = loginMapper.loginCheck(loginDTO);
            if (userPass) {
                loginVO = new LoginVO();
                loginVO.setLoginFlag(true);
                return Result.success(loginVO);
            } else {
                return Result.failure("请检查用户名和密码");
            }
        }
        return Result.failure("请输入正确的验证码");
    }

    @Override
    public Result<Boolean> register(RegisterDTO registerDTO) {
        logger.info("开始注册...{}", JSONObject.toJSONString(registerDTO));
        if (registerDTO == null){
            return Result.failure("请检查传入信息");
        }
        if (StringUtils.isEmpty(registerDTO.getUsername()) || StringUtils.isEmpty(registerDTO.getPassword())) {
            return Result.failure("请检查传入参数");
        }
        //1.查询用户名是否已经存在
        Boolean existFlag = loginMapper.selectUser(registerDTO.getUsername());
        if (existFlag){
            return Result.failure("该用户已被注册，请更换用户名");
        }
        try {
            loginMapper.insertUser(registerDTO);
        }catch (Exception e){
            logger.error("注册失败");
            return Result.failure("注册失败，请稍后再试");
        }
        return Result.success(true);
    }


    private boolean checkImage(LoginDTO loginDTO) {
        if (StringUtils.isEmpty(loginDTO.getUuid())) {
            return false;
        }
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + loginDTO.getUuid();
        Object captcha = redisUtils.getCacheObject(verifyKey);
        redisUtils.deleteObject(verifyKey);
        return captcha != null;
    }
}
