package com.jamie.framework.login.controller;

import com.jamie.framework.login.vo.SysUserEntityVO;
import com.jamie.framework.login.service.LoginService;
import com.jamie.framework.util.api.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lizheng
 * @date: 9:04 2019/10/12
 * @Description: LoginController
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;


    @RequestMapping("/login")
    public ApiResult login(SysUserEntityVO userEntityVO, HttpServletResponse httpServletResponse) {
        return loginService.login(userEntityVO, httpServletResponse);
    }

    @RequestMapping("/getLoginSalt")
    public ApiResult getLoginSalt(String username) {
        String salt = loginService.getLoginRandomSalt(username);
        return ApiResult.ok(salt);
    }

    @RequestMapping("/logout")
    public ApiResult logout(HttpServletRequest req, HttpServletResponse res) {
        loginService.logout(req, res);
        return ApiResult.ok();
    }

}
