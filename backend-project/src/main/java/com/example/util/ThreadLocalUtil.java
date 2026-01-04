package com.example.util;

/**
 * ThreadLocal工具类
 * @description 用于在当前线程中存储和获取用户ID
 */
public class ThreadLocalUtil {
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置当前线程的用户ID
     * @param userId 用户ID
     */
    public static void setUserId(Long userId) {
        threadLocal.set(userId);
    }

    /**
     * 获取当前线程的用户ID
     * @return 用户ID
     */
    public static Long getUserId() {
        return threadLocal.get();
    }

    /**
     * 清除当前线程的用户ID
     */
    public static void clearUserId() {
        threadLocal.remove();
    }
}
