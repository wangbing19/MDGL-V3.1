package com.vision.pojo.ppo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.vision.pojo.BasePojo;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
@TableName("ppo_appointment_time")
public class PpoAppointmentTime extends BasePojo{
	@TableId(type=IdType.AUTO)
	private Long id;
	private Long tarinerId; 
	private Date appointmentTime;
	private Integer appointmentTimeState;
	private String createdUser;
	private String modifiedUser;
}
