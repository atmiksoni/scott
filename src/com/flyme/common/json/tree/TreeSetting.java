package com.flyme.common.json.tree;

/**
 * Tree模型配置
 */
public class TreeSetting {
	private String url;// 链接地址固定值
	private String target;// 连接目标
	private Boolean hasChild=false;
	private String state = "closed";

	public Boolean getHasChild() {
		return hasChild;
	}

	public void setHasChild(Boolean hasChild) {
		this.hasChild = hasChild;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
