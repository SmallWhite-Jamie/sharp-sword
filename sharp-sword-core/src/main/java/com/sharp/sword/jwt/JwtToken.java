package com.sharp.sword.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.shiro.authc.HostAuthenticationToken;

/**
 * @author lizheng
 * @date: 13:24 2019/10/11
 * @Description: JwtToken
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken implements HostAuthenticationToken {

    private String host;

    private String username;

    private String token;

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }
}
