package com.sharp.sword.conf.webmvc;

import com.sharp.sword.conf.AppProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 * @author lizheng
 * @date: 18:24 2020/02/11
 * @Description: CorsConfig
 */
@Configuration
public class CorsConfig {

    @Autowired
    private AppProperties appProperties;

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        String allowedOrigin = appProperties.getAllowedOrigin();
        config.addAllowedOrigin(StringUtils.isNotBlank(allowedOrigin) ? allowedOrigin : CorsConfiguration.ALL);
        config.addAllowedHeader(CorsConfiguration.ALL);
        config.addAllowedMethod(CorsConfiguration.ALL);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        CorsFilter filter = new CorsFilter(source);
        return new FilterRegistrationBean<>(filter);
    }
}
