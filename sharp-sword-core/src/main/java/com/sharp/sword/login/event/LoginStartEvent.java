package com.sharp.sword.login.event;

import com.sharp.sword.login.vo.SysUserEntityVO;
import org.springframework.context.ApplicationEvent;

/**
 * LoginStartEvent
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/13 16:21
 */
public class LoginStartEvent extends ApplicationEvent {

    public LoginStartEvent(SysUserEntityVO source) {
        super(source);
    }

    @Override
    public SysUserEntityVO getSource() {
        return (SysUserEntityVO) super.getSource();
    }
}
