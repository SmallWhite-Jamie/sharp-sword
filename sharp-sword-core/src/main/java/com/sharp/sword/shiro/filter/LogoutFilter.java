package com.sharp.sword.shiro.filter;

import com.sharp.sword.constant.RedisConstant;
import com.sharp.sword.jwt.JWTUtil;
import com.sharp.sword.jwt.JwtProperties;
import com.sharp.sword.jwt.VerifyResult;
import com.sharp.sword.redis.RedisService;
import com.sharp.sword.util.http.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.AdviceFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lizheng
 * @date: 10:53 2019/10/17
 * @Description: LogoutFilter
 */
public class LogoutFilter extends AdviceFilter {

    private RedisService redisService;
    private JwtProperties jwtProperties;

    public LogoutFilter(RedisService redisService, JwtProperties jwtProperties) {
        this.redisService = redisService;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String token = JWTUtil.getTokenFromRequest(req);
        if (StringUtils.isNotBlank(token)) {
            if (VerifyResult.SUCCESS == JWTUtil.verifyToken(token, null)) {
                // 清理redis用户信息
                String userId = JWTUtil.getJwtUsername(token);
                redisService.delKey(RedisConstant.USER_INFO_KEY + userId);
                redisService.delKey(RedisConstant.RANDOM_SALT_KEY + userId);
                // 清空cookies
                CookieUtil.delCookie(req, res, jwtProperties.getTokenName());
            }
            return true;
        } else {
            return false;
        }
    }

}
