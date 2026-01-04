package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置类
 * 用于解决前端Vue3项目(3000端口)访问后端接口时的跨域问题
 */
@Configuration
public class CorsConfig {

    /**
     * 配置CORS过滤器
     * 允许所有来源、所有请求头、所有请求方法访问后端接口
     * 
     * @return CorsFilter实例
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // 允许所有来源(生产环境建议指定具体域名)
        config.addAllowedOriginPattern("*");
        
        // 允许所有请求头
        config.addAllowedHeader("*");
        
        // 允许所有请求方法(GET, POST, PUT, DELETE等)
        config.addAllowedMethod("*");
        
        // 允许携带凭证(cookies)
        config.setAllowCredentials(true);
        
        // 暴露的响应头
        config.addExposedHeader("*");
        
        // 配置CORS源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        
        // 对所有路径应用CORS配置
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}
