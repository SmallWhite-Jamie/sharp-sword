package com.sharp.sword.login.event;

import com.sharp.sword.login.entity.SysUserEntity;
import org.springframework.context.ApplicationEvent;

/**
 * 登录成功事件
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/10 19:28
 */
public class LoginSuccessEvent extends ApplicationEvent {
    public LoginSuccessEvent(SysUserEntity source) {
        super(source);
    }

    @Override
    public SysUserEntity getSource() {
        return (SysUserEntity) super.getSource();
    }
}
