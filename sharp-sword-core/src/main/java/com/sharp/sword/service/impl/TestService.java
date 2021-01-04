package com.sharp.sword.service.impl;

import com.sharp.sword.bean.User;
import com.sharp.sword.datasource.annotation.TargetDataSource;
import com.sharp.sword.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lizheng
 * @date: 9:57 2020/02/04
 * @Description: TestService
 */
@Service
@TargetDataSource("slave1")
public class TestService {

    @Autowired
    private UserMapper userMapper;

    @TargetDataSource("master")
    public List<User> test1() {
        return userMapper.findAll();
    }

    public List<User> test2() {
        return userMapper.findAll();
    }
}
