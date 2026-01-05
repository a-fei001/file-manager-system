package com.example.mapper;

import com.example.pojo.Document;
import com.example.vo.DocumentVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 文档Mapper接口
 * @description 提供文档数据访问方法
 */
@Mapper
public interface DocumentMapper {
    
    /**
     * 根据文件名查询文档
     * @param fileName 文件名
     * @return 文档对象
     */
    @Select("SELECT * FROM sys_document WHERE file_name = #{fileName}")
    Document findByFileName(String fileName);
    
    /**
     * 根据ID查询文档
     * @param id 文档ID
     * @return 文档对象
     */
    @Select("SELECT * FROM sys_document WHERE id = #{id}")
    Document findById(Long id);
    
    /**
     * 新增文档
     * @param document 文档对象
     */
    @Insert("INSERT INTO sys_document (file_name, file_type, bucket_name, object_key, admin_id, admin_name) " +
            "VALUES (#{fileName}, #{fileType}, #{bucketName}, #{objectKey}, #{adminId}, #{adminName})")
    void insert(Document document);
    
    /**
     * 根据ID删除文档
     * @param id 文档ID
     */
    @Delete("DELETE FROM sys_document WHERE id = #{id}")
    void deleteById(Long id);
    
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
