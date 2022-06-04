package com.flyme.common.json.model;

import java.io.Serializable;

/**
 * 对象选取操作模型
 */
public class CheckHas implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 对象ID */
	private String id;
	/* 操作 */
	private String opt;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getOpt() {
		return opt;
	}
	
	public void setOpt(String opt) {
		this.opt = opt;
	}
	
	public static String OPT_ADD = "add";
	public static String OPT_DEL = "del";
	
}
