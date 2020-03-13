package com.jamie.framework.login.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jamie.framework.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * SysLoginError 登录密码错误次数记录
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/10 19:15
 */
@TableName("sys_login_error")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLoginErrorEntity extends BaseEntity {
    private String userId;
    private Date loginTime;
}
