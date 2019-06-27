package com.vision.pojo.ppo;



import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.vision.pojo.BasePojo;
import com.vision.pojo.pre.SymptomDesc;
import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
@TableName("ppo_appointment_order")
public class PpoAppointmentOrder extends BasePojo{

	@TableId(type=IdType.AUTO)
	private Long orderId;
	private Long trainerId;
	private String trainerName; 
	private Long organizationId;
	private Long customerId;
	private String serviceItems;
	private Date appointmentTime;
	private String appointmentRemark;
	private Integer appointmentState;
	private String createdUser;
	private String modifiedUser;
	
}
