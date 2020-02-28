package com.jamie.framework.service.impl;

import com.jamie.framework.bean.SysPermission;
import com.jamie.framework.bean.SysRoles;
import com.jamie.framework.cache.annotation.Cacheable;
import com.jamie.framework.constant.RedisConstant;
import com.jamie.framework.mapper.PermissionMapper;
import com.jamie.framework.service.PermissionService;
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
