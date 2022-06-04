package com.flyme.app.core.service;

import javax.servlet.http.HttpServletRequest;

import com.flyme.base.pay.vo.PrePayRequest;
import com.flyme.base.pay.vo.PrePayResponse;
import com.flyme.common.json.model.ApiJson;


public interface IMyPayService {

	/********************  订单支付   ********************/
	public ApiJson pay(PrePayRequest prePayInfo);

	/********************  充值提交   ********************/
	public ApiJson charge(PrePayRequest prePayInfo);
	public ApiJson wxCharge(PrePayRequest prePayInfo);
	
	
	
	
	/**********************************************************
	 |			                                       订单支付回调						  |
	 *********************************************************/
	/** 微信支付成功回调  */
	public PrePayResponse wxpay_success(HttpServletRequest request);

	/** 微信公众号支付成功回调  */
	public PrePayResponse wechatpay_success(HttpServletRequest request);

	/** 支付宝支付成功回调 */
	public PrePayResponse alipay_success(HttpServletRequest request);
	
	
	
	
	/**********************************************************
	 |			                                       余额充值回调						  |
	 *********************************************************/
	/** 微信充值成功回调 */
	public PrePayResponse wxCharge_success(HttpServletRequest request);

	/** 支付宝支付成功回调 */
	public PrePayResponse alicharge_success(HttpServletRequest request);
	
	/** 微信公众号充值成功回调 */
	public PrePayResponse weChatcharge_success(HttpServletRequest request);

}
