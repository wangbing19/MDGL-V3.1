package com.vision.pojo.sys;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
@TableName("sys_user")
public class SysUser {
	@TableId(type=IdType.AUTO)
	private Long id;
	//登陆时间
	private Date loginTime;
	//姓名
	private String userName;
	//密码
	private String password;
	//盐  密码加密时前缀，使加密后的值不同
	private String salt;
	//备注
	private String remark;
	//邮件
	private String email;
	//手机号
	private String mobile;
	//用户状态
	private Integer userStatus;
	//顾客用户数量上限设置
	private Integer customerLimit;
	//门店上线数量设置
	private Integer deptLimit;
	//组织id
	private Long organizationId;
	private Date createdTime;
	private String createdUser;
	private Date modifiedTime;
	private String modifiedUser;
	/**组织信息*/
	@TableField(exist=false)
	private String organizationName; 
	@TableField(exist=false)
	private String organizationAddress;;
}
