package com.flyme.common.json.echarts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
/**
 * @description:(Echarts Y坐标轴)
 * 
 */
@JsonInclude(Include.NON_NULL)
public class YAxis {

	private String type="value";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
