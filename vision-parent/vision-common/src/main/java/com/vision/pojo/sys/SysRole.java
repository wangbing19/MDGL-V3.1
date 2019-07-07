package com.vision.pojo.sys;



import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色PO对象(实体对象，pojo对象)
 * @author ta
 */
@Data
@Accessors(chain=true)
@TableName("sys_role")
public class SysRole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId(type=IdType.AUTO)
	private Integer id;
	//姓名
	private String name;
	//备注
	private String note;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;
	//角色id
	@TableField(exist=false)
	Integer[] menuIds;
	
    
}
