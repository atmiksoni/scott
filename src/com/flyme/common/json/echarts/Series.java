package com.flyme.common.json.echarts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @description:(Echarts 图标)
 */
@JsonInclude(Include.NON_NULL)
public class Series {
	
	private String name;
	private String type;
	private Object data;
	private MarkPoint markPoint;
	private MarkLine markLine;
	
	/* 柱形图属性 */
	private String barGap;// 柱形图柱子间距
	private String barWidth;// 柱形图柱子宽度
	private String barCategoryGap;// 类目间柱形距离
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public MarkPoint getMarkPoint() {
		return markPoint;
	}
	
	public void setMarkPoint(MarkPoint markPoint) {
		this.markPoint = markPoint;
	}
	
	public MarkLine getMarkLine() {
		return markLine;
	}
	
	public void setMarkLine(MarkLine markLine) {
		this.markLine = markLine;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	public String getBarGap() {
		return barGap;
	}
	
	public void setBarGap(String barGap) {
		this.barGap = barGap;
	}
	
	public String getBarWidth() {
		return barWidth;
	}
	
	public void setBarWidth(String barWidth) {
		this.barWidth = barWidth;
	}

	public String getBarCategoryGap() {
		return barCategoryGap;
	}

	public void setBarCategoryGap(String barCategoryGap) {
		this.barCategoryGap = barCategoryGap;
	}
	
}
