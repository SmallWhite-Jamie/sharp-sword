package com.sharp.sword.bean;

import com.sharp.sword.login.entity.SysUserEntity;
import lombok.Data;

import java.util.List;

/**
 * @author lizheng
 * @date: 15:36 2019/12/18
 * @Description: UserInfo
 */
@Data
public class UserInfo {
    private SysUserEntity sysUserEntity;
    private List<String> roles;
    private List<String> permissions;
}
