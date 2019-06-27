package com.vision.mapper.ppo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.ppo.PpoAppointmentOrder;


public interface PpoAppointmentOrderMapper extends BaseMapper<PpoAppointmentOrder>{

	List<PpoAppointmentOrder> findPpoOderAll(@Param("organizationId") Long organizationId);



}
