package com.example.config;

import com.example.interceptor.AdminInterceptor;
import com.example.interceptor.TokenInterceptor;
import com.example.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 * @description 配置拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Autowired
    private UserInterceptor userInterceptor;
    @Autowired
    private AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login");

        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/user/**");

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**");
    }
}
