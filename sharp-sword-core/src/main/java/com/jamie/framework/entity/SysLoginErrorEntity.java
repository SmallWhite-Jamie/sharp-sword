package com.jamie.framework.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * SysLoginError 登录密码错误次数记录
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/10 19:15
 */
@TableName("sys_login_error")
@Data
public class SysLoginErrorEntity extends BaseEntity {
}
