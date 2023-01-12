package com.course.booking.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.course.booking.common.entity.CacheConstants;
import com.course.booking.common.response.AjaxResult;
import com.course.booking.service.LoginService;
import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
public class LoginServiceImpl implements LoginService {


    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    /**
     * 获取验证码
     *
     * @return 验证码信息
     */
    public AjaxResult getCheckImage() {

        // 保存验证码信息
        String uuid = IdUtil.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        String capText = captchaProducerMath.createText();
        capStr = capText.substring(0, capText.lastIndexOf("@"));
        code = capText.substring(capText.lastIndexOf("@") + 1);
        image = captchaProducerMath.createImage(capStr);

//        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }
        return AjaxResult.success("获取验证码成功",Base64.encode(os.toByteArray()));
    }
}
