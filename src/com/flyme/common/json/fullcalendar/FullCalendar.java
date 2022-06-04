package com.flyme.common.json.fullcalendar;

import com.flyme.common.json.pqgrid.PqGrid;

public class FullCalendar {
	private String title;/* 日历内容 */
	private String start;/* 开始时间 */
	private String end;/* 结束时间 */
	private boolean allDay;/* 全天时间 */
	private PqGrid pqGrid;

	public PqGrid getPqGrid() {
		return pqGrid;
	}

	public void setPqGrid(PqGrid pqGrid) {
		this.pqGrid = pqGrid;
	}

	public String getTitle() {
		return title;
	}

	public FullCalendar setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getStart() {
		return start;
	}

	public FullCalendar setStart(String start) {
		this.start = start;
		return this;
	}

	public String getEnd() {
		return end;
	}

	public FullCalendar setEnd(String end) {
		this.end = end;
		return this;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public FullCalendar setAllDay(boolean allDay) {
		this.allDay = allDay;
		return this;
	}

}
