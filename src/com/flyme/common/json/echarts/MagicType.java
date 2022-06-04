package com.flyme.common.json.echarts;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @description:(Echarts动态类型切换)
 * 
 */
@JsonInclude(Include.NON_NULL)
public class MagicType {
	private boolean show=false;
	private Object type=null;
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}
	public Object getType() {
		return type;
	}
	public void setType(Object type) {
		this.type = type;
	}
}
