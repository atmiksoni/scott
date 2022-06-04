package com.flyme.base.pay.vo;

import java.math.BigDecimal;

/**
 * 退款参数
 */
public class RefundRequest {
	/**
	 * 商户订单号
	 */
	private String outTradeNo;
	/**
	 * 商户退款单号
	 */
	private String outRefundNo;
	/**
	 * 订单金额
	 */
	private BigDecimal totalFee;
	/**
	 * 退款金额
	 */
	private BigDecimal refundFee;

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(BigDecimal refundFee) {
		this.refundFee = refundFee;
	}
}
