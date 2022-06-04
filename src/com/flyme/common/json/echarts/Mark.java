package com.flyme.common.json.echarts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @description:(Echarts辅助线标志)
 * 
 */
@JsonInclude(Include.NON_NULL)
public class Mark {
	private boolean show=true;

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
}
