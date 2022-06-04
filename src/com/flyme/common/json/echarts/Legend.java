package com.flyme.common.json.echarts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @description:(Echarts图例)
 *
 */
@JsonInclude(Include.NON_NULL)
public class Legend {
	private Object data;//图例内容数组

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
