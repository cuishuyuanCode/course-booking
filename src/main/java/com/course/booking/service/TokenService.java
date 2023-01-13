package com.course.booking.service;

import com.course.booking.common.entity.user.LoginUser;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

    LoginUser getLoginUser(HttpServletRequest request);

    void deleteLoginUserFromRedis(String token);

    void checkToken(LoginUser loginUser);

    String createToken(LoginUser loginUser);
}
