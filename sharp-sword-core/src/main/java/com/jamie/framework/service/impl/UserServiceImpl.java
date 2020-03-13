package com.jamie.framework.service.impl;

import com.jamie.framework.bean.SysPermission;
import com.jamie.framework.bean.SysRoles;
import com.jamie.framework.login.entity.SysUserEntity;
import com.jamie.framework.bean.User;
import com.jamie.framework.bean.UserInfo;
import com.jamie.framework.datasource.annotation.TargetDataSource;
import com.jamie.framework.idgenerator.IdGenerator;
import com.jamie.framework.mapper.UserMapper;
import com.jamie.framework.service.PermissionService;
import com.jamie.framework.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jamie.li
 */
@Service
public class UserServiceImpl implements UserServiceI {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private AppBaseService appBaseService;

    @Autowired
    private PermissionService permissionService;

    @Cacheable(value = "user", key = "'User:'+#id")
    @Override
    public User getUserById(String id) {
        User user = userMapper.findById(id);
        return user;
    }

    @TargetDataSource("slave1")
    @Override
    public List<User> getUserByName(String name) {
        List<User> users = userMapper.findByName("%"+name+"%");
        return users;
    }

//    @CachePut(value = "user", key = "'User:'+#user.id")
    @Override
    public User save(User user) {
        user.setId(idGenerator.nextIdStr());
        userMapper.insert(user);
        return user;
    }

    @Override
    public UserInfo getUserInfo() {
        SysUserEntity user = appBaseService.getUserInfo();
        List<SysRoles> roles = permissionService.getRolesByUserId(user.getUserid());
        List<SysPermission> permissions = permissionService.getSysPermissionByUserId(user.getUserid());
        UserInfo userInfo = new UserInfo();
        userInfo.setSysUserEntity(user);
        userInfo.setPermissions(permissions.stream().map(SysPermission::getCode).collect(Collectors.toList()));
        userInfo.setRoles(roles.stream().map(SysRoles::getRoleCode).collect(Collectors.toList()));
        return userInfo;
    }
}
