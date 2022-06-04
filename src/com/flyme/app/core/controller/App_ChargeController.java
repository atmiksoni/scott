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
import com.flyme.base.system.balance.service.IBalanceService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 充值
 */
@Controller
@RequestMapping("/api")
public class App_ChargeController extends MbsBaseController {
	
	@Autowired
	private IMyPayService payService;
	@Autowired
	private IBalanceService banceService;

	/**
	 * APP充值 调用此接口
	 */
	@RequestMapping(value = "/charge_sub.rm")
	@Authorization
	public void charge_sub(@CurrentUser Account account, HttpServletRequest request, HttpServletResponse response) {
		PrePayRequest prePayInfo = banceService.getPrePayInfo(request, account); // 生成支付参数
		ApiJson j = payService.charge(prePayInfo);
		JsonUtil.writeApiJson(response, j);
	}

	/**
	 * 微信充值回调
	 */
	@RequestMapping(value = "/wxcharge_success.rm")
	@ResponseBody
	public String wxcharge_success(HttpServletRequest request, HttpServletResponse response) {
		PrePayResponse prePayResponse = payService.wxCharge_success(request);
		return prePayResponse.getMsg();
	}

	/**
	 * 支付宝充值回调
	 */
	@RequestMapping("/alicharge_success.rm")
	@ResponseBody
	public String alicharge_success(HttpServletRequest request) {
		PrePayResponse prePayResponse = payService.alicharge_success(request);
		return prePayResponse.getMsg();
	}

}