package com.vision.mapper.ppo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.ppo.PpoTrainer;


public interface PpoTrainerMapper extends BaseMapper<PpoTrainer>{
		
	List<PpoTrainer> findPpoTrainerAll(@Param("trainerName")String trainerName, @Param("startIndex")Integer startIndex, @Param("pageSize")Integer pageSize,
			@Param("list")List<Long> list);
}
