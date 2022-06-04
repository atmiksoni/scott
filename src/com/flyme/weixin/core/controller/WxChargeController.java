package com.flyme.weixin.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flyme.app.core.service.IMyPayService;
import com.flyme.base.pay.vo.PrePayResponse;
import com.flyme.base.system.balance.service.IBalanceService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.core.shiro.util.BaseShiroUtils;
import com.flyme.core.springmvc.authority.WxAuth;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 微信公众号充值
 */
@Controller
@RequestMapping("/weixin")
public class WxChargeController extends MbsBaseController {

	@Autowired
	private IMyPayService myPayService;
	@Autowired
	private IBalanceService balanceService;

	/**
	 * 返回前台H5调用JS支付所需要的参数，公众号充值调用此接口
	 */
	@RequestMapping(value = "/wechat_charge_sub.wx")
	@ResponseBody
	@WxAuth
	public ApiJson charge_sub(HttpServletResponse response, HttpServletRequest request) {
		ApiJson j  = balanceService.getWxPrePayInfo(request, BaseShiroUtils.getWxAccount()); // 生成支付参数
		if(j.getObject() != null){
			j = myPayService.wxCharge(j.getObject());
		}
		return j;
	}

	/**
	 * 微信通知充值结果的回调地址notify_url
	 */
	@RequestMapping(value = "/wechat_charge_success.wx")
	@ResponseBody
	public String wechat_charge_success(HttpServletRequest request, HttpServletResponse response) {
		PrePayResponse prePayResponse = myPayService.weChatcharge_success(request);
		return prePayResponse.getMsg();
	}
}