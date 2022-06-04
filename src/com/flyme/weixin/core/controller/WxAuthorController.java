package com.flyme.weixin.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.rbac.account.service.IAccountService;
import com.flyme.base.rbac.accountinfo.pojo.AccountInfo;
import com.flyme.base.rbac.accountinfo.service.IAccountInfoService;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.context.SysConfigUtil;
import com.flyme.common.util.conver.ConvertUtils;
import com.flyme.core.shiro.util.SessionUtil;
import com.flyme.core.shiro.web.Constants;
import com.flyme.weixin.core.service.WeixinService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Controller
@RequestMapping("/weixin")
public class WxAuthorController {

	@Autowired
	private WeixinService wxService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IAccountInfoService accountInfoService;

	/**
	 * 获取用户openId
	 */
	@RequestMapping("/author.wx")
	public String author(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {
		String code = ObjectUtils.getParameter(request, "code");
		String servletPath = request.getParameter("servletPath");
		System.out.println("author请求路径" + servletPath);
		String param =ConvertUtils.unicode2String( ObjectUtils.getParameter("param",""));
		System.out.println("WxAuthorController--" + code);
		//String openId = SysConfigUtil.getOpenId();
		String openId = null;
		if (ObjectUtils.isNotEmpty(code)) {
			try {
				WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
				WxMpUser wxMpUser = wxService.getUserService().userInfo(wxMpOAuth2AccessToken.getOpenId(), "zh_CN");
				openId = wxMpUser.getOpenId();
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
		}
		Account account = accountService.findByOpenId(openId);
		if (ObjectUtils.isEmpty(account)) {
			attr.addAttribute("servletPath", servletPath);
			attr.addAttribute("param", param);
			attr.addAttribute("openId", openId);
			String url = SysConfigUtil.getServerPath() + "/weixin/login.wx";
			return "redirect:" + url;
		} else {
			AccountInfo accountInfo = accountInfoService.findById(account.getAccountId());
			SessionUtil.set(Constants.WEIXIN_ACCOUNT, account);
			SessionUtil.set(Constants.WEIXIN_USERINFO, accountInfo);
			String url = SysConfigUtil.getServerPath() + servletPath+"?"+param;
			return "redirect:" + url;
		}
	}
}
