package com.vision.vo;

import java.util.List;

public class TreeMenus<T> {

	private Long key;
	private String title;
	private Long value;
	private Long pId;
	private List<TreeMenus<T>> children;
	public Long getKey() {
		return key;
	}
	public void setKey(Long key) {
		this.key = key;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	

	

	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	public List<TreeMenus<T>> getChildren() {
		return children;
	}
	public void setChildren(List<TreeMenus<T>> children) {
		this.children = children;
	}
	
	
	

}
