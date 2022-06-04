package com.flyme.app.core.service;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.flyme.base.pay.vo.PrePayRequest;
import com.flyme.base.pay.vo.PrePayResponse;
import com.flyme.base.system.balance.pojo.Balance;
import com.flyme.base.system.balance.service.IBalanceService;
import com.flyme.common.constants.Global;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.context.SysConfigUtil;
import com.flyme.common.util.conver.ConvertUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.thirdsdk.alipay.callback.PayCallBack;
import com.flyme.thirdsdk.alipay.prams.AlipayPrams;
import com.flyme.thirdsdk.alipay.util.AliPayUtil;
import com.flyme.thirdsdk.weixin.config.WxAppPayConfig;
import com.flyme.thirdsdk.weixin.service.WxAppPayService;
import com.github.binarywang.wxpay.bean.WxPayOrderNotifyResponse;
import com.github.binarywang.wxpay.bean.request.WxPayBaseRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;

@Service
public class MyPayService implements IMyPayService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String serverName = SysConfigUtil.getConfigByName("serverName");// 服务器地址
	private Boolean PayTest = ConvertUtils.getBoolean(SysConfigUtil.getConfigByName("PayTest"));// 测试支付

	@Resource(name = "wxAppPayService")
	private WxAppPayService wxAppPayService;// app支付
	@Resource(name = "wxPayService")
	private WxPayService wxPayService;// 公众号支付
	@Autowired
	private WxAppPayConfig wxAppPayConfig;
	@Autowired
	private IBalanceService balanceService;

	@Override
	public ApiJson pay(PrePayRequest prePayInfo) {
		ApiJson j = new ApiJson();
		if(ObjectUtils.isNotEmpty(prePayInfo.getError())){
			return j.setError(prePayInfo.getError());
		}
		if (ObjectUtils.isEmpty(prePayInfo)) {
			return j.setError("参数异常");
		}
		if (prePayInfo.getSuccess() == false) {
			return j.setError(prePayInfo.getBody());
		}
		BigDecimal totalFee = prePayInfo.getTotalFee();
		if (ObjectUtils.isEmpty(totalFee) || ObjectUtils.lezero(totalFee)) {
			return j.setError("订单金额不正确");
		}
		String payType = prePayInfo.getPayType();
		switch (payType) {
			case "WxPay":
				j = WxAppPay(prePayInfo, "/api/wxpay_success.rm");
				break;
			case "AliPay":
				j = Alipay(prePayInfo, "/api/alipay_success.rm");
				break;
			case "WechatPay":
				j = WechatPay(prePayInfo, "/weixin/wechatpay_success.wx");
				break;
			case "Balance":
				j = balanceService.balancePay(prePayInfo);
				break;
		}
		return j;
	}

	@Override
	public ApiJson charge(PrePayRequest prePayInfo) {
		ApiJson j = new ApiJson();
		if (ObjectUtils.isEmpty(prePayInfo)) {
			return j.setError("参数异常");
		}
		BigDecimal totalFee = prePayInfo.getTotalFee();
		if (ObjectUtils.isEmpty(totalFee) || ObjectUtils.lezero(totalFee)) {
			return j.setError("金额不正确");
		}
		String payType = prePayInfo.getPayType();
		switch (payType) {
			case "WxPay":
				j = WxAppPay(prePayInfo, "/api/wxcharge_success.rm");
				break;
			case "AliPay":
				j = Alipay(prePayInfo, "/api/alicharge_success.rm");
				break;
			case "WechatPay":
				j = WechatPay(prePayInfo, "/weixin/wechat_charge_success.wx");
				break;
		}
		return j;
	}

	@Override
	public ApiJson wxCharge(PrePayRequest prePayInfo) {
		ApiJson j = new ApiJson();
		if (ObjectUtils.isEmpty(prePayInfo)) {
			return j.setError("参数异常");
		}
		BigDecimal totalFee = prePayInfo.getTotalFee();
		if (ObjectUtils.isEmpty(totalFee) || ObjectUtils.lezero(totalFee)) {
			return j.setError("充值金额不正确");
		}
		String payType = prePayInfo.getPayType();
		switch (payType) {
			case "WxPay":
				j = WxAppPay(prePayInfo, "/api/wxcharge_success.rm");
				break;
			case "Alipay":
				j = Alipay(prePayInfo, "/api/alicharge_success.rm");
				break;
			case "WechatPay":
				j = WechatPay(prePayInfo, "/weixin/wechat_charge_success.wx");
				break;
		}
		return j;
	}

	/**
	 * (APP) 微信支付/微信充值
	 */
	private ApiJson WxAppPay(PrePayRequest prePayInfo, String callBackUrl) {
		ApiJson j = new ApiJson();
		BigDecimal totalFee = prePayInfo.getTotalFee();
		Map<String, Object> attach = prePayInfo.getAttach();
		String payMoney = (PayTest ? "0.01" : totalFee.toString());
		WxPayUnifiedOrderRequest payInfo = WxPayUnifiedOrderRequest.newBuilder().mchId(wxAppPayConfig.getMchId()).appid(wxAppPayConfig.getAppId())
				.outTradeNo(prePayInfo.getOutTradeNo()).totalFee(WxPayBaseRequest.yuanToFee(payMoney)).attach(JsonUtil.beanToJson(attach)).body(prePayInfo.getBody())
				.tradeType("APP").spbillCreateIp(prePayInfo.getSpbillCreateIp()).notifyURL(serverName + callBackUrl).build();
		try {
			this.wxAppPayService.setConfig(wxAppPayConfig);
			Map<String, String> map = this.wxAppPayService.getPayInfo(payInfo);
			j.setObject(map);
		} catch (WxPayException e) {
			j.setError("支付失败");
			this.logger.error(e.getErrCodeDes());
		}
		return j;
	}

	/**
	 * 微信公众号支付/充值
	 */
	private ApiJson WechatPay(PrePayRequest prePayInfo, String callBackUrl) {
		ApiJson j = new ApiJson();
		BigDecimal totalFee = prePayInfo.getTotalFee();
		Map<String, Object> attach = prePayInfo.getAttach();
		String payMoney = (PayTest ? "0.01" : totalFee.toString());
		WxPayUnifiedOrderRequest payInfo = WxPayUnifiedOrderRequest.newBuilder().openid(prePayInfo.getOpenId()).outTradeNo(prePayInfo.getOutTradeNo())
				.totalFee(WxPayBaseRequest.yuanToFee(payMoney)).attach(JsonUtil.beanToJson(attach)).body(prePayInfo.getBody()).tradeType("JSAPI")
				.spbillCreateIp(prePayInfo.getSpbillCreateIp()).notifyURL(serverName + callBackUrl).build();
		try {
			Map<String, String> map = this.wxPayService.getPayInfo(payInfo);
			j.setObject(map);
		} catch (WxPayException e) {
			j.setError("支付失败");
			this.logger.error(e.getErrCodeDes());
		}
		return j;
	}

	/**
	 * (APP) 支付宝支付/充值
	 */
	private ApiJson Alipay(PrePayRequest prePayInfo, String callBackUrl) {
		ApiJson j = new ApiJson();
		BigDecimal payMoney = (PayTest ? new BigDecimal("0.01") : prePayInfo.getTotalFee());
		AlipayPrams prams = new AlipayPrams();
		prams.setBody(prePayInfo.getBody());
		prams.setSubject(prePayInfo.getBody());
		prams.setOut_trade_no(prePayInfo.getOutTradeNo());
		prams.setTotal_fee(payMoney);
		CriterionMap attach = prePayInfo.getAttach();
		prams.setPassback_params(attach);
		prams.setNotify_url(serverName + callBackUrl);
		j.setObject(AliPayUtil.pay_init(prams));
		return j;
	}
	
	/**
	 * 微信充值回调
	 */
	@Override
	public PrePayResponse wxCharge_success(HttpServletRequest request) {
		PrePayResponse prePayResponse = new PrePayResponse();
		try {
			synchronized (this) {
				System.out.println("========================进入了回调");
				String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
				WxPayOrderNotifyResult result = wxAppPayService.getOrderNotifyResult(xmlResult);
				System.out.println("========================结果转换成功");
				String outTradeNo = result.getOutTradeNo();
				System.out.println("========================获取流水号成功");
				CriterionMap attach = JsonUtil.jsonToMap(result.getAttach(), false);
				System.out.println("========================结果转map成功");
				// 此处要添加充值 version 验证
				String balanceId = attach.get("balanceId");
				System.out.println("========================获取了余额ID");
				Balance balance = balanceService.findById(balanceId);
				Integer localVersion = balance.getVersion();
				Integer remoteVersion = attach.get("version");
				if (localVersion != remoteVersion) {
					throw new Exception("重复回调！");
				}
				System.out.println("========================通过了验证");
				attach.add("intro", "微信充值");
				balanceService.chargeSuccess(attach, outTradeNo);
				prePayResponse.setSuccess(true);
				prePayResponse.setMsg(WxPayOrderNotifyResponse.success("msg:微信充值回调成功"));
			}
		} catch (Exception e) {
			System.out.println("========================回调失败！！");
			logger.error("微信回调结果异常,异常原因{}", e.getMessage());
			prePayResponse.setSuccess(false);
			prePayResponse.setMsg(WxPayOrderNotifyResponse.fail(e.getMessage()));
		}
		return prePayResponse;
	}

	/**
	 * 阿里充值回调
	 */
	@Override
	public PrePayResponse alicharge_success(HttpServletRequest request) {
		PrePayResponse prePayResponse = new PrePayResponse();
		PayCallBack payCallBack = new PayCallBack(request);
		if (payCallBack.success) {
			System.out.println("================进入回调成功！");
			String outTradeNo = payCallBack.getOutTradeNo();
			CriterionMap attach = payCallBack.getPassbackParams();
			// 此处要添加充值 version 验证
			String balanceId = attach.get("balanceId");
			Balance balance = balanceService.findById(balanceId);
			Integer localVersion = balance.getVersion();
			Integer remoteVersion = attach.get("version");
			if (localVersion != remoteVersion) {
				prePayResponse.setSuccess(false);
				prePayResponse.setMsg("重复回调!");
				return prePayResponse;
			}
			attach.add("intro", "支付宝充值");
			balanceService.chargeSuccess(attach, outTradeNo);
			logger.info("支付宝充值回调成功");
			prePayResponse.setSuccess(true);
			prePayResponse.setMsg("msg:支付宝充值回调成功");
		} else {
			System.out.println("================进入回调失败！");
			prePayResponse.setSuccess(false);
			prePayResponse.setMsg("msg:支付宝充值回调失败");
		}
		return prePayResponse;
	}

	@Override
	public PrePayResponse weChatcharge_success(HttpServletRequest request) {
		PrePayResponse prePayResponse = new PrePayResponse();
		try {
			synchronized (this) {
				String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
				WxPayOrderNotifyResult result = wxPayService.getOrderNotifyResult(xmlResult);
				String outTradeNo = result.getOutTradeNo();
				CriterionMap attach = JsonUtil.jsonToMap(result.getAttach(), false);
				// 此处要添加充值 version 验证
				String balanceId = attach.get("balanceId");
				Balance balance = balanceService.findById(balanceId);
				Integer localVersion = balance.getVersion();
				Integer remoteVersion = attach.get("version");
				if (localVersion != remoteVersion) {
					prePayResponse.setSuccess(false);
					prePayResponse.setMsg("重复回调!");
				}
				attach.add("intro", "公众号充值");
				balanceService.chargeSuccess(attach, outTradeNo);
				prePayResponse.setSuccess(true);
				prePayResponse.setMsg(WxPayOrderNotifyResponse.success("msg:公众号充值回调成功"));
			}
		} catch (Exception e) {
			logger.error("微信回调结果异常,异常原因{}", e.getMessage());
			prePayResponse.setSuccess(false);
			prePayResponse.setMsg(WxPayOrderNotifyResponse.fail(e.getMessage()));
		}
		return prePayResponse;
	}

	/**
	 * 微信支付回调
	 */
	@Override
	public PrePayResponse wxpay_success(HttpServletRequest request) {
		PrePayResponse prePayResponse = new PrePayResponse();
		try {
			synchronized (this) {
				String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
				WxPayOrderNotifyResult result = wxAppPayService.getOrderNotifyResult(xmlResult);
				String outTradeNo = result.getOutTradeNo();
				CriterionMap attach = JsonUtil.jsonToMap(result.getAttach(), false);
				// 此处要添加已支付验证
				String orderbusId = attach.get("orderbusId");
				String orderType= attach.get("orderType");
				switch (orderType) {
				case "nursing"://护理
					if(ObjectUtils.isNotEmpty(orderbusId)){
					
					
						prePayResponse.setSuccess(true);
						prePayResponse.setMsg(WxPayOrderNotifyResponse.success("处理成功!"));
					}else {
						prePayResponse.setSuccess(false);
						prePayResponse.setMsg("未找到订单ID！");
						return prePayResponse;
					}
					break;
				case "drug"://药品
					
					if(ObjectUtils.isNotEmpty(orderbusId)){
					

						prePayResponse.setSuccess(true);
						prePayResponse.setMsg(WxPayOrderNotifyResponse.success("处理成功!"));
					}else {
						prePayResponse.setSuccess(false);
						prePayResponse.setMsg("未找到订单ID！");
						return prePayResponse;
					}
					break;
				case "MedicalServiceOrder"://医疗
					if(ObjectUtils.isNotEmpty(orderbusId)){
						
					
						prePayResponse.setSuccess(true);
						prePayResponse.setMsg(WxPayOrderNotifyResponse.success("处理成功!"));
					}else {
						prePayResponse.setSuccess(false);
						prePayResponse.setMsg("未找到订单ID！");
						return prePayResponse;
					}
					break;
				case "PackagesOrder"://套餐
					if(ObjectUtils.isNotEmpty(orderbusId)){
					
						
						prePayResponse.setSuccess(true);
						prePayResponse.setMsg(WxPayOrderNotifyResponse.success("处理成功!"));
					}else {
						prePayResponse.setSuccess(false);
						prePayResponse.setMsg("未找到订单ID！");
						return prePayResponse;
					}
					break;
				default:
					prePayResponse.setSuccess(false);
					prePayResponse.setMsg("未找到订单类型！");
					return prePayResponse;
				}
			}
		} catch (Exception e) {
			logger.error("微信回调结果异常,异常原因{}", e.getMessage());
			prePayResponse.setSuccess(false);
			prePayResponse.setMsg(WxPayOrderNotifyResponse.fail(e.getMessage()));
		}
		return prePayResponse;
	}

	@Override
	public PrePayResponse wechatpay_success(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 支付宝支付回调
	 */
	@Override
	public PrePayResponse alipay_success(HttpServletRequest request) {
		PrePayResponse prePayResponse = new PrePayResponse();
		PayCallBack payCallBack = new PayCallBack(request);
		if (payCallBack.success) {
			String outTradeNo = payCallBack.getOutTradeNo();
			CriterionMap attach = payCallBack.getPassbackParams();
			// 此处要添加已支付验证
			String orderbusId = attach.get("orderbusId");
			String orderType= attach.get("orderType");
			switch (orderType) {
				case "nursing"://护理
					if(ObjectUtils.isNotEmpty(orderbusId)){
						
					
						prePayResponse.setSuccess(true);
						prePayResponse.setMsg("success");
					}else {
						prePayResponse.setSuccess(false);
						prePayResponse.setMsg("未找到订单ID！");
						return prePayResponse;
					}
					break;
				case "drug"://药品
					if(ObjectUtils.isNotEmpty(orderbusId)){
					
						
						prePayResponse.setSuccess(true);
						prePayResponse.setMsg(WxPayOrderNotifyResponse.success("处理成功!"));
					}else {
						prePayResponse.setSuccess(false);
						prePayResponse.setMsg("未找到订单ID！");
						return prePayResponse;
					}
					break;
				case "medical"://医疗
					
					break;
	
				default:
					prePayResponse.setSuccess(false);
					prePayResponse.setMsg("未找到订单类型！");
					return prePayResponse;
			}
		} else {
			prePayResponse.setSuccess(false);
			prePayResponse.setMsg("failure");
		}
		return prePayResponse;
	}

}
