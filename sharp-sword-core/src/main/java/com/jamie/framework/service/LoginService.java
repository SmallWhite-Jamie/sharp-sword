package com.jamie.framework.service;


import com.jamie.framework.login.vo.SysUserEntityVO;
import com.jamie.framework.util.api.ApiResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lizheng
 * @date: 9:51 2019/10/14
 * @Description: LoginService
 */
public interface LoginService {
    ApiResult login(SysUserEntityVO userEntityVO, HttpServletResponse httpServletResponse);

    String getLoginRandomSalt(String userid);

    void logout(HttpServletRequest req, HttpServletResponse res);
}
