package com.flyme.common.json.model;

import java.io.Serializable;

/**
 * 排序操作模型
 */
public class OrderModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private int oo;
	private String id;
	private int no;

	public int getOo() {
		return oo;
	}

	public void setOo(int oo) {
		this.oo = oo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

}
