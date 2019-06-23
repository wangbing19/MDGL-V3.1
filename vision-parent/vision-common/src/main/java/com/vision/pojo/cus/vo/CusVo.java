package com.vision.pojo.cus.vo;

import lombok.Data;

@Data
public class CusVo {

	/**id*/
	private Integer id;
	/**状态state*/
	private Integer state;
	/**姓名*/
	private String name;
	/**电话*/
	private String tel;
	/**起始页码值*/
	private Integer pageCurrent;
	 /**门店id*/
    private Integer orgId;
    /**登录用户*/
    private String user;
	/** 页面大小 */
    private Integer pageSize;
    /** 用户id */
    private Integer customerId;
    
	
}
