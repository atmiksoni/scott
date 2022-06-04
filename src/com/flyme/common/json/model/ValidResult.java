package com.flyme.common.json.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 服务器端验证返回结果
 */
@JsonInclude(Include.NON_NULL)
public class ValidResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ok;// 是否成功
	private String error;// 提示信息

	public String getOk() {
		return ok;
	}

	public String getError() {
		return error;
	}

	public void setOk(String ok) {
		this.ok = ok;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setOk() {
		this.ok = "验证通过";
	}

}
