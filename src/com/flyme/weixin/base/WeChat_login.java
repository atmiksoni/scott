package com.flyme.weixin.base;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flyme.app.core.manager.TokenManager;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.springmvc.controller.MbsBaseController;

/** 登录 */
@Controller
@RequestMapping("/weixin")
public class WeChat_login extends MbsBaseController {
	@Autowired
	private TokenManager tokenManager;

	/**
	 * 登录
	 */
	@RequestMapping(value = "/login.wx")
	public String login(HttpServletRequest request) {
		String servletPath = ObjectUtils.getParameter(request, "servletPath", "/weixin/index.wx");
		String openId = ObjectUtils.getParameter(request, "openId");
		String param = ObjectUtils.getParameter(request, "param");
		request.setAttribute("servletPath", servletPath);
		request.setAttribute("param", param);
		request.setAttribute("openId", openId);
		return "login";
	}

	/**
	 * 用户端登录 RE02 2017/07/21
	 */
	@RequestMapping(value = "/wx_login_sub.wx")
	@ResponseBody
	public ApiJson WxLogin(HttpServletResponse response, HttpServletRequest request, String servletPath, String openId, String param, String mobile, String smscode, String inviteCode) throws IOException {
		ApiJson j = new ApiJson();
		CriterionMap map = new CriterionMap();
	
		return j;
	}
}
