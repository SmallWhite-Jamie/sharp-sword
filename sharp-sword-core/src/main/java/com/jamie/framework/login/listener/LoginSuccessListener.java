package com.jamie.framework.login.listener;

import com.jamie.framework.login.LoginValidatorProperties;
import com.jamie.framework.login.event.LoginSuccessEvent;
import com.jamie.framework.login.service.SysLoginErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * LoginSuccessListener
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/10 20:11
 */
@Component
public class LoginSuccessListener implements ApplicationListener<LoginSuccessEvent> {

    @Autowired
    private SysLoginErrorService loginErrorService;

    @Autowired
    private LoginValidatorProperties loginValidatorProperties;

    @Override
    public void onApplicationEvent(LoginSuccessEvent event) {
        if (loginValidatorProperties.isVerifyPasswordErrorNum()) {
            loginErrorService.deleteLogicByUserId(event.getSource().getUsername());
        }
    }
}
