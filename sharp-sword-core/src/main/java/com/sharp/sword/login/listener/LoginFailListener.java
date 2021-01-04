package com.sharp.sword.login.listener;

import cn.hutool.core.util.ObjectUtil;
import com.sharp.sword.login.LoginValidatorProperties;
import com.sharp.sword.login.entity.SysLoginErrorEntity;
import com.sharp.sword.login.event.LoginFailEvent;
import com.sharp.sword.login.LoginFailDataBean;
import com.sharp.sword.login.service.SysLoginErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * LoginFailListener
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/10 20:15
 */
@Component
public class LoginFailListener implements ApplicationListener<LoginFailEvent> {

    @Autowired
    private SysLoginErrorService loginErrorService;

    @Autowired
    private LoginValidatorProperties loginValidatorProperties;

    @Override
    public void onApplicationEvent(LoginFailEvent event) {
        LoginFailDataBean source = event.getSource();
        switch (source.getFailEnum()) {
            case SALT_ERROR: {

                break;
            }
            case CAPTCHA_ERROR: {

                break;
            }
            case PASSWORD_ERROR: {
                if (loginValidatorProperties.isVerifyPasswordErrorNum()) {
                    SysLoginErrorEntity errorEntity = new SysLoginErrorEntity();
                    errorEntity.setUserId(ObjectUtil.toString(source.getData()));
                    errorEntity.setLoginTime(new Date());
                    loginErrorService.save(errorEntity);
                }
                break;
            }
            case USER_NOT_EXIST: {

                break;
            }

            case PW_ERRPR_TOO_MANY: {

                break;
            }
            default: {

            }
        }
    }
}
