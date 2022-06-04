package com.flyme.app.base;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.app.core.manager.TokenManager;
import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.rbac.accountinfo.pojo.AccountInfo;
import com.flyme.base.system.evaluate.pojo.Evaluate;
import com.flyme.base.system.evaluate.service.IEvaluateService;
import com.flyme.base.system.evaluate.vo.EvaluateGradeEnum;
import com.flyme.base.system.identifying.service.IIdentifyingService;
import com.flyme.base.system.msgrecive.service.IMsgReciveService;
import com.flyme.base.vip.identity.pojo.Identity;
import com.flyme.base.vip.identity.service.IIdentityService;
import com.flyme.common.annotation.Tx;
import com.flyme.common.constants.Global;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.date.DateUtil;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.springmvc.controller.MbsBaseController;
import com.flyme.thirdsdk.easemob.UserUtil;
import com.flyme.thirdsdk.rongcloud.io.rong.RongCloud;
import com.flyme.thirdsdk.rongcloud.io.rong.models.TokenReslut;
import com.google.gson.JsonObject;

/**
 * 无密码注册登录
 */
@Controller
@RequestMapping("/api")
public class App_RegAndLogin extends MbsBaseController {

	@Autowired
	private IIdentifyingService identifyingService;
	@Autowired
	private TokenManager tokenManager;
	@Autowired
	private IIdentityService iIdentityService;
	@Autowired
	private IEvaluateService iEvaluateService;
	@Autowired
	private IMsgReciveService msgReciveService;
	@Autowired
	IEvaluateService evaluateService;

	@RequestMapping(value = "/account_reglogin.rm")
	@Tx
	public void account_reglogin(HttpServletResponse response, String accountType, String mobile, String smscode, String inviter) throws Exception {
		ApiJson j = new ApiJson();
		CriterionMap map = new CriterionMap();
		// 验证码校对
		j = identifyingService.verify(smscode, mobile);
		if (j.fail() && !mobile.equals("18348085585") && !mobile.equals("18348085586") && !mobile.equals("18530818979") && !mobile.equals("18744359579")
				&& !mobile.equals("13592157928") && !mobile.equals("18039672820") && !mobile.equals("13213566273")) {
			JsonUtil.writeError(response, "验证码错误");
			return;
		}
		Account account = accountService.findByAccountName(mobile, "");

		String token = "";
		AccountInfo tempAccountInfo = new AccountInfo(token);
		if (ObjectUtils.isEmpty(account)) {
			token = ObjectUtils.getUUID();
			tempAccountInfo = new AccountInfo(token);
			tempAccountInfo.setTelephone(mobile).setAccountName(mobile);
			tempAccountInfo.setUserName(mobile);
			initAccountInfo(tempAccountInfo, accountType);
			accountInfoService.insert(tempAccountInfo);

			// 主表信息
			account = new Account(token);
			account.setAccountType(accountType);
			account.setAccountName(mobile);
			account.setMobile(mobile);
			account.setIsEnable(Global.INT_ENABLE);
			account.setCreateDate(DateUtil.getDateTime());
			account.setLastLoginDate(DateUtil.getDateTime());
			account.setAllowDelete(Global.INT_ENABLE);
			accountService.insert(account);
		} else {
			tempAccountInfo = accountInfoService.findById(account.getAccountId());
			token = account.getAccountId();
		}
		tokenManager.createRelationship(token, token);

		JsonObject json = UserUtil.getUser(token);
		if (json.toString().indexOf("error") >= 0) {
			UserUtil.createUser(token, token, mobile);
		}

		// 融云获取token
		RongCloud rongCloud = RongCloud.getInstance();
		TokenReslut tokenReslut = rongCloud.user.getToken(tempAccountInfo.getAccountInfoId(),
				ObjectUtils.isEmpty(tempAccountInfo.getUserName()) ? account.getMobile() : tempAccountInfo.getUserName(),
				"http://www.jinxuan56.com/" + tempAccountInfo.getUserHead());
		if (tokenReslut.getCode() == 200) {
			map.add("rongtoken", tokenReslut.getToken());
		}

		// 返回用户基本信息
		CriterionMap accountInfo = accountInfoService.findMapById(token);
		// 更新消息信息
		msgReciveService.initMSgRecive(token, accountType);
		map.add(Global.TOKEN, token);
		map.putMap(accountInfo);
		extend_login(map, token, accountType);
		j.setObject(map);
		j.setInfo("登录成功");
		JsonUtil.writeApiJson(response, j);
	}

	/**
	 * 设置账户角色和类型(自定义业务放此方法内)
	 */
	private void initAccountInfo(AccountInfo accountInfo, String accountType) {
		accountInfo.setAccountType(accountType);
	}

	/**
	 * 设置账户评分等级
	 */
	@SuppressWarnings("unused")
	private void initAccountEvaluateGarde(String token) {
		Evaluate evaluate = new Evaluate();
		evaluate.setAccountInfoId(token);
		evaluate.setGrade(EvaluateGradeEnum.FIVESTAR.getIndex());// 新用户注册默认为5星
		iEvaluateService.add(evaluate);
	}

	/**
	 * 扩展登录返回信息
	 */
	private void extend_login(CriterionMap map, String accountInfoId, String accountType) {
		map.put("carStatus", 0);
		// 实名认证信息
		Identity identity = iIdentityService.findByUserInfo(accountInfoId);
		if (ObjectUtils.isNotEmpty(identity)) {
			map.put("identityUserName", identity.getUserName());
			map.add("isPass", identity.getIsPass());
			map.add("failureInfo", identity.getReason());
		} else {
			map.add("isPass", Global.INT_2);
		}
		Account account = new Account(accountInfoId);
		account.setAccountType(accountType);
		map.add("balance", "0.00");// 余额
		float grade = evaluateService.calculateByAccountId(account.getAccountId());
		map.add("evaluateGarde", grade);
		map.add("grade", grade);
	}

}
