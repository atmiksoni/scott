package com.flyme.weixin.base;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.rbac.accountinfo.pojo.AccountInfo;
import com.flyme.base.system.about.service.IAboutService;
import com.flyme.base.system.feedback.pojo.FeedBack;
import com.flyme.base.system.feedback.service.IFeedBackService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.conver.ConvertUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.shiro.util.BaseShiroUtils;
import com.flyme.core.shiro.util.SessionUtil;
import com.flyme.core.shiro.web.Constants;
import com.flyme.core.springmvc.authority.WxAuth;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 设置
 */
@Controller
@RequestMapping("/weixin")
public class WeChat_Set extends MbsBaseController {
	
	@Autowired
	private IAboutService aboutService;
	@Autowired
	private IFeedBackService feedBackService;
	
	/** 设置 */
	@RequestMapping("/setting.wx")
	@WxAuth
	public String setting(ModelMap modelMap) {
		return "setting/index";
	}
	
	/** 关于我们 */
	@RequestMapping("/about_us.wx")
	@WxAuth
	public String aboutUs(ModelMap modelMap) {
		CriterionMap about = aboutService.getNewestOne();
		modelMap.addAttribute("about", about);
		return "setting/about_us";
	}
	
	/** 用户反馈*/
	@RequestMapping("/feedback.wx")
	@WxAuth
	public String feedback(ModelMap modelMap) {
		return "setting/feedback";
	}
	/** 用户反馈提交 */
	@RequestMapping(value = "/feedback_sub.wx")
	@ResponseBody
	public ApiJson feedback(HttpServletResponse response,FeedBack feedback) {
		Account account = SessionUtil.get(Constants.WEIXIN_ACCOUNT);
		ApiJson j = new ApiJson();
		
		String contentUnicode = ConvertUtils.string2Unicode(feedback.getContent());
		AccountInfo accountInfo = accountInfoService.findByAccountId(account.getAccountId());
		if (ObjectUtils.isNotEmpty(accountInfo)) {
			feedback.setAccountInfo(accountInfo);
			feedback.setStatus(0);//0:未处理1:已处理
			feedback.setContent(contentUnicode);
			feedback.setMobile(accountInfo.getTelephone());
			feedBackService.add(feedback);
			j.setInfo("提交成功!");
		}else{
			j.setError("提交失败,请重新提交!");
		}
		return j;
	}
	/** 退出登录 */
	@RequestMapping(value = "/log_out.wx")
	@ResponseBody
	public ApiJson updateNick() {
		ApiJson j = new ApiJson();
		BaseShiroUtils.getSession().removeAttribute(Constants.WEIXIN_USERINFO);
		BaseShiroUtils.getSession().removeAttribute(Constants.WEIXIN_ACCOUNT);
		if(BaseShiroUtils.getWxAccount() == null && BaseShiroUtils.getWxAccountInfo() == null){
			j.setInfo("退出成功！");
		}
		return j;
	}
}
