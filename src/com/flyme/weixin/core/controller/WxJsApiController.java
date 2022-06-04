package com.flyme.weixin.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flyme.common.util.ObjectUtils;
import com.flyme.core.springmvc.controller.MbsBaseController;
import com.flyme.weixin.core.service.WeixinService;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;

/**
 * 微信JSAPI
 */
@Controller
@RequestMapping("/weixin")
public class WxJsApiController extends MbsBaseController {

	@Autowired
	private WeixinService wxService;

	@RequestMapping("/jsapi.wx")
	@ResponseBody
	public WxJsapiSignature add(HttpServletRequest request) throws WxErrorException {
		String url = ObjectUtils.getParameter("url");
		WxJsapiSignature jsapiSignature = wxService.createJsapiSignature(url);
		return jsapiSignature;
	}
}