package com.vision.pojo.ppo.vo;

import java.util.Date;

import com.vision.pojo.BasePojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PpoItemsOrganization  extends BasePojo{
	private Long itemId;
	private String serviceName; 
	private String serviceContent;
	private String serviceState;
	private Integer serviceRemark;
	private String createdUser;
	private String modifiedUser;
	private String organizationName;
	private String organizationAddress;
	
}
