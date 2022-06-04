package com.flyme.common.json.echarts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @description:(Echarts标题模型)
 *
 */
@JsonInclude(Include.NON_NULL)
public class Title {
	private String text;//主标题文本
	private String subtext;//副标题文本
	
	
	public String getSubtext() {
		return subtext;
	}
	public void setSubtext(String subtext) {
		this.subtext = subtext;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	

}
