package com.jamie.framework.service;

import com.jamie.framework.bean.SysPermission;
import com.jamie.framework.bean.SysRoles;

import java.util.List;

/**
 * @author lizheng
 * @date: 16:06 2019/10/21
 * @Description: PermissionService
 */
public interface PermissionService {
    List<SysPermission> getSysPermissionByUserId(String userId);
    List<SysRoles> getRolesByUserId(String userId);
}
