package com.course.booking.common.entity.security;


public class SecurityConstants {

    public static final String header = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    /**
     * 令牌密钥
     */
    public static final String TOKEN_SECURITY_KEY = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";


}
