package com.jamie.framework.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.jamie.framework.bean.SysPermission;
import com.jamie.framework.bean.SysRoles;
import com.jamie.framework.bean.SysUser;
import com.jamie.framework.conf.AppProperties;
import com.jamie.framework.constant.AppConstant;
import com.jamie.framework.constant.RedisConstant;
import com.jamie.framework.enums.LoginFailEnum;
import com.jamie.framework.event.LoginFailEvent;
import com.jamie.framework.event.LoginSuccessEvent;
import com.jamie.framework.idgenerator.IdGenerator;
import com.jamie.framework.jwt.JWTUtil;
import com.jamie.framework.jwt.JwtProperties;
import com.jamie.framework.mapper.SysUserMapper;
import com.jamie.framework.redis.RedisService;
import com.jamie.framework.service.LoginService;
import com.jamie.framework.service.PermissionService;
import com.jamie.framework.util.ApplicationContextUtil;
import com.jamie.framework.util.api.ApiCode;
import com.jamie.framework.util.api.ApiResult;
import com.jamie.framework.util.http.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private AppProperties appProperties;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private PermissionService permissionService;

    @Override
    public ApiResult login(String userid, String pw, HttpServletResponse httpServletResponse) {
        // 获取rdis中用户登录时的salt值
        String salt = redisService.getStr(RedisConstant.RANDOM_SALT_KEY + userid);
        if (StringUtils.isBlank(salt)) {
            ApplicationContextUtil.publishEvent(new LoginFailEvent(LoginFailEnum.SALT_ERROR));
            log.info("无法获取用户[{}]登录对应的 salt 值.", userid);
            return ApiResult.fail(ApiCode.LOGIN_EXCEPTION, "无法获取登录对应的 salt 值");
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("userid", userid);
        // 获取数据库用户信息
        List<SysUser> sysUsers = userMapper.selectByMap(map);
        if (CollectionUtils.isEmpty(sysUsers)) {
            log.warn("用户[{}]: 用户名或密码不正确", userid);
            ApplicationContextUtil.publishEvent(new LoginFailEvent(LoginFailEnum.USER_NOT_EXIST));
            return ApiResult.fail(ApiCode.USERNAME_PASSWORD_ERROR, "用户名或密码不正确");
        }
        SysUser sysUser = sysUsers.get(0);
        String md5Hex = DigestUtil.md5Hex(sysUser.getPwHash().toUpperCase() + salt).toUpperCase();
        // pw: MD5(MD5(明文密码) + salt)
        if (md5Hex.equals(pw)) {
            ApplicationContextUtil.publishEvent(new LoginSuccessEvent(sysUser));
            Map<String, Object> data = loginSuccess(userid, httpServletResponse, sysUser);
            return ApiResult.ok(data);
        }
        ApplicationContextUtil.publishEvent(new LoginFailEvent(LoginFailEnum.PASSWORD_ERROR));
        log.warn("用户[{}]: 用户名或密码不正确", userid);
        return ApiResult.fail(ApiCode.USERNAME_PASSWORD_ERROR, "用户名或密码不正确");
    }

    @Override
    public String getLoginRandomSalt(String userid) {
        String str = idGenerator.nextIdStr();
        redisService.setStr(RedisConstant.RANDOM_SALT_KEY + userid, str, appProperties.getLoginSaltTimeoutSeconds());
        return str;
    }

    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res) {
        String userId = JWTUtil.getJwtUsername(JWTUtil.getTokenFromRequest(req));
        redisService.delKey(RedisConstant.USER_INFO_KEY + userId,
                RedisConstant.RANDOM_SALT_KEY + userId,
                RedisConstant.USER_ROLE_KEY + userId,
                RedisConstant.USER_PERMISSION_KEY + userId,
                RedisConstant.USER_TOKEN_KEY + userId);

//        JWTRealm 里面已经做过验证了，存储不需要再验证token合法性
//        String token = JWTUtil.getTokenFromRequest(req);
//        if (StringUtils.isNotBlank(token)) {
//            if (VerifyResult.SUCCESS == JWTUtil.verifyToken(token, null)) {
//                // 清理redis用户信息
//                String userId = JWTUtil.getJwtUsername(token);
//                String redisToken = redisService.getStr(RedisConstant.USER_TOKEN_KEY + userId);
//                if (token.equals(redisToken)) {
//                    redisService.delKey(RedisConstant.USER_INFO_KEY + userId,
//                            RedisConstant.RANDOM_SALT_KEY + userId,
//                            RedisConstant.USER_ROLE_KEY + userId,
//                            RedisConstant.USER_PERMISSION_KEY + userId,
//                            RedisConstant.USER_TOKEN_KEY + userId);
//                    // 清空cookies
//                    CookieUtil.delCookie(req, res, jwtProperties.getTokenName());
//                }
//            }
//        }
    }

    private Map<String, Object> loginSuccess(String userid, HttpServletResponse httpServletResponse, SysUser sysUser) {
        String token = JWTUtil.generateToken(userid);
        String tokenName = jwtProperties.getTokenName();
        if (StringUtils.isBlank(tokenName)) {
            tokenName = AppConstant.DEF_TOKEN_NAME;
        }
        CookieUtil.set(httpServletResponse, AppConstant.TOKEN_NAME_KEY, tokenName, -1, "/");
        CookieUtil.set(httpServletResponse, tokenName, token, -1, "/");
        httpServletResponse.setHeader(AppConstant.TOKEN_NAME_KEY, tokenName);
        httpServletResponse.setHeader(tokenName, token);
        // 保存用户信息到redis
        sysUser.setPassword(null);
        sysUser.setPwHash(null);
        redisService.setObj(RedisConstant.USER_INFO_KEY + userid, sysUser, jwtProperties.getExpireSecond());
        redisService.setStr(RedisConstant.USER_TOKEN_KEY + userid, token, jwtProperties.getExpireSecond());

        // 更新角色相关信息
        redisService.delKey(RedisConstant.USER_ROLE_KEY + userid);
        List<SysRoles> sysRoles = permissionService.getRolesByUserId(userid);
        log.info("sysRoles: {}", sysRoles.toString());
        // 更新权限相关信息
        redisService.delKey(RedisConstant.USER_PERMISSION_KEY + userid);
        List<SysPermission> sysPermissions = permissionService.getSysPermissionByUserId(userid);
        log.info("sysPermissions: {}", sysPermissions.toString());
        Map<String, Object> data = new HashMap<>(2);
        data.put("token", token);
        data.put("tokenName", tokenName);
        data.put("userInfo", sysUser);
        data.put("roles", sysRoles.stream().map(SysRoles::getRoleCode).collect(Collectors.toList()));
        data.put("permissions", sysPermissions.stream().map(SysPermission::getCode).collect(Collectors.toList()));
        return data;
    }
}
