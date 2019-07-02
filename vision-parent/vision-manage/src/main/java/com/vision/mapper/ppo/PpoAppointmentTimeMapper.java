package com.vision.mapper.ppo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vision.pojo.ppo.PpoAppointmentTime;


public interface PpoAppointmentTimeMapper extends BaseMapper<PpoAppointmentTime>{

	int insertAppointmentTime(PpoAppointmentTime ppoAppointmentTime);

}
