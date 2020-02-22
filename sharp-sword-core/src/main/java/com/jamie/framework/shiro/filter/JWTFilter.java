package com.jamie.framework.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.jamie.framework.conf.AppProperties;
import com.jamie.framework.constant.AppConstant;
import com.jamie.framework.constant.RedisConstant;
import com.jamie.framework.jwt.JWTUtil;
import com.jamie.framework.jwt.JwtProperties;
import com.jamie.framework.jwt.JwtToken;
import com.jamie.framework.util.ApplicationContextUtil;
import com.jamie.framework.util.api.ApiCode;
import com.jamie.framework.util.api.ApiResult;
import com.jamie.framework.util.http.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author lizheng
 * @date: 16:24 2019/10/10
 * @Description: JWTFilter
 */
@Slf4j
public class JWTFilter extends AuthenticatingFilter {

    private JwtProperties jwtProperties;

    public JWTFilter(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     * 判断是否允许访问
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String requestPath = WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
        if (this.isLoginRequest(request, response)) {
            return true;
        }
        String token = JWTUtil.getTokenFromRequest((HttpServletRequest) request);
        log.info("请求路径: [{}], token: [{}]", requestPath, token);
        if (StringUtils.isBlank(token)) {
            return false;
        }
        boolean allowed = false;
        try {
            allowed = this.executeLogin(request, response);
        } catch (Exception e) {
            log.error("访问错误", e);
        }
        return allowed || super.isPermissive(mappedValue);
//        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     *  将JWT Token包装成AuthenticationToken
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String token = JWTUtil.getTokenFromRequest((HttpServletRequest) servletRequest);
        JwtToken jwtToken = new JwtToken();
        jwtToken.setHost(servletRequest.getRemoteHost());
        jwtToken.setToken(token);
        jwtToken.setUsername(JWTUtil.getJwtUsername(token));
        return jwtToken;
    }

    /**
     * 访问失败
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        // servletResponse((HttpServletResponse) servletResponse, "访问失败");
        return false;
    }

    /**
     * 登录成功
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        JwtToken jwtToken = (JwtToken) token;
        if (JWTUtil.checkRefresh(jwtToken)) {
            log.info("刷新TOKEN...");
            String userId = jwtToken.getUsername();
            // 生成新的token
            String newToken = JWTUtil.generateToken(userId);
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            // 刷新cookies
            CookieUtil.set(httpServletResponse, jwtProperties.getTokenName(), newToken, (int) jwtProperties.getExpireSecond(), "/");
            httpServletResponse.setHeader(jwtProperties.getTokenName(), newToken);
            httpServletResponse.setHeader(AppConstant.TOKEN_REFRESH, "OK");

            // 刷新redis
            RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtil.getBean("redisTemplate");
            String appKey = ApplicationContextUtil.getBean(AppProperties.class).getKey();
            redisTemplate.expire(RedisConstant.USER_INFO_KEY + appKey + userId, jwtProperties.getExpireSecond(), TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(RedisConstant.USER_TOKEN_KEY + appKey + userId, newToken, jwtProperties.getExpireSecond(), TimeUnit.SECONDS);
            redisTemplate.expire(RedisConstant.USER_ROLE_KEY + appKey + userId, jwtProperties.getExpireSecond(), TimeUnit.SECONDS);
            redisTemplate.expire(RedisConstant.USER_PERMISSION_KEY + appKey + userId, jwtProperties.getExpireSecond(), TimeUnit.SECONDS);
        }
        return true;
    }

    /**
     * 登录失败
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        log.warn(e.getMessage(), e);
        servletResponse((HttpServletResponse) response, e.getMessage(), ApiCode.LOGIN_EXCEPTION.getCode());
        return false;
    }

    private void servletResponse(HttpServletResponse res, String data, int code) {
        res.setStatus(200);
        res.setHeader("Content-type", "text/json;charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        try {
            ApiResult result = code == 200 ? ApiResult.ok(data) : ApiResult.fail(ApiCode.LOGIN_EXCEPTION, data);
            res.getWriter().write(JSONObject.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
