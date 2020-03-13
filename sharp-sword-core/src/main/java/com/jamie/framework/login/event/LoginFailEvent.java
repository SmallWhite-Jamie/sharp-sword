package com.jamie.framework.login.event;

import com.jamie.framework.login.LoginFailDataBean;
import org.springframework.context.ApplicationEvent;

/**
 * 登录失败事件
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/10 19:28
 */
public class LoginFailEvent extends ApplicationEvent {
    public LoginFailEvent(LoginFailDataBean source) {
        super(source);
    }

    @Override
    public LoginFailDataBean getSource() {
        return (LoginFailDataBean) super.getSource();
    }
}
