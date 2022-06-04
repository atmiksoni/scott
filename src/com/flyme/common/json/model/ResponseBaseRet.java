package com.flyme.common.json.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResponseBaseRet {
	public static final int STATUS_OK = 100;
	public static final int STATUS_FAIL = 101;
	int msgcode = STATUS_OK;
	private String msg;
	public String status = "y";

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getMsgcode() {
		return msgcode;
	}

	public void setMsgcode(int msgcode) {
		this.msgcode = msgcode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
