package com.jamie.framework.enums;

/**
 * LoginFailEnum
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/10 19:37
 */
public enum ErrorCode {
    // 登录相关--------------------------------------START
    /**
     * 用户不存在
     */
    USER_NOT_EXIST(1, "用户不存在"),

    /**
     * 密码错误
     */
    PASSWORD_ERROR(2, "密码错误"),

    /**
     * 验证码错误
     */
    CAPTCHA_ERROR(3, "验证码错误"),

    /**
     * 登录 Salt 值错误
     */
    SALT_ERROR(4, "登录 Salt 值错误"),

    /**
     * 登录密码错误次数过多
     */
    PW_ERRPR_TOO_MANY(5, "登录密码错误次数过多");
    // 登录相关--------------------------------------END

    ErrorCode(int type, String msg) {
        this.type = type;
        this.msg = msg;
    }
    private final int type;
    private final String msg;

    public int getType() {
        return type;
    }


    public String getMsg() {
        return msg;
    }
}
