package com.jamie.framework.service;

import com.jamie.framework.bean.User;
import com.jamie.framework.bean.UserInfo;

import java.util.List;

public interface UserServiceI {

    User getUserById(String id);

    List<User> getUserByName(String name);

//    PageInfo<User> getAllUser(int pageNum, int  pageSize);

    User save(User user);

    UserInfo getUserInfo();
}
