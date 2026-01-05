package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果类
 * @description 封装分页查询结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    
    private Long total;
    
    private List<T> items;
    
    public static <T> PageResult<T> of(Long total, List<T> items) {
        return new PageResult<>(total, items);
    }
}
