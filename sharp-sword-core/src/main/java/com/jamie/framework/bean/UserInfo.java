package com.jamie.framework.bean;

import lombok.Data;

import java.util.List;

/**
 * @author lizheng
 * @date: 15:36 2019/12/18
 * @Description: UserInfo
 */
@Data
public class UserInfo {
    private SysUser sysUser;
    private List<String> roles;
    private List<String> permissions;
}
