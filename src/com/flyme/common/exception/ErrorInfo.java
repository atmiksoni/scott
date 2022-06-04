package com.flyme.common.exception;

import com.flyme.common.util.ObjectUtils;

public class ErrorInfo {
	private String status = "n";
	private String info = "操作异常";// 错r误信息

	public ErrorInfo() {

	}

	public ErrorInfo(Exception ex) {
		this.info = getErrorMessage(ex);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	private String getErrorMessage(Exception ex) {
		String error = ex.getMessage();
		if (!ObjectUtils.isEmpty(error)) {
			if (error.indexOf("Mapped Statements collection does not contain value for") > 0) {
				String method = error.substring(error.lastIndexOf("for") + 3);
				this.info = method + "方法不存在";
			}
			if (error.indexOf("There is no getter for property named") > 0) {
				String field = error.substring(error.lastIndexOf("named") + 5, error.lastIndexOf("in"));
				String pojo = error.substring(error.lastIndexOf("pojo.") + 5, error.length() - 1);
				this.info = field + "字段在实体" + pojo + "中未定义";
			}
			if (error.indexOf("Could not write JSON: Could not set property") > -1) {
				String field = error.substring(error.lastIndexOf("set property") + 13, error.lastIndexOf("of"));
				this.info = field + "写入JSON错误,字段不存在";
			}
			if (error.indexOf("/ by zero") > -1) {
				this.info = "整数除以0";
			}
			if (error.indexOf("Cannot invoke method multiply() on null object") > -1) {
				this.info = "公式错误";
			}
			if (error.indexOf("Data too long") > -1) {
				this.info = "字段长度异常";
			}
			if (error.indexOf("不能将值 NULL 插入列") > -1) {
				this.info = "数据库插入异常:非空限制";
			}
			if (error.indexOf("selectOne()") > -1) {
				this.info = "存在相同的用户名";
			}
			if (error.indexOf("no getter for property named") > -1) {
				String field = error.substring(error.indexOf("no"));
				this.info = field;
			} else {
				this.info = error;
			}
		}
		return info;

	}
}
