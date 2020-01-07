package com.jamie.framework.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.jamie.framework.bean.SysPermission;
import com.jamie.framework.bean.SysRoles;
import com.jamie.framework.bean.SysUser;
import com.jamie.framework.conf.AppProperties;
import com.jamie.framework.constant.AppConstant;
import com.jamie.framework.constant.RedisConstant;
import com.jamie.framework.idgenerator.IdGenerator;
import com.jamie.framework.jwt.JWTUtil;
import com.jamie.framework.jwt.JwtProperties;
import com.jamie.framework.jwt.VerifyResult;
import com.jamie.framework.mapper.SysUserMapper;
import com.jamie.framework.service.LoginService;
import com.jamie.framework.service.PermissionService;
import com.jamie.framework.util.api.ApiCode;
import com.jamie.framework.util.api.ApiResult;
import com.jamie.framework.util.http.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author lizheng
 * @date: 9:51 2019/10/14
 * @Description: LoginServiceImpl
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private PermissionService permissionService;

    @SuppressWarnings("unchecked")
    @Override
    public ApiResult login(String userid, String pw, HttpServletResponse httpServletResponse) {
        // 获取rdis中用户登录时的salt值
        String salt = stringRedisTemplate.opsForValue().get(RedisConstant.RANDOM_SALT_KEY + ":" + appProperties.getKey() + userid);
        if (StringUtils.isBlank(salt)) {
            log.info("无法获取用户[{}]登录对应的 salt 值.", userid);
            return ApiResult.fail(ApiCode.LOGIN_EXCEPTION, "无法获取登录对应的 salt 值");
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("userid", userid);
        // 获取数据库用户信息
        List<SysUser> sysUsers = userMapper.selectByMap(map);
        if (CollectionUtils.isEmpty(sysUsers)) {
            log.warn("用户[{}]: 用户名或密码不正确", userid);
            return ApiResult.fail(ApiCode.USERNAME_PASSWORD_ERROR, "用户名或密码不正确");
        }
        SysUser sysUser = sysUsers.get(0);
        String md5Hex = DigestUtil.md5Hex(sysUser.getPwHash().toUpperCase() + salt).toUpperCase();
        // pw: MD5(MD5(明文密码) + salt)
        if (md5Hex.equals(pw)) {
            String token = JWTUtil.generateToken(userid);
            String tokenName = jwtProperties.getTokenName();
            if (StringUtils.isBlank(tokenName)) {
                tokenName = AppConstant.DEF_TOKEN_NAME;
            }
            CookieUtil.set(httpServletResponse, AppConstant.TOKEN_NAME_KEY, tokenName, -1, "/");
            CookieUtil.set(httpServletResponse, tokenName, token, -1, "/");
            httpServletResponse.setHeader(AppConstant.TOKEN_NAME_KEY, token);
            httpServletResponse.setHeader(tokenName, token);
            // 保存用户信息到redis
            sysUser.setPassword(null);
            sysUser.setPwHash(null);
            String key = RedisConstant.USER_INFO_KEY + appProperties.getKey() + userid;
            String tokenKey = RedisConstant.USER_TOKEN_KEY + appProperties.getKey() + userid;
            redisTemplate.opsForValue().set(key, sysUser, jwtProperties.getExpireSecond(), TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(tokenKey, token, jwtProperties.getExpireSecond(), TimeUnit.SECONDS);

            // 更新角色相关信息
            redisTemplate.delete(RedisConstant.USER_ROLE_KEY + appProperties.getKey() + userid);
            List<SysRoles> sysRoles = permissionService.getRolesByUserId(userid);
            log.info("sysRoles: {}", sysRoles.toString());
            // 更新权限相关信息
            redisTemplate.delete(RedisConstant.USER_PERMISSION_KEY + appProperties.getKey() + userid);
            List<SysPermission> sysPermissions = permissionService.getSysPermissionByUserId(userid);
            log.info("sysPermissions: {}", sysPermissions.toString());
            Map<String, Object> data = new HashMap<>(2);
            data.put("token", token);
            data.put("tokenName", tokenName);
            data.put("userInfo", sysUser);
            data.put("roles", sysRoles.stream().map(SysRoles::getRoleCode).collect(Collectors.toList()));
            data.put("permissions", sysPermissions.stream().map(SysPermission::getCode).collect(Collectors.toList()));
            return ApiResult.ok(data);
        }
        log.warn("用户[{}]: 用户名或密码不正确", userid);
        return ApiResult.fail(ApiCode.USERNAME_PASSWORD_ERROR, "用户名或密码不正确");
    }

    @Override
    public String getLoginRandomSalt(String userid) {
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String str = idGenerator.nextIdStr();
        operations.set(RedisConstant.RANDOM_SALT_KEY + ":" + appProperties.getKey() + userid, str, appProperties.getLoginSaltTimeoutSeconds(), TimeUnit.SECONDS);
        return str;
    }

    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res) {
        String token = JWTUtil.getTokenFromRequest(req);
        if (StringUtils.isNotBlank(token)) {
            if (VerifyResult.SUCCESS == JWTUtil.verifyToken(token, null)) {
                // 清理redis用户信息
                String userId = JWTUtil.getJwtUsername(token);
                String redisToken = (String) redisTemplate.opsForValue().get(RedisConstant.USER_TOKEN_KEY + appProperties.getKey() + userId);
                if (token.equals(redisToken)) {
                    redisTemplate.delete(RedisConstant.USER_INFO_KEY + appProperties.getKey() + userId);
                    redisTemplate.delete(RedisConstant.RANDOM_SALT_KEY + appProperties.getKey() + userId);
                    redisTemplate.delete(RedisConstant.USER_ROLE_KEY + appProperties.getKey() + userId);
                    redisTemplate.delete(RedisConstant.USER_PERMISSION_KEY + appProperties.getKey() + userId);
                    redisTemplate.delete(RedisConstant.USER_TOKEN_KEY + appProperties.getKey() + userId);
                    // 清空cookies
                    CookieUtil.delCookie(req, res, jwtProperties.getTokenName());
                }
            }
        }
    }
}
