package com.flyme.common.json.echarts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @description:(Echarts模型)
 * 
 */
@JsonInclude(Include.NON_NULL)
public class Echarts implements Serializable {
	private static final long serialVersionUID = 1L;
	private Object lData;
	private Object xData;
	private List<Series> series = new ArrayList<Series>();

	public Object getlData() {
		return lData;
	}

	public void setlData(Object lData) {
		this.lData = lData;
	}

	public Object getxData() {
		return xData;
	}

	public void setxData(Object xData) {
		this.xData = xData;
	}

	public List<Series> getSeries() {
		return series;
	}

	public void setSeries(List<Series> series) {
		this.series = series;
	}

}
