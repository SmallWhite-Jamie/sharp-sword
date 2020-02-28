package com.jamie.framework.mapper;

import com.jamie.framework.bean.SysPermission;
import com.jamie.framework.bean.SysRoles;

import java.util.List;

/**
 * @author lizheng
 * @date: 16:12 2019/10/21
 * @Description: PermissionMapper
 */
public interface PermissionMapper {
    List<SysPermission> getPermissionByUserId(String id);

    List<SysRoles> getRolesByUserId(String id);
}
