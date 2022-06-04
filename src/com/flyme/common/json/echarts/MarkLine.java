package com.flyme.common.json.echarts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @description:(Echarts 标线)
 * 
 */
@JsonInclude(Include.NON_NULL)
public class MarkLine {

	private SeriesData date;

	public SeriesData getDate() {
		return date;
	}

	public void setDate(SeriesData date) {
		this.date = date;
	}
}
