package com.vision.pojo.sys;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
@TableName("sys_user")
public class SysUser {
	@TableId(type=IdType.AUTO)
	private Long userId;
	private Date loginTime;
	private String userName;
	private String password;
	private String salt;
	private String remark;
	private String email;
	private String mobile;
	private Integer userStatus;
	private Integer customerLimit;
	private Integer deptLimit;
	private Date createdTime;
	private Long organizationId;
	private String createdUser;
	private Date modifiedTime;
	private String modifiedUser;
}
