package com.jamie.framework.controller;

import com.jamie.framework.bean.User;
import com.jamie.framework.bean.UserInfo;
import com.jamie.framework.service.UserServiceI;
import com.jamie.framework.util.api.ApiResult;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author jamie.li
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceI userServiceI;

    @RequestMapping("/info")
    public ApiResult userInfo() {
        UserInfo userInfo = userServiceI.getUserInfo();
        return ApiResult.ok(userInfo);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userServiceI.getUserById(id);
    }

    @GetMapping("/name/{name}")
    public List<User> getUserByName(@PathVariable String name) {
        return userServiceI.getUserByName(name);
    }

    @PostMapping("/save")
    @RequiresRoles({"admin"})
    public User save(User user) {
       return userServiceI.save(user);
    }

}
