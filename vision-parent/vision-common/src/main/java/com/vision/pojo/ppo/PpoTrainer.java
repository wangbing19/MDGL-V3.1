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
	private Long tarinerId;
	private Long organizationId; 
	private String trainerName;
	private String trainerGender;
	private String post;
	private String description;
	private String resume;
	private Long serviceItemsId;
	private Integer numberPeople;
	private  String linkman;
	private Integer phone;
	private Integer trainerState;
	private String createdUser;
	private String modifiedUser;
	
}
