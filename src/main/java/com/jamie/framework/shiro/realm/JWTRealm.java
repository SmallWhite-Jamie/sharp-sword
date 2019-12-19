package com.jamie.framework.shiro.realm;

import com.jamie.framework.bean.SysPermission;
import com.jamie.framework.bean.SysRoles;
import com.jamie.framework.conf.AppProperties;
import com.jamie.framework.constant.RedisConstant;
import com.jamie.framework.jwt.JWTUtil;
import com.jamie.framework.jwt.JwtToken;
import com.jamie.framework.jwt.VerifyResult;
import com.jamie.framework.service.PermissionService;
import com.jamie.framework.util.ApplicationContextUtil;
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
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lizheng
 * @date: 15:27 2019/10/11
 * @Description: JWTRealm
 */
public class JWTRealm extends AuthorizingRealm {

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
            throw new AuthenticationException("token不能为空");
        }
        VerifyResult verifyResult = JWTUtil.verifyToken(jwtToken.getToken(), null);
        if (verifyResult == VerifyResult.EXPIRED) {
            throw new AuthenticationException("token已过期");
        } else if (verifyResult == VerifyResult.FAILURE) {
            throw new AuthenticationException("token验证失败");
        }
        // 验证redis登录信息
        AppProperties appProperties = ApplicationContextUtil.getBean(AppProperties.class);
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtil.getBean("redisTemplate");
        String key = RedisConstant.USER_INFO_KEY + appProperties.getKey() + JWTUtil.getJwtUsername(jwtToken.getToken());
        Boolean hasKey = redisTemplate.hasKey(key);
        if (!hasKey) {
            throw new AuthenticationException("非法token, 用户已经退出");
        }
        return new SimpleAuthenticationInfo(jwtToken.getToken(), jwtToken.getToken(), getName());
    }
}
