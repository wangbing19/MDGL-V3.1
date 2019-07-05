package com.vision.pojo.sys.vo;

import java.util.Date;



import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
public class SysOrganizationLogs {
	private Integer id;
	//用户名
	private Long organizationId;
	private String username;
	//用户操作
	private String operation;
	//请求方法
	private String method;
	//请求参数
	private String params;
	//执行时长(毫秒)
	private Long time;
	//IP地址
	private String ip;
	//创建时间
	private Date createdTime;
	private String organizationName;
	private String organizationAddress;
}
