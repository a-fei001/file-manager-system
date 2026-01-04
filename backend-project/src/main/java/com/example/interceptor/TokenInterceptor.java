package com.example.interceptor;

import com.example.util.JwtUtil;
import com.example.util.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Token解析拦截器
 * @description 拦截除登录请求以外的所有请求，解析token并将userId存入ThreadLocal
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token != null && JwtUtil.validateToken(token)) {
            Long userId = JwtUtil.getUserIdFromToken(token);
            ThreadLocalUtil.setUserId(userId);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.clearUserId();
    }
}
