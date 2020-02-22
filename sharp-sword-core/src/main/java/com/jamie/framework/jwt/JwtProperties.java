/*
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jamie.framework.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jamie.li
 */
@Data
@Component
@ConfigurationProperties(prefix = "shiro.jwt")
public class JwtProperties {

    /**
     * token名称,默认名称为：token，可自定义
     */
    private String tokenName = "token";

    /**
     * 密码
     */
    private String secret = "123456";

    /**
     * 签发人
     */
    private String issuer;

    /**
     * 主题
     */
    private String subject;

    /**
     * 签发的目标
     */
    private String audience = "audience";

    /**
     * token失效时间,默认1小时，60*60=3600
     */
    private int expireSecond = 60 * 60;

    /**
     * 是否刷新token，默认为true
     */
    private boolean refreshToken = true;

    /**
     * 刷新token倒计时，默认10分钟，10*60=600
     */
    private int refreshTokenInterval = 60 * 10;

    /**
     * redis校验jwt token是否存在
     */
    private boolean redisCheck;

    /**
     * 单用户登陆，一个用户只能又一个有效的token
     */
    private boolean singleLogin;

}
