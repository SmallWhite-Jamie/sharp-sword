package com.sharp.sword.service;

import com.sharp.sword.bean.User;
import com.sharp.sword.bean.UserInfo;

import java.util.List;

public interface UserServiceI {

    User getUserById(String id);

    List<User> getUserByName(String name);

//    PageInfo<User> getAllUser(int pageNum, int  pageSize);

    User save(User user);

    UserInfo getUserInfo();
}
