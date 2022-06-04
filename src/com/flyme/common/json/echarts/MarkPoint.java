package com.flyme.common.json.echarts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @description:(Echarts 标注)
 * 
 */
@JsonInclude(Include.NON_NULL)
public class MarkPoint {

	private SeriesData data;

	public SeriesData getData() {
		return data;
	}

	public void setData(SeriesData data) {
		this.data = data;
	}
}
