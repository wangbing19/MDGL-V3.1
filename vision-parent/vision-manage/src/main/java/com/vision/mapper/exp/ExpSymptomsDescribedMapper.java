package com.vision.mapper.exp;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.exp.ExpExpertReply;
import com.vision.pojo.exp.ExpSymptomsDescribed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ExpSymptomsDescribedMapper extends BaseMapper<ExpSymptomsDescribed> {
    List<Integer> selectSymId(@Param("ids")Integer... ids);
}
