package com.vision.mapper.exp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.exp.ExpExpert;
import com.vision.vo.Node;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExpertMapper extends BaseMapper<ExpExpert> {
    /**
     * 与远程诊断表并用,通过专家表id查找专家姓名
     *
     * @param expertId
     * @return
     */
    String findId(Integer expertId);

    /**
     * 基于条件进行分页查询
     *
     * @param expertName
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<ExpExpert> limitExp(
            @Param("expertName") String expertName,
            @Param("startIndex") Integer startIndex,
            @Param("pageSize") Integer pageSize);

    Node[] selectExpName();
}
