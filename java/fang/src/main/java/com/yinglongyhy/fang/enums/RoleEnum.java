package com.yinglongyhy.fang.enums;

public enum RoleEnum {
    /**
     * 管理员权限, 用户权限
     */
    ADMIN("admin"), USER("user");

    /**
     * 权限字符串
     */
    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return this.role;
    }

}
