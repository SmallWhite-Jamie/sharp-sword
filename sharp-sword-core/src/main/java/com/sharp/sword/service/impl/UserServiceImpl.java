package com.sharp.sword.service.impl;

import com.sharp.sword.bean.SysPermission;
import com.sharp.sword.bean.SysRoles;
import com.sharp.sword.login.entity.SysUserEntity;
import com.sharp.sword.bean.User;
import com.sharp.sword.bean.UserInfo;
import com.sharp.sword.datasource.annotation.TargetDataSource;
import com.sharp.sword.idgenerator.IdGenerator;
import com.sharp.sword.mapper.UserMapper;
import com.sharp.sword.service.PermissionService;
import com.sharp.sword.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("redisIdGenerator")
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
