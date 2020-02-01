package com.jamie.framework.service.impl;

import com.jamie.framework.bean.SysUser;
import com.jamie.framework.conf.AppProperties;
import com.jamie.framework.constant.RedisConstant;
import com.jamie.framework.jwt.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lizheng
 * @date: 15:28 2019/10/14
 * @Description: AppBaseService
 */
@Service
public class AppBaseService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private AppProperties appProperties;

    public String getUserId() {
        return JWTUtil.getJwtUsername(getToken());
    }

    public String getToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return JWTUtil.getTokenFromRequest(request);
    }

    public SysUser getUserInfo() {
        String userId = getUserId();
        if (StringUtils.isBlank(userId)) {
            return null;
        }
        return (SysUser) redisTemplate.opsForValue().get(RedisConstant.USER_INFO_KEY + appProperties.getKey() + userId);
    }

}
