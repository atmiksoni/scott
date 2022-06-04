package com.flyme.common.json.model;
/**
 * 日程模型
 * @author Administrator
 *
 */
public class Calendar {
	private String id;
	private String title;
	private String start;
	private String textColor="#ffffff";
	private Boolean allDay=true;
	private String color="#ccaaaa";
	private String end;
	private String className;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getAllDay() {
		return allDay;
	}
	public void setAllDay(Boolean allDay) {
		this.allDay = allDay;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getTextColor() {
		return textColor;
	}
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}

}
