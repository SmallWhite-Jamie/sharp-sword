package com.jamie.framework.login.listener;

import com.jamie.framework.enums.ErrorCode;
import com.jamie.framework.exception.LoginException;
import com.jamie.framework.login.LoginValidatorProperties;
import com.jamie.framework.login.event.LoginStartEvent;
import com.jamie.framework.login.service.SysLoginErrorService;
import com.jamie.framework.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * LoginStartListener
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/13 16:32
 */
@Component
@Order(1)
public class LoginStartListener implements ApplicationListener<LoginStartEvent> {

    @Autowired
    private RedisService redisService;

    @Autowired
    private LoginValidatorProperties properties;

    @Autowired
    private SysLoginErrorService sysLoginErrorService;

    @Override
    public void onApplicationEvent(LoginStartEvent event) {
        // 校验验证码
        if (properties.isCaptcha()) {
            String code = redisService.getStr(event.getSource().getUserid());
            if (code == null || !code.equals(event.getSource().getCaptchaCode())) {
                throw new LoginException(ErrorCode.CAPTCHA_ERROR.getMsg());
            }
        }
        // 验证密码出错次数
        if (!sysLoginErrorService.validateLoginErrorNum(event.getSource().getUserid())) {
            throw new LoginException(ErrorCode.PW_ERRPR_TOO_MANY.getMsg());
        }
    }
}
