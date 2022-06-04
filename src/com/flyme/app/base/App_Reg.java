package com.flyme.app.base;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.flyme.app.core.manager.TokenManager;
import com.flyme.base.rbac.account.vo.AccountTypeEnum;
import com.flyme.base.rbac.accountinfo.pojo.AccountInfo;
import com.flyme.base.system.identifying.service.IIdentifyingService;
import com.flyme.common.constants.RoleConstants;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 用户注册
 */
@Controller
@RequestMapping("/api")
public class App_Reg extends MbsBaseController {

	@Autowired
	private IIdentifyingService identifyingService;
	@Autowired
	private TokenManager tokenManager;

	/**
	 * 用户注册
	 */
	@RequestMapping(value = "/account_reg.rm")
	public void accountReg(HttpServletResponse response, String mobile, String smscode, String password, String inviter) {
		CriterionMap map = new CriterionMap();
		if (ObjectUtils.isEmpty(password)) {
			JsonUtil.writeError(response, "请设置密码");
			return;
		}
		ApiJson j =new ApiJson();
		// 验证码校对
		j = identifyingService.verify(smscode, mobile);
		if (j.fail()) {
			JsonUtil.writeError(response, "验证码错误");
			return;
		}
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountName(mobile).setPassword(password);
		accountInfo.setRoleCode(RoleConstants.ROLE_ENGINEER);
		initAccountInfo(accountInfo, AccountTypeEnum.Vip.getValue());
		j = accountInfoService.add(accountInfo);

		if (j.success()) {
			String token = j.getObject();
			tokenManager.createRelationship(mobile, token);
			map.put("token", token);
			j.setObject(map);
			j.setInfo("注册成功！");
		}
		JsonUtil.writeApiJson(response, j);
	}

	/**
	 * 设置账户角色和类型(自定义业务放此方法内)
	 */
	private void initAccountInfo(AccountInfo accountInfo, String accountType) {
		accountInfo.setAccountType(accountType);
	}

}
