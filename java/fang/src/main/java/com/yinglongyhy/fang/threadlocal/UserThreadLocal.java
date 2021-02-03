package com.yinglongyhy.fang.threadlocal;

import com.yinglongyhy.fang.entity.User;

public class UserThreadLocal {
    private static final ThreadLocal<User> CURRENT_USER_INFO = new ThreadLocal<>();

    public static void set(User user) {
        CURRENT_USER_INFO.set(user);
    }

    public static User get() {
        return CURRENT_USER_INFO.get();
    }

    public static void remove() {
        CURRENT_USER_INFO.remove();
    }

}
