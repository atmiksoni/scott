package com.flyme.common.json.echarts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @description:(Echarts工具箱)
 * 
 */
@JsonInclude(Include.NON_NULL)
public class Toolbox {
	private boolean show=true;//显示策略，可选为：true（显示） | false（隐藏）
	private Feature feature=new Feature();
	
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public Feature getFeature() {
		return feature;
	}
	public void setFeature(Feature feature) {
		this.feature = feature;
	}
}
