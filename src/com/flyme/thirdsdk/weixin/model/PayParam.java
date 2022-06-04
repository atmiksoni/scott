package com.flyme.thirdsdk.weixin.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayParam implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * appId
	 */
	private String appId = "";
	/**
	 * 商户号
	 */
	private String partnerId = "";
	/**
	 * 订单Id
	 */
	private String payInfoId = "";
	/**
	 * 标题
	 */
	private String title = "";
	/**
	 * 账单信息
	 */
	private String body = "";
	/**
	 * 支付人信息1
	 */
	private String payParams1 = "";
	/**
	 * 支付人信息2
	 */
	private String payParams2 = "";
	/**
	 * 账单金额
	 */
	private BigDecimal total = new BigDecimal("0.00");

	/**
	 * 支付签名
	 */
	private String sign = "";
	/**
	 * 支付方式
	 */
	private Integer payType = 1;

	/**
	 * 微信预支付Id
	 */
	private String prepayId = "";
	/**
	 * 随机数
	 */
	private String nonceStr = "";
	/**
	 * 时间戳
	 */
	private String timeStamp = "";
	private String msg = "";
	private String packageValue = "";
	private Boolean success = false;

	public String getPayInfoId() {
		return payInfoId;
	}

	public void setPayInfoId(String payInfoId) {
		this.payInfoId = payInfoId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPayParams1() {
		return payParams1;
	}

	public void setPayParams1(String payParams1) {
		this.payParams1 = payParams1;
	}

	public String getPayParams2() {
		return payParams2;
	}

	public void setPayParams2(String payParams2) {
		this.payParams2 = payParams2;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPackageValue() {
		return packageValue;
	}

	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

}
