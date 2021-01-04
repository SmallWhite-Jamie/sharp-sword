package com.sharp.sword.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.sharp.sword.constant.AppConstant;
import com.sharp.sword.constant.RedisConstant;
import com.sharp.sword.jwt.JWTUtil;
import com.sharp.sword.jwt.JwtProperties;
import com.sharp.sword.jwt.JwtToken;
import com.sharp.sword.redis.RedisService;
import com.sharp.sword.util.ApplicationContextUtil;
import com.sharp.sword.util.api.ApiCode;
import com.sharp.sword.util.api.ApiResult;
import com.sharp.sword.util.http.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lizheng
 * @date: 16:24 2019/10/10
 * @Description: JWTFilter
 */
@Slf4j
public class JWTFilter extends AuthenticatingFilter {

    private JwtProperties jwtProperties;

    private RedisService redisService;

    public JWTFilter(JwtProperties jwtProperties, RedisService redisService) {
        this.jwtProperties = jwtProperties;
        this.redisService = redisService;
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
        for (String url : ApplicationContextUtil.getShiroAnonUrlSet()) {
            if (requestPath.startsWith(url)) {
                return true;
            }
        }
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
         servletResponse((HttpServletResponse) servletResponse, null, ApiCode.NOT_PERMISSION);
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
            redisService.expireSeconds(RedisConstant.USER_INFO_KEY + userId, jwtProperties.getExpireSecond());
            redisService.setStr(RedisConstant.USER_TOKEN_KEY + userId, newToken, jwtProperties.getExpireSecond());
            redisService.expireSeconds(RedisConstant.USER_ROLE_KEY + userId, jwtProperties.getExpireSecond());
            redisService.expireSeconds(RedisConstant.USER_PERMISSION_KEY  + userId, jwtProperties.getExpireSecond());
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
        servletResponse((HttpServletResponse) response, e.getMessage(), ApiCode.LOGIN_EXCEPTION);
        return false;
    }

    private void servletResponse(HttpServletResponse res, String data, ApiCode code) {
        res.setStatus(200);
        res.setHeader("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        res.setCharacterEncoding("UTF-8");
        try {
            ApiResult result = code.getCode() == 200 ? ApiResult.ok(data) : ApiResult.fail(code, data);
            res.getWriter().write(JSONObject.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
