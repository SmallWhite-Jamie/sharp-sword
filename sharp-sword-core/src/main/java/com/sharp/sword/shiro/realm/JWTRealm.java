package com.sharp.sword.shiro.realm;

import com.sharp.sword.bean.SysPermission;
import com.sharp.sword.bean.SysRoles;
import com.sharp.sword.constant.RedisConstant;
import com.sharp.sword.jwt.JWTUtil;
import com.sharp.sword.jwt.JwtToken;
import com.sharp.sword.jwt.VerifyResult;
import com.sharp.sword.redis.RedisService;
import com.sharp.sword.service.PermissionService;
import com.sharp.sword.util.ApplicationContextUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lizheng
 * @date: 15:27 2019/10/11
 * @Description: JWTRealm
 */
public class JWTRealm extends AuthorizingRealm {

    private RedisService redisService;

    public JWTRealm(RedisService redisService) {
        this.redisService = redisService;
    }


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String token = (String) principals.getPrimaryPrincipal();
        String userId = JWTUtil.getJwtUsername(token);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        PermissionService permissionService = ApplicationContextUtil.getBean(PermissionService.class);

        // 获取角色
        List<SysRoles> sysRoles = permissionService.getRolesByUserId(userId);
        if (CollectionUtils.isNotEmpty(sysRoles)) {
            Set<String> roles = sysRoles.stream().map(SysRoles::getRoleCode).collect(Collectors.toSet());
            authorizationInfo.addRoles(roles);
        }
        // 获取权限
        List<SysPermission> sysPermissions = permissionService.getSysPermissionByUserId(userId);
        if (CollectionUtils.isNotEmpty(sysPermissions)) {
            Set<String> permissions = sysPermissions.stream().map(SysPermission::getCode).collect(Collectors.toSet());
            authorizationInfo.addStringPermissions(permissions);
        }
        return authorizationInfo;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @SuppressWarnings("unchecked")
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        if (jwtToken == null || StringUtils.isBlank(jwtToken.getToken())) {
            throw new AuthenticationException(VerifyResult.NULL.toString());
        }
        VerifyResult verifyResult = JWTUtil.verifyToken(jwtToken.getToken(), null);
        if (verifyResult == VerifyResult.EXPIRED) {
            throw new AuthenticationException(VerifyResult.EXPIRED.toString());
        } else if (verifyResult == VerifyResult.FAILURE) {
            throw new AuthenticationException(VerifyResult.FAILURE.toString());
        }
        // 验证redis登录信息
        String key = RedisConstant.USER_TOKEN_KEY + JWTUtil.getJwtUsername(jwtToken.getToken());
        String serviceToken = redisService.getStr(key);
        if (!jwtToken.getToken().equals(serviceToken)) {
            throw new AuthenticationException("用户信息认证失败");
        }
        return new SimpleAuthenticationInfo(jwtToken.getToken(), jwtToken.getToken(), getName());
    }
}
