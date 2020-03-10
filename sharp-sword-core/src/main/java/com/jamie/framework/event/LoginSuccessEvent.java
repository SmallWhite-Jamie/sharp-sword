package com.jamie.framework.event;

import com.jamie.framework.bean.SysUser;
import org.springframework.context.ApplicationEvent;

/**
 * 登录成功事件
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/10 19:28
 */
public class LoginSuccessEvent extends ApplicationEvent {
    public LoginSuccessEvent(SysUser source) {
        super(source);
    }
}