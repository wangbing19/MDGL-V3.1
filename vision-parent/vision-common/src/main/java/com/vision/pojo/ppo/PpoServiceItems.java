package com.vision.pojo.ppo;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.vision.pojo.BasePojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("ppo_service_items")
public class PpoServiceItems extends BasePojo {
	@TableId(type=IdType.AUTO)
	private Long itemId;
	private Long organizationId;
	private String serviceName; 
	private String serviceContent;
	private String serviceState;
	private Integer serviceRemark;
	private String createdUser;
	private String modifiedUser;
}
