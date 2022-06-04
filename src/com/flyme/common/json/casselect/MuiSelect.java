package com.flyme.common.json.casselect;

import java.util.List;

public class MuiSelect {
	private String text;
	private String value;
	private List<MuiSelect> children;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<MuiSelect> getChildren() {
		return children;
	}
	public void setChildren(List<MuiSelect> children) {
		this.children = children;
	}

	
}
