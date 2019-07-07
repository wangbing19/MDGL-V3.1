package com.vision.pojo.sys;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.vision.pojo.BasePojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@TableName("sys_organization")
public class SysOrganization extends BasePojo {
	@TableId(type=IdType.AUTO)
	private Long organizationId;
	private String organizationName; 
	private String organizationAddress;
	private Long organizationParentId;
	private Integer organizationSort;
	private String organizationNote;
	/**用户上限*/
	private Integer toplimit;
	/**剩余*/
	private Integer surplus;
}
