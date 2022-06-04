package com.flyme.base.pay.vo;

import java.io.Serializable;

import com.flyme.common.util.map.CriterionMap;


/**
 * 支付结果通知
 */
public class PrePayResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private Boolean success = true;
	private String msg;
	private String outTradeNo;
	private CriterionMap attach;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public CriterionMap getAttach() {
		return attach;
	}

	public void setAttach(CriterionMap attach) {
		this.attach = attach;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
