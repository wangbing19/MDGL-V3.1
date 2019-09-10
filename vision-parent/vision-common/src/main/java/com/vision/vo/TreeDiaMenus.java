package com.vision.vo;

import java.util.List;

public class TreeDiaMenus<T> {
	private Long id;
	private Long key;
	private String title;
	private String value;
	private Long pId;
	private List<TreeDiaMenus<T>> children;
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
	

	


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	public List<TreeDiaMenus<T>> getChildren() {
		return children;
	}
	public void setChildren(List<TreeDiaMenus<T>> children) {
		this.children = children;
	}
	
	
	

}
