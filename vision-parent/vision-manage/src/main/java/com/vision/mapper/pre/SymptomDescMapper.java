package com.vision.mapper.pre;

import org.apache.ibatis.annotations.Param;

import com.vision.pojo.pre.SymptomDesc;

public interface SymptomDescMapper {
    /**
     * 根据症状id删除对应的症状描述
     */
    void deleteDescObjectBySymptomId(@Param("id") Long id);

    /**
     * 新增症状描述表对象
     */
    void insertSymptomDescObject(SymptomDesc symptomDesc);

    /**
     * 根据症状id修改症状的描述信息
     */
    void updateSymptomDescObject(@Param("id") Long id, @Param("symptomDesc") String symptomDesc);

    /**
     * 根据症状id查询症状描述信息
     */
    SymptomDesc findSymptomDescObjectByid(@Param("id") Long id);

}
