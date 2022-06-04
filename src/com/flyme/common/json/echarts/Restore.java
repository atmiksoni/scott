package com.flyme.common.json.echarts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @description:(Echarts还原，复位原始图表)
 * 
 */
@JsonInclude(Include.NON_NULL)
public class Restore { 
	private boolean show=true;

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
}
