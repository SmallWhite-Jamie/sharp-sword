package com.sharp.sword.jwt;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;

/**
 * @author lizheng
 * @date: 16:53 2019/10/10
 * @Description: JWTUtil
 */
@Slf4j
@Component
public class JWTUtil {

    private static JwtProperties jwtProperties;

    private static final String JWT_USERNAME = "username";

    public JWTUtil(JwtProperties properties) {
        JWTUtil.jwtProperties = properties;
    }


    /**
     *
     * @param username 用户名
     * @return
     */
    public static String generateToken(String username) {
        return generateToken(username, null, null);
    }

    /**
     *
     * @param username 用户名
     * @param salt 盐值
     * @param expireDuration 过期时间
     * @return
     */
    public static String generateToken(String username, String salt, Duration expireDuration) {
        if (StrUtil.isBlank(username)) {
            return null;
        }
        if (StrUtil.isBlank(salt)) {
            // 默认为 secret = 123456
            salt = jwtProperties.getSecret();
        }
        long expireSecond;
        if (expireDuration == null) {
            expireSecond = jwtProperties.getExpireSecond();
        } else {
            expireSecond = expireDuration.getSeconds();
        }
        log.debug("generate token, username: [{}], salt: [{}], expireSecond: [{}]",
                username, salt, expireSecond);
        Date expireDate = DateUtils.addSeconds(new Date(), (int) expireSecond);
        Algorithm algorithm = Algorithm.HMAC256(salt);

        String token = JWT.create()
                .withClaim(JWT_USERNAME, username)
                // jwt唯一id
                .withJWTId(UUID.randomUUID().toString(true))
                // 签发人
                .withIssuer(jwtProperties.getIssuer())
                // 主题
                .withSubject(jwtProperties.getSubject())
                // 签发的目标
                .withAudience(jwtProperties.getAudience())
                // 签名时间
                .withIssuedAt(new Date())
                // token过期时间
                .withExpiresAt(expireDate)
                // 签名
                .sign(algorithm);
        log.info("token: [{}]", token);
        return token;
    }

    /**
     *
     * @param token token
     * @param salt 盐值
     * @return
     */
    public static VerifyResult verifyToken(String token, String salt) {
        try {
            if (StrUtil.isBlank(salt)) {
                salt = jwtProperties.getSecret();
            }
            Algorithm algorithm = Algorithm.HMAC256(salt);
            JWTVerifier verifier = JWT.require(algorithm)
                    // 签发人
                    .withIssuer(jwtProperties.getIssuer())
                    // 主题
                    .withSubject(jwtProperties.getSubject())
                    // 签发的目标
                    .withAudience(jwtProperties.getAudience())
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            if (jwt != null) {
                return VerifyResult.SUCCESS;
            }
        } catch (TokenExpiredException expired) {
            log.error("Verify Token Exception", expired.getMessage());
            return VerifyResult.EXPIRED;
        } catch (Exception e) {
            log.error("Verify Token Exception", e.getMessage());
        }
        return VerifyResult.FAILURE;
    }

    public static String getJwtUsername(String token) {
        if (StrUtil.isBlank(token)) {
            return null;
        }
        String username = null;
        try {
            DecodedJWT decode = JWT.decode(token);
            username = decode.getClaim(JWT_USERNAME).asString();
        } catch (JWTDecodeException e) {
            log.error("根据token获取 JWT_USERNAME 失败：", e);
        }
        return username;
    }

    public static String getTokenFromRequest(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request不能为空");
        }
        // 从请求参数中获取token
        String token = request.getParameter(jwtProperties.getTokenName());
        if (StringUtils.isBlank(token)) {
            // 从请求头中获取token
            token = request.getHeader(jwtProperties.getTokenName());
            if (StringUtils.isBlank(token)) {
                // 从cookies中获取token
                Cookie[] cookies = request.getCookies();
                if (cookies == null) {
                    return null;
                }
                for (Cookie cookie : cookies) {
                    if (jwtProperties.getTokenName().equalsIgnoreCase(cookie.getName()))
                    {
                        token = cookie.getValue();
                    }
                }
            }
        }
        return token;
    }

    public static boolean isExpired(String token) {
        Date expiresAt = JWT.decode(token).getExpiresAt();
        return expiresAt != null && expiresAt.after(new Date());
    }

    public static boolean checkRefresh(JwtToken jwtToken) {
        boolean isRefreshToken = jwtProperties.isRefreshToken();
        String token = jwtToken.getToken();
        if (!isRefreshToken || StringUtils.isBlank(token)) {
            return false;
        }

        // 获取过期时间
        Date expiresAt = JWT.decode(token).getExpiresAt();
        // 如果 当前时间 + (超时时间 - 刷新时间) > 过期时间(未来某个时间点)，则刷新token
        boolean refresh = DateUtils.addSeconds(new Date(), jwtProperties.getExpireSecond() - jwtProperties.getRefreshTokenInterval()).after(expiresAt);
        return refresh;
    }
}
