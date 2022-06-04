package com.flyme.common.json.model;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * AJAX请求时返回的JSON对象
 */
@JsonInclude(Include.NON_NULL)
public class AjaxJson implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String alerttype;// 弹出框方式
	private Map<String, Object> attributes;// 其他参数
	private String href;//回调地址
	private String callback;// 回调函数
	private String status = "y";// 是否成功
	private boolean success = true;
	private Boolean close = true;// 是否关闭窗口
	private String msg;// 提示信息
	private Object object;// 返回对象
	private Object[] ids;// 返回数组
	private String theme;
	private String type;
	private Boolean refrsh = true;// 是否刷新父页面
	private String remark;

	public AjaxJson() {
	}

	public AjaxJson(ApiJson j) {
		if (j.success()) {
			this.setInfo(j.getInfo());
			this.setObject(j.getObject());
		} else {
			this.setError(j.getInfo());
		}
	}

	public AjaxJson setApi(ApiJson j) {
		if (j.success()) {
			this.setInfo(j.getInfo());
			this.setObject(j.getObject());
		} else {
			this.setError(j.getInfo());
		}
		return this;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * 设置其他参数
	 */
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getAlerttype() {
		return alerttype;
	}

	/**
	 * 设置弹出框类型可选tip,alert
	 */
	public void setAlerttype(String alerttype) {
		this.alerttype = alerttype;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setInfo(String msg, String status) {
		this.msg = msg;
		this.type = "success";
		this.status = status;
	}

	public AjaxJson setInfo(String msg) {
		this.msg = msg;
		this.type = "success";
		return this;
	}

	public void setInfoAndRemark(String msg, String remark) {
		this.msg = msg;
		this.remark = remark;
		this.type = "success";
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object[] getIds() {
		return ids;
	}

	public void setIds(Object[] ids) {
		this.ids = ids;
	}

	public AjaxJson setError(String msg) {
		this.msg = msg;
		this.status = "n";
		this.type = "error";
		this.success = false;
		return this;
	}

	public Boolean getClose() {
		return close;
	}

	public void setClose(Boolean close) {
		this.close = close;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setZuiSuccess(String msg) {
		this.theme = "zui";
		this.msg = msg;
		this.type = "success";
	}

	public void setZuiError(String msg) {
		this.theme = "zui";
		this.msg = msg;
		this.type = "warning";
		this.success = false;
	}

	public Boolean getRefrsh() {
		return refrsh;
	}

	public void setRefrsh(Boolean refrsh) {
		this.refrsh = refrsh;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}