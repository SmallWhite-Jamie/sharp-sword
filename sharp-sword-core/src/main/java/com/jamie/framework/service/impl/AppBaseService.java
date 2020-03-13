package com.jamie.framework.service.impl;

import com.jamie.framework.login.entity.SysUserEntity;
import com.jamie.framework.constant.RedisConstant;
import com.jamie.framework.jwt.JWTUtil;
import com.jamie.framework.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lizheng
 * @date: 15:28 2019/10/14
 * @Description: AppBaseService
 */
@Service
public class AppBaseService {

    @Autowired
    private RedisService redisService;

    public String getUserId() {
        return JWTUtil.getJwtUsername(getToken());
    }

    public String getToken() {
        if (RequestContextHolder.getRequestAttributes() != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return JWTUtil.getTokenFromRequest(request);
        }
        return null;
    }

    public SysUserEntity getUserInfo() {
        String userId = getUserId();
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        return (SysUserEntity) redisService.getObj(RedisConstant.USER_INFO_KEY + userId);
    }

}
