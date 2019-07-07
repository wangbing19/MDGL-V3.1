package com.vision.pojo.sys;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
@TableName("sys_user_role")
public class SysRoleUser {
	@TableId(type=IdType.AUTO)
	private Integer id;
	//用户名
	private Long roleId;
	private String userId;
	

}
