package com.flyme.weixin.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flyme.app.core.service.IMyPayService;
import com.flyme.base.pay.vo.PrePayResponse;
import com.flyme.common.json.model.ApiJson;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 微信公众号支付
 */
@Controller
@RequestMapping("/weixin")
public class WxPayController extends MbsBaseController {

	@Autowired
	private IMyPayService myPayService;


	/**
	 * 返回前台H5调用JS支付所需要的参数，公众号支付调用此接口
	 */
	@RequestMapping(value = "/wechatpay_sub.wx")
	@ResponseBody
	public ApiJson pay_sub(HttpServletResponse response, HttpServletRequest request) {
		ApiJson j = new ApiJson();//orderService.getWxPrePayInfo(request, BaseShiroUtils.getWxAccount()); // 生成支付参数
		if(j.getObject() != null){
			j = myPayService.pay(j.getObject());
		}
		return j;
	}

	/**
	 * 微信通知支付结果的回调地址notify_url
	 */
	@RequestMapping(value = "/wechatpay_success.wx")
	@ResponseBody
	public String wechatpay_success(HttpServletRequest request, HttpServletResponse response) {
		PrePayResponse prePayResponse = myPayService.wechatpay_success(request);
		return prePayResponse.getMsg();
	}
}