package com.example.interceptor;

import com.example.mapper.UserMapper;
import com.example.pojo.User;
import com.example.util.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 管理员权限拦截器
 * @description 拦截管理员页面相关请求，验证管理员身份和权限
 */
@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long userId = ThreadLocalUtil.getUserId();
        if (userId == null) {
            response.setStatus(401);
            response.getWriter().write("{\"code\": 401, \"message\": \"未登录\"}");
            return false;
        }

        User user = userMapper.findByUsername(request.getHeader("username"));
        if (user == null || user.getRole() != 1) {
            response.setStatus(403);
            response.getWriter().write("{\"code\": 403, \"message\": \"无权访问\"}");
            return false;
        }

        return true;
    }
}
