package com.sharp.sword.mapper;

import com.sharp.sword.bean.SysPermission;
import com.sharp.sword.bean.SysRoles;

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
