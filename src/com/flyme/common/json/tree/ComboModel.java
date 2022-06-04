package com.flyme.common.json.tree;

public class ComboModel {
	private String value;//值1
	private String text;// 名称
	private String group;// 分组名称
	private Boolean selected = false;// 是否选中状态
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
}