package com.flyme.thirdsdk.weixin.util;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flyme.common.json.model.ApiJson;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.context.PcUtils;
import com.flyme.common.util.date.DateUtil;
import com.flyme.thirdsdk.weixin.http.ClientCustomSSL;
import com.flyme.thirdsdk.weixin.http.GetWxOrderno;
import com.flyme.thirdsdk.weixin.model.PayParam;
import com.flyme.thirdsdk.weixin.model.RequestHandler;

/**
 * 微信支付工具类
 */
public class WxPayUtil {
	public final static String PAY_UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";// 统一下单
	public final static String PAY_REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";// 退款
	public final static String appid = "";// APP
	public final static String mch_id = "";// 商户ID
	public final static String mch_key = "";// 商户key
	public final static String appsecret = "";// secret

	/**
	 * 微信支付下单（公众号）
	 */
	public static ApiJson getPayInfo(String payInfoId, String body, Integer totalFee, String notifyUrl, String openId) {
		ApiJson j = new ApiJson();
		/*
		 * String nonceStr = ObjectUtils.getWxRandom(); String timeStamp =
		 * DateUtil.getTimeStamp(); UnifiedorderRequest request = new
		 * UnifiedorderRequest(); request.setTotal_fee(totalFee);
		 * request.setBody(body); request.setOut_trade_no(payInfoId);
		 * request.setProduct_id(payInfoId); request.setNonce_str(nonceStr);
		 * request.setNotify_url(notifyUrl); request.setOpenid(openId);
		 * request.setTrade_type("JSAPI");
		 * request.setSpbill_create_ip("127.0.0.1"); try { UnifiedorderResponse
		 * response = PayManager.unifiedorder(request); String result_code =
		 * response.getResult_code(); if (result_code.equals(Global.WX_SUCCESS))
		 * { String prepayId = response.getPrepay_id(); timeStamp =
		 * DateUtil.getTimeStamp(); H5PayParam payParam =
		 * PayManager.buildH5PayConfig(timeStamp, nonceStr, prepayId);
		 * j.setObject(payParam); } else { j.setError(response.getErr_code()); }
		 * } catch (Exception e) { j.setError("创建订单失败"); e.printStackTrace(); }
		 */
		return j;
	}

	/**
	 * 微信支付下单
	 */
	public static ApiJson pay_init(HttpServletRequest request, HttpServletResponse response, String out_trade_no, String body, Integer total_fee, String notify_url, String attach) {
		ApiJson j = new ApiJson();
		PayParam pi = new PayParam();
		String nonce_str = TenpayUtil.getNonceStr();
		String spbill_create_ip = PcUtils.getIpAddr();
		String trade_type = "APP";
		// 对以下字段进行签名
		SortedMap<String, Object> packageParams = new TreeMap<String, Object>();
		packageParams.put("appid", appid);
		packageParams.put("attach", attach);
		packageParams.put("body", body);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("notify_url", notify_url);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("total_fee", total_fee);
		packageParams.put("trade_type", trade_type);
		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(appid, appsecret, mch_key);
		String sign = reqHandler.createSign(packageParams);// 获取签名
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<attach>" + attach + "</attach>" + "<body><![CDATA[" + body + "]]></body>" + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<notify_url>" + notify_url + "</notify_url>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>" + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>" + "<total_fee>" + total_fee + "</total_fee>" + "<trade_type>" + trade_type + "</trade_type>" + "<sign>" + sign + "</sign>" + "</xml>";
		Boolean tag = GetWxOrderno.getPayNo(pi, PAY_UNIFIEDORDER, xml);
		if (tag) {
			// 获取到prepayid后对以下字段进行签名最终发送给app
			SortedMap<String, Object> finalpackage = new TreeMap<String, Object>();
			String timestamp = DateUtil.getTimeStamp();
			finalpackage.put("appid", appid);
			finalpackage.put("timestamp", timestamp);
			finalpackage.put("noncestr", nonce_str);
			finalpackage.put("partnerid", mch_id);
			finalpackage.put("package", "Sign=WXPay");
			finalpackage.put("prepayid", pi.getPrepayId());
			String finalsign = reqHandler.createSign(finalpackage);
			pi.setTimeStamp(timestamp);
			pi.setSign(finalsign);
			pi.setTotal(new BigDecimal(total_fee));
			pi.setPayInfoId(out_trade_no);
			pi.setNonceStr(nonce_str);
			pi.setAppId(appid);
			pi.setPartnerId(mch_id);
			j.setObject(pi);
		} else {
			j.setError(pi.getMsg());
		}
		return j;
	}

	/**
	 * 微信退款
	 */
	public void wechatRefund(String out_refund_no, String out_trade_no, int total_fee, int refund_fee) {
		String nonce_str = ObjectUtils.getWxRandom();
		String op_user_id = "";// 商户MCHID
		String partnerkey = "";// 商户KEY
		SortedMap<String, Object> packageParams = new TreeMap<String, Object>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		packageParams.put("op_user_id", op_user_id);
		RequestHandler reqHandler = new RequestHandler(null, null);
		reqHandler.init(appid, appsecret, partnerkey);

		String sign = reqHandler.createSign(packageParams);
		String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>" + mch_id + "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<sign><![CDATA[" + sign + "]]></sign>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>" + "<out_refund_no>" + out_refund_no + "</out_refund_no>" + "<total_fee>" + total_fee + "</total_fee>" + "<refund_fee>" + refund_fee + "</refund_fee>" + "<op_user_id>" + op_user_id + "</op_user_id>" + "</xml>";
		try {
			ClientCustomSSL.doRefund(PAY_REFUND, xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		// ApiJson payInfo = getPayInfo("201235455", "sdfs", 333,
		// "http://www.zhiwanjia.com","");
		ApiJson a = pay_init(null, null, "231235555", "sdfs", 333, "http://www.zhiwanjia.com", "");
		// System.out.println(pay.getPrepayId());
	}
}
