package com.flyme.weixin.base;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.flyme.app.core.authorization.Authorization;
import com.flyme.app.core.authorization.CurrentUser;
import com.flyme.app.core.manager.TokenManager;
import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.rbac.account.service.IAccountService;
import com.flyme.base.rbac.accountinfo.pojo.AccountInfo;
import com.flyme.base.rbac.accountinfo.service.IAccountInfoService;
import com.flyme.base.rbac.role.service.IRoleService;
import com.flyme.base.vip.identity.pojo.Identity;
import com.flyme.base.vip.identity.service.IIdentityService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.conver.ConvertUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.shiro.util.BaseShiroUtils;
import com.flyme.core.springmvc.authority.WxAuth;
import com.flyme.core.springmvc.controller.MbsBaseController;

@Controller
@RequestMapping("/weixin")
public class WeChat_Register extends MbsBaseController {

	@Autowired
	private TokenManager tokenManager;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IAccountInfoService accountInfoService;
	@Autowired
	private IIdentityService iIdentityService;

	/** 获取验证码 */
	@RequestMapping(value = "/security_code_login.wx")
	@ResponseBody
	public ApiJson getSmsCode(HttpServletResponse response, String mobile) {
		ApiJson j = new ApiJson();
		// j = identifyingService.getSmsCode(mobile, false);
		return j.setInfo("暂时没有短信验证码，验证码请随意输入，后台暂时不验证。");
	}

	/***************
	 * 退出登录 RE04 2017/04/06
	 ************************/
	@RequestMapping("/logout_sub.wx")
	@WxAuth
	public ApiJson logout() {
		ApiJson j = new ApiJson();
		AccountInfo accountInfo = BaseShiroUtils.getAccountInfo();
		tokenManager.delRelationshipByKey(accountInfo.getTelephone());
		return j.setInfo("退出成功");
	}

	/** 实名认证资料 */
	@RequestMapping(value = "/certification.wx")
	@Authorization
	public void certification(@CurrentUser Account account, HttpServletResponse response) {
		ApiJson j = new ApiJson();
		CriterionMap map = new CriterionMap("accountInfoId", "cardNo", "userName", "driNo", "cardNoImg", "cardNoImg2", "driPic", "driPic2", "isPass");
		CriterionMap accountInfo = accountInfoService.findMapById(account.getAccountId());
		String accountInfoId = accountInfo.getString("accountInfoId");
		if (ObjectUtils.isNotEmpty(accountInfoId)) {
			Identity identity = iIdentityService.findByAccountInfo(account.getAccountId());
			if (ObjectUtils.isNotEmpty(identity)) {
				map.add("cardNo", identity.getCardNo());
				map.add("cardNoImg", identity.getFrontCard());
				map.add("cardNoImg2", identity.getBackCard());
				map.add("isPass", identity.getIsPass());
			}
			map.add("userName", accountInfo.get("userName"));
		}
		j.setObject(map);
		JsonUtil.writeApiJson(response, j);
	}
}
