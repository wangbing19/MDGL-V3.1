package com.vision.vo;

import java.io.Serializable;
/**
 * 用于封装数据的节点对象
 * 1)sys_menus (存储的是菜单信息)
 * 2)sys_role_menus(存储的是菜单和角色的关系数据)
 */
public class Node2 implements Serializable{
	private static final long serialVersionUID = 4351174414771192644L;
	/**节点id(例如菜单id)*/
	private Long organizationId;
	/**节点名(例如菜单名称)*/
	private String organizationName;
	/**父节点id(例如菜单的上级菜单id)*/
	private Long organizationParentId;
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	public Long getOrganizationParentId() {
		return organizationParentId;
	}
	public void setOrganizationParentId(Long organizationParentId) {
		this.organizationParentId = organizationParentId;
	}
	
	 
}
