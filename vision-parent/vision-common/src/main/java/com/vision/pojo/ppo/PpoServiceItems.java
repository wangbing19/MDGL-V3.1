package com.vision.pojo.ppo;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.vision.pojo.BaseNewPojo;
import com.vision.pojo.BasePojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("ppo_service_items")
public class PpoServiceItems extends BaseNewPojo {
	@TableId(type=IdType.AUTO)
	private Long id;
	
				
	private Long organizationId;
	private String serviceName; 
	private String serviceContent;
	private String serviceState;
	private String serviceRemark;
	private String createdUser;
	private String modifiedUser;
}
