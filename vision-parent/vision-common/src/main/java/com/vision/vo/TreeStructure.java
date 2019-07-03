package com.vision.vo;

import java.util.List;

public class TreeStructure<T> {
	private Long id;
	private Long parentId;
	private T data;
	private List<TreeStructure<T>> list;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public List<TreeStructure<T>> getList() {
		return list;
	}
	public void setList(List<TreeStructure<T>> list) {
		this.list = list;
	}
	
}
