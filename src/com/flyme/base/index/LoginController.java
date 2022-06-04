package com.flyme.base.index;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.flyme.base.rbac.account.service.IAccountService;
import com.flyme.base.rbac.accountrole.service.IAccountRoleService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.util.annotation.LogAnnotation;
import com.flyme.common.util.annotation.LogTypeEnum;
import com.flyme.core.hcaptcha.CaptchaService;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 系统登录
 */
@Controller
public class LoginController extends MbsBaseController {

	@Autowired
	private CaptchaService captchaService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	public IAccountRoleService accountRoleService;

	@RequestMapping(value = "captcha.do")
	public void requestCaptcha(HttpServletRequest request, HttpServletResponse response, String token) {
		captchaService.getCaptch(request, response, token);
	}

	/**
	 * 登录页面跳转
	 */
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() || subject.isRemembered()) {
			return "redirect:index.do";
		} else {
			session.setAttribute("userName", "admin");
			return "login";
		}
	}

	@RequestMapping("/checkuser.do")
	@ResponseBody
	@LogAnnotation(logType = LogTypeEnum.login)
	public ApiJson login(ApiJson j, HttpServletRequest request) {
		j = accountService.login(request);
		return j;
	}

	@RequestMapping("/logout.do")
	public String logout() {
		// 使用权限管理工具进行用户的退出，注销登录
		SecurityUtils.getSubject().logout();
		return "redirect:login.do";
	}
	
}
