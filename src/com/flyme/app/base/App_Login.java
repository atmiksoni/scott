package com.flyme.app.base;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.flyme.app.core.manager.TokenManager;
import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.system.msgrecive.service.IMsgReciveService;
import com.flyme.base.vip.identity.pojo.Identity;
import com.flyme.base.vip.identity.service.IIdentityService;
import com.flyme.common.constants.Global;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.shiro.util.PasswordHelper;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 用户登录
 */
@Controller
@RequestMapping("/api")
public class App_Login extends MbsBaseController {
	@Autowired
	private TokenManager tokenManager;
	@Autowired
	private IIdentityService iIdentityService;
	@Autowired
	private IMsgReciveService msgReciveService;
	@RequestMapping(value = "/account_login.rm")
	public void account_login(HttpServletResponse response, HttpServletRequest request, String accountType, String accountName, String password) {
		CriterionMap map = new CriterionMap(false);
		// 验证帐号是否注册
		Account account = accountService.findByAccountName(accountName, accountType);
		if (ObjectUtils.isEmpty(account)) {
			JsonUtil.writeError(response, "账户未注册");
			return;
		}
		// 验证账户密码
		Boolean tag = PasswordHelper.checkMd5Password(account.getAccountName(), password, account.getSalt(), account.getPassword());
		if (!tag) {
			JsonUtil.writeError(response, "用户名或密码不正确");
			return;
		}
		
		if(account.getIsEnable().equals(Global.INT_DISABLE)){
			JsonUtil.writeError(response, "您的账号已被禁用，请联系客服人员");
			return;
		}
		
		String token = account.getAccountId();
		tokenManager.createRelationship(token, token);
		// 返回用户基本信息
		CriterionMap accountInfo = accountInfoService.findMapById(token);
		// 更新消息信息
		//msgReciveService.initMSgRecive(token, accountType);
		map.add(Global.TOKEN, token);
		map.putMap(accountInfo);
		extend_login(map, token, accountType);
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 扩展登录返回信息
	 */
	private void extend_login(CriterionMap map, String accountInfoId, String accountType) {
		
		
		// 实名认证信息
		Identity identity = iIdentityService.findByUserInfo(accountInfoId);
		if (ObjectUtils.isNotEmpty(identity)) {
			map.put("realName", identity.getUserName());
			map.add("isPass", identity.getIsPass());
		} else {
			map.add("isPass", Global.INT_2);
		}
		/*if(!pointsDetailsService.isSign(accountInfoId)){
			pointsService.sign(accountInfoId);
		}*/
		Account account = new Account(accountInfoId);
		account.setAccountType(accountType);
		map.add("balance", "0.00");// 余额
	}

}
