package com.sharp.sword.login;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * LoginValidatorProperties
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/13 15:19
 */
@Component
@ConfigurationProperties(prefix = "login.validator")
@Data
public class LoginValidatorProperties {
    /**
     * 验证密码出错次数，此处为true，interval、num、time 才生效
     */
    private boolean verifyPasswordErrorNum = false;

    /**
     * 时间间隔，例如：300 秒内出错不超过 5 次
     */
    private long interval = 60 * 5;

    /**
     * 错误次数
     */
    private int num = 5;

    /**
     * 恢复可登录状态时间秒
     */
    private long time = 60 * 10;

    /**
     * 登录是否需要验证码
     */
    private boolean captcha = false;

}
