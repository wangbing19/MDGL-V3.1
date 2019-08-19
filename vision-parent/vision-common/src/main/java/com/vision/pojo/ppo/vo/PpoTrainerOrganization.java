package com.vision.pojo.ppo.vo;



import com.baomidou.mybatisplus.annotation.IdType;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.vision.pojo.BasePojo;
import com.vision.pojo.pre.SymptomDesc;
import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
public class PpoTrainerOrganization extends BasePojo{
	private Long id;
	private Long organizationId; 
	private String trainerName;
	private String trainerGender;
	private String post;
	private String description;
	private String resume;
	private Long serviceItemsId;
	private Integer numberPeople;
	private  String linkman;
	private String phone;
	private Integer trainerState;
	private String createdUser;
	private String modifiedUser;
	private String organizationName;
	private String organizationAddress;
	
}
