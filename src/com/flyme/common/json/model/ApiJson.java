package com.flyme.common.json.model;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;
import com.flyme.common.constants.Global;
import com.flyme.common.json.view.AjaxView;
import com.flyme.common.util.ObjectUtils;

/**
 * Ajax请求返回的JSON对象
 */
@JsonInclude(Include.NON_NULL)
public class ApiJson implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 提示信息 */
	@JsonView(AjaxView.class)
	private String info = "";
	/* 状态码(100:成功,101:失败,102:登录超时) */
	@JsonView(AjaxView.class)
	private Integer code = 100;
	/* 返回对象 */
	@JsonView(AjaxView.class)
	private Object object;
	/* 是否关闭窗口 */
	private Boolean close;
	private Boolean refrsh = true;// 是否刷新父页面
	/* 其他参数 */
	private Map<String, Object> attributes;
	private String remark;

	public ApiJson() {

	}

	public String getInfo() {
		return info;
	}

	public ApiJson setInfo(String info) {
		this.info = info;
		this.code = Global.CODE_OK;
		this.setClose(true);
		return this;
	}

	public ApiJson setAppInfo(String info) {
		this.info = info;
		this.code = Global.CODE_OK;
		return this;
	}

	public ApiJson setAppError(String info) {
		this.info = info;
		this.code = Global.CODE_FAIL;
		return this;
	}

	public ApiJson setError(String info) {
		this.info = info;
		this.code = Global.CODE_FAIL;
		this.setClose(false);
		return this;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@SuppressWarnings("unchecked")
	public <T> T getObject() {
		return (T) object;
	}

	public void setObject(Object object) {
		if (ObjectUtils.isEmpty(object)) {
			object = new Object();
		}
		this.object = object;
	}
	
	public void setObject(Object object,boolean flag) {
		if(!flag){
			if (ObjectUtils.isEmpty(object)) {
				object = new Object();
			}
		}
		this.object = object;
	}
	
	public Boolean getClose() {
		return close;
	}

	public void setClose(Boolean close) {
		this.close = close;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean success() {
		Boolean tag = false;
		if (code.equals(Global.CODE_OK)) {
			tag = true;
		}
		return tag;
	}

	public Boolean fail() {
		Boolean tag = false;
		if (code.equals(Global.CODE_FAIL)) {
			tag = true;
		}
		return tag;
	}

	public Boolean getRefrsh() {
		return refrsh;
	}

	public void setRefrsh(Boolean refrsh) {
		this.refrsh = refrsh;
	}
}