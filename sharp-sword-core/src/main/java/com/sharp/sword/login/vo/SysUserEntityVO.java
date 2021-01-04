package com.sharp.sword.login.vo;

import com.sharp.sword.login.entity.SysUserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SysUserEntityVO
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/3/13 16:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserEntityVO extends SysUserEntity {
    private String captchaCode;
}
