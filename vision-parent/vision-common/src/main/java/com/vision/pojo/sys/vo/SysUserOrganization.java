package com.vision.pojo.sys.vo;
import java.util.Date;
import java.util.List;

import com.vision.pojo.sys.SysOrganization;

import lombok.Data;
import lombok.experimental.Accessors;
/***
 * 借助此对象封装角色和菜单相关信息
 * @author ta
 */

@Data
@Accessors(chain=true)
public class SysUserOrganization {
	
	
	private Long id;
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
	private SysOrganization sysOrganization;
}
