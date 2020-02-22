package com.jamie.framework.service.impl;

import com.jamie.framework.bean.SysPermission;
import com.jamie.framework.bean.SysRoles;
import com.jamie.framework.conf.AppProperties;
import com.jamie.framework.constant.RedisConstant;
import com.jamie.framework.jwt.JwtProperties;
import com.jamie.framework.mapper.PermissionMapper;
import com.jamie.framework.service.PermissionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lizheng
 * @date: 16:06 2019/10/21
 * @Description: PermissionServiceImpl
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public List<SysPermission> getSysPermissionByUserId(String userId) {
        List<SysPermission> permissions = (List<SysPermission>) redisTemplate.opsForValue().get(RedisConstant.USER_PERMISSION_KEY + appProperties.getKey() + userId);
        if (CollectionUtils.isEmpty(permissions)) {
            permissions = permissionMapper.getPermissionByUserId(userId);
            if (CollectionUtils.isNotEmpty(permissions)) {
                redisTemplate.opsForValue().set(
                        RedisConstant.USER_PERMISSION_KEY + appProperties.getKey() + userId,
                        permissions,
                        jwtProperties.getExpireSecond(),
                        TimeUnit.SECONDS);
            } else {
                permissions = new ArrayList<>();
            }

        }
        return permissions;
    }

    @Override
    public List<SysRoles> getRolesByUserId(String userId) {
        List<SysRoles> roles = (List<SysRoles>) redisTemplate.opsForValue().get(RedisConstant.USER_ROLE_KEY + appProperties.getKey() + userId);
        if (CollectionUtils.isEmpty(roles)) {
            roles = permissionMapper.getRolesByUserId(userId);
            if (CollectionUtils.isNotEmpty(roles)) {
                redisTemplate.opsForValue().set(
                        RedisConstant.USER_ROLE_KEY + appProperties.getKey() + userId,
                        roles,
                        jwtProperties.getExpireSecond(), TimeUnit.SECONDS);
            } else {
                roles = new ArrayList<>();
            }
        }
        return roles;
    }
}
