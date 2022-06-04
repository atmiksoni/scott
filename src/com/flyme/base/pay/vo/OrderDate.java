package com.flyme.base.pay.vo;

import java.util.List;

/******
 * 取货日期
 * 2017/07/28
 */
public class OrderDate {

	/** 日期Id*/
	private Integer dateId;
	/** 显示在app的日期字符串 */
	private String dateString;
	/** 记录在数据库的日期*/
	private String dateRecord;
	/** 星期几 */
	private String week;
	/** 时间段列表 */
	private List<OrderTime> orderTimes;
	/** 今天标记  */
	private Integer isToday;

	
	public Integer getDateId() {
		return dateId;
	}
	public void setDateId(Integer dateId) {
		this.dateId = dateId;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	public String getDateRecord() {
		return dateRecord;
	}
	public void setDateRecord(String dateRecord) {
		this.dateRecord = dateRecord;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public List<OrderTime> getOrderTimes() {
		return orderTimes;
	}
	public void setOrderTimes(List<OrderTime> orderTimes) {
		this.orderTimes = orderTimes;
	}
	public Integer getIsToday() {
		return isToday;
	}
	public void setIsToday(Integer isToday) {
		this.isToday = isToday;
	}
	
	
	
}
