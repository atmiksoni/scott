package com.flyme.base.pay.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.common.util.map.CriterionMap;

/**
 * 支付参数
 */
public class PrePayRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private Boolean success = true;
	private Account account;
	private String openId = "";// 微信openId
	private String outTradeNo = "";// 订单号
	private BigDecimal totalFee;// 订单金额
	private String body;// 商品描述
	private String tradeType = "JSAPI";// 支付类型
	private String spbillCreateIp;// 客户端IP
	private String orderType;// 业务类型
	private String payType;// 支付类型;
	private CriterionMap attach;
	private String error;// 错误信息;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public CriterionMap getAttach() {
		return attach;
	}

	public void setAttach(CriterionMap attach) {
		this.attach = attach;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
