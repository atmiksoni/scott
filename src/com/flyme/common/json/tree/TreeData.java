package com.flyme.common.json.tree;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.flyme.common.json.view.TreeView;

public class TreeData implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	@JsonView(TreeView.class)
	private List<?> data;
	@JsonView(TreeView.class)
	private List<String> checkeds;
	@JsonView(TreeView.class)
	private String state = "closed";
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public List<?> getData() {
		return data;
	}
	
	public void setData(List<?> data) {
		this.data = data;
	}
	
	public void setData(List<?> data, String state) {
		this.state = state;
		this.data = data;
	}
	
	public List<String> getCheckeds() {
		return checkeds;
	}
	
	public void setCheckeds(List<String> checkeds) {
		this.checkeds = checkeds;
	}
	
	public void setOpen() {
		this.state = "open";
	}
}
