package com.vision.pojo.ppo;



import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.vision.pojo.BasePojo;
import com.vision.pojo.pre.SymptomDesc;
import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
@TableName("ppo_trainer")
public class PpoTrainer extends BasePojo{
	@TableId(type=IdType.AUTO)
	/**
	 * 主键
	 */
	private Long id; 
	/**
	 * 组织id
	 */
	private Long organizationId; 
	/**
	 * 训练师姓名
	 */
	private String trainerName;
	/**
	 * 训练师性别
	 */
	private String trainerGender;
	/**
	 * 职位
	 */
	private String post;
	/**
	 * 职位描述
	 */
	private String description;
	/**
	 * 简历
	 */
	private String resume;
	/**
	 * 配置服务项目id
	 */
	private Long serviceItemsId;
	/**
	 * 预约人数限制
	 */
	private  String numberPeople;
	/**
	 * 联系人
	 */
	private  String linkman;
	/**
	 * 联系人电话
	 */
	private String phone;
	/**
	 * 训练师状态（1生效 0失效）
	 */
	private Integer trainerState;
	
	private String createdUser;
	private String modifiedUser;
	
}
