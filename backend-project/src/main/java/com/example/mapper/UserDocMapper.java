package com.example.mapper;

import com.example.vo.DocumentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 用户文档Mapper接口
 * @description 提供用户文档查询数据访问方法
 */
@Mapper
public interface UserDocMapper {
    
    /**
     * 根据条件统计文档数量
     * @param params 查询参数（包含fileName, fileType）
     * @return 文档数量
     */
    Long countByCondition(Map<String, Object> params);
    
    /**
     * 根据条件分页查询文档列表
     * @param params 查询参数（包含fileName, fileType, pageSize, offset）
     * @return 文档列表
     */
    List<DocumentVO> findByCondition(Map<String, Object> params);
}
