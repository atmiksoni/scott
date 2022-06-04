package com.flyme.base.pay.vo;

/**********
 *订单预约时间列表 
 ******/
public class OrderTime {
	
	/** id*/
	private Integer timeId;
	/** app显示时间区间字符串  */
	private String timeString;
	/** 启用状态 */
	private Integer isAble;
	
	
	public Integer getTimeId() {
		return timeId;
	}
	public void setTimeId(Integer timeId) {
		this.timeId = timeId;
	}
	public String getTimeString() {
		return timeString;
	}
	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}
	public Integer getIsAble() {
		return isAble;
	}
	public void setIsAble(Integer isAble) {
		this.isAble = isAble;
	}
	
}
