package com.sharp.sword.service.impl;

import com.sharp.sword.bean.SysPermission;
import com.sharp.sword.bean.SysRoles;
import com.sharp.sword.cache.annotation.Cacheable;
import com.sharp.sword.constant.RedisConstant;
import com.sharp.sword.mapper.PermissionMapper;
import com.sharp.sword.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lizheng
 * @date: 16:06 2019/10/21
 * @Description: PermissionServiceImpl
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    @Cacheable(key = "#userId", prefix = RedisConstant.USER_PERMISSION_KEY, useSysTokenTimeout = true)
    public List<SysPermission> getSysPermissionByUserId(String userId) {
        return permissionMapper.getPermissionByUserId(userId);
    }

    @Override
    @Cacheable(key = "#userId", prefix = RedisConstant.USER_ROLE_KEY, useSysTokenTimeout = true)
    public List<SysRoles> getRolesByUserId(String userId) {
        return permissionMapper.getRolesByUserId(userId);
    }
}
