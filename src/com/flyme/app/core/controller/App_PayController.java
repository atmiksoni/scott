package com.flyme.app.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.flyme.app.core.authorization.Authorization;
import com.flyme.app.core.authorization.CurrentUser;
import com.flyme.app.core.service.IMyPayService;
import com.flyme.base.pay.vo.PrePayRequest;
import com.flyme.base.pay.vo.PrePayResponse;
import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 支付(示例程序)
 */
@Controller
@RequestMapping("/api")
public class App_PayController extends MbsBaseController {

	@Autowired
	private IMyPayService payService;

	/**
	 * APP支付调用此接口
	 */
	@RequestMapping(value = "/pay_sub.rm")
	@Authorization
	public void pay_sub(@CurrentUser Account account, HttpServletRequest request, HttpServletResponse response, String ucouponId, String medicalServiceOrderId,
			String packagesOrderId,String nursingServiceOrderId) {
		PrePayRequest prePayInfo = new PrePayRequest();
	
		ApiJson j = payService.pay(prePayInfo);
		JsonUtil.writeApiJson(response, j);
	}

	/**
	 * 微信支付结果回调
	 */
	@RequestMapping(value = "/wxpay_success.rm")
	@ResponseBody
	public String wxpay_success(HttpServletRequest request, HttpServletResponse response) {
		PrePayResponse prePayResponse = payService.wxpay_success(request);
		return prePayResponse.getMsg();
	}

	/**
	 * 支付宝支付回调
	 */
	@RequestMapping("/alipay_success.rm")
	@ResponseBody
	public String alipay_success(HttpServletRequest request) {
		PrePayResponse prePayResponse = payService.alipay_success(request);
		return prePayResponse.getMsg();
	}

}