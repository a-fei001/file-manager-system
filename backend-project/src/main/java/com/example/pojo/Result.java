package com.example.pojo;

import lombok.Data;

/**
 * 统一响应结果类
 * @description 用于封装所有接口的响应数据
 */
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    /**
     * 成功响应（无数据）
     * @param message 成功消息
     * @return Result对象
     */
    public static <T> Result<T> success(String message) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        return result;
    }

    /**
     * 成功响应（带数据）
     * @param message 成功消息
     * @param data 响应数据
     * @return Result对象
     */
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 错误响应
     * @param code 错误码
     * @param message 错误消息
     * @return Result对象
     */
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 错误响应（默认错误码400）
     * @param message 错误消息
     * @return Result对象
     */
    public static <T> Result<T> error(String message) {
        return error(400, message);
    }
}
