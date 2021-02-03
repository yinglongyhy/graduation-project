package com.yinglongyhy.fang.threadlocal;

import com.yinglongyhy.fang.entity.LoginToken;

public class TokenThreadLocal {
    private static final ThreadLocal<LoginToken> CURRENT_LOGIN_TOKEN_INFO = new ThreadLocal<>();

    public static void set(LoginToken loginToken) {
        CURRENT_LOGIN_TOKEN_INFO.set(loginToken);
    }

    public static LoginToken get() {
        return CURRENT_LOGIN_TOKEN_INFO.get();
    }

    public static void remove() {
        CURRENT_LOGIN_TOKEN_INFO.remove();
    }
}
