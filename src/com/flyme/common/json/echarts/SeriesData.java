package com.flyme.common.json.echarts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @description:(Echarts 标注图形数据)
 * 
 */
@JsonInclude(Include.NON_NULL)
public class  SeriesData {
	private String type;
	private String name;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
