package com.flyme.common.json.echarts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 *@description:( Echarts提示框)
 *
 */
@JsonInclude(Include.NON_NULL)
public class Tooltip {

	private String trigger;//触发类型

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
}
