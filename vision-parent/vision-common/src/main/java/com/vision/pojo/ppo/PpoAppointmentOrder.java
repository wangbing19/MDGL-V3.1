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
	private Long orderId;//主键id
	private Long trainerId;//训练师id
	private String trainerName;//训练师名称
	private Long organizationId;//组织id
	private Long customerId;//顾客id
	private String serviceItems;//服务项目
	private Date appointmentTime;//预约时间
	private String appointmentRemark;//预约备注
	private Integer appointmentState;//预约状态（0预约失败，1预约成功，2订单已完成，3订单预约成功但未完成）
	private String createdUser;
	private String modifiedUser;
	
}
