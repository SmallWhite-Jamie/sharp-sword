package com.sharp.sword.login.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.sharp.sword.bean.SysPermission;
import com.sharp.sword.bean.SysRoles;
import com.sharp.sword.login.entity.SysUserEntity;
import com.sharp.sword.conf.AppProperties;
import com.sharp.sword.constant.AppConstant;
import com.sharp.sword.constant.RedisConstant;
import com.sharp.sword.enums.ErrorCode;
import com.sharp.sword.login.event.LoginFailEvent;
import com.sharp.sword.login.event.LoginStartEvent;
import com.sharp.sword.login.event.LoginSuccessEvent;
import com.sharp.sword.idgenerator.IdGenerator;
import com.sharp.sword.jwt.JWTUtil;
import com.sharp.sword.jwt.JwtProperties;
import com.sharp.sword.login.LoginFailDataBean;
import com.sharp.sword.login.vo.SysUserEntityVO;
import com.sharp.sword.login.mapper.SysUserMapper;
import com.sharp.sword.redis.RedisService;
import com.sharp.sword.login.service.LoginService;
import com.sharp.sword.service.PermissionService;
import com.sharp.sword.util.ApplicationContextUtil;
import com.sharp.sword.util.api.ApiCode;
import com.sharp.sword.util.api.ApiResult;
import com.sharp.sword.util.http.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("redisIdGenerator")
    private IdGenerator idGenerator;

    @Autowired
    private PermissionService permissionService;

    @Override
    public ApiResult login(SysUserEntityVO userEntityVO, HttpServletResponse httpServletResponse) {
        String userid = userEntityVO.getUserid();
        // 1、获取rdis中用户登录时的salt值
        String salt = redisService.getStr(RedisConstant.RANDOM_SALT_KEY + userid);
        if (StringUtils.isBlank(salt)) {
            ApplicationContextUtil.publishEvent(new LoginFailEvent(new LoginFailDataBean(ErrorCode.SALT_ERROR, userid)));
            log.info("无法获取用户[{}]登录对应的 salt 值.", userid);
            return ApiResult.fail(ApiCode.LOGIN_EXCEPTION, "无法获取登录对应的 salt 值");
        }
        // 发布一个事件，通过订阅该事件，在验证密码之前做一些操作
        ApplicationContextUtil.publishEvent(new LoginStartEvent(userEntityVO));

        Map<String, Object> map = new HashMap<>(2);
        map.put("userid", userid);
        // 2、获取数据库用户信息
        List<SysUserEntity> sysUserEntities = userMapper.selectByMap(map);
        if (CollectionUtils.isEmpty(sysUserEntities)) {
            log.warn("用户[{}]: 用户名或密码不正确", userid);
            ApplicationContextUtil.publishEvent(new LoginFailEvent(new LoginFailDataBean(ErrorCode.USER_NOT_EXIST, userid)));
            return ApiResult.fail(ApiCode.USERNAME_PASSWORD_ERROR, "用户名或密码不正确");
        }
        SysUserEntity sysUserEntity = sysUserEntities.get(0);
        String md5Hex = DigestUtil.md5Hex(sysUserEntity.getPwHash().toUpperCase() + salt).toUpperCase();
        // pw: MD5(MD5(明文密码) + salt)
        if (md5Hex.equals(userEntityVO.getPassword())) {
            ApplicationContextUtil.publishEvent(new LoginSuccessEvent(sysUserEntity));
            Map<String, Object> data = loginSuccess(userid, httpServletResponse, sysUserEntity);
            return ApiResult.ok(data);
        }
        // 发布一个密码错误事件
        ApplicationContextUtil.publishEvent(new LoginFailEvent(new LoginFailDataBean(ErrorCode.PASSWORD_ERROR, userid)));
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

    private Map<String, Object> loginSuccess(String userid, HttpServletResponse httpServletResponse, SysUserEntity sysUserEntity) {
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
        sysUserEntity.setPassword(null);
        sysUserEntity.setPwHash(null);
        redisService.setObj(RedisConstant.USER_INFO_KEY + userid, sysUserEntity, jwtProperties.getExpireSecond());
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
        data.put("userInfo", sysUserEntity);
        data.put("roles", sysRoles.stream().map(SysRoles::getRoleCode).collect(Collectors.toList()));
        data.put("permissions", sysPermissions.stream().map(SysPermission::getCode).collect(Collectors.toList()));
        return data;
    }
}
