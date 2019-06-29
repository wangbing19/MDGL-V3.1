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
	private Long id;//主键
	private Long tarinerId;//训练师id 
	private Date appointmentTime;//预约时间
	private Integer appointmentTimeState;//状态（1生效 0失效）
	private String createdUser;
	private String modifiedUser;
}
