package com.flyme.app.base;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.flyme.app.core.authorization.Authorization;
import com.flyme.app.core.authorization.CurrentUser;
import com.flyme.app.core.manager.TokenManager;
import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.rbac.account.vo.AccountTypeEnum;
import com.flyme.base.rbac.accountinfo.pojo.AccountInfo;
import com.flyme.base.system.balance.pojo.Balance;
import com.flyme.base.system.balance.service.IBalanceService;
import com.flyme.base.system.identifying.service.IIdentifyingService;
import com.flyme.base.system.msgrecive.service.IMsgReciveService;
import com.flyme.base.system.ucoupon.service.IUcouponService;
import com.flyme.base.vip.identity.pojo.Identity;
import com.flyme.base.vip.identity.service.IIdentityService;
import com.flyme.base.vip.points.pojo.Points;
import com.flyme.base.vip.points.service.IPointsService;
import com.flyme.common.constants.Global;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.upload.UploadFile;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.filters.CriteriaQuery;
import com.flyme.core.mybatis.filters.GroupOp;
import com.flyme.core.springmvc.controller.MbsBaseController;
import com.flyme.thirdsdk.sms.DESUtil;
import com.flyme.thirdsdk.sms.JiaMiUtils;
@Controller
@RequestMapping("/api")
public class App_Base extends MbsBaseController {

	@Autowired
	private TokenManager tokenManager;
	@Autowired
	private IIdentifyingService identifyingService;
	@Autowired
	private IIdentityService iIdentityService;
	@Autowired
	private IMsgReciveService msgReciveService;
	@Autowired
	private IUcouponService ucouponService;
	@Autowired
	private IBalanceService balanceService;
	@Autowired
	private IPointsService pointsService;

	/**
	 * 获取验证码
	 */
	@RequestMapping(value = "/get_smscode.rm")
	public void get_reg_vercode(HttpServletRequest request, HttpServletResponse response, String mobile, String accountType, Boolean isReg, String smsKey, String time) {
		ApiJson j = new ApiJson();
		if (ObjectUtils.isEmpty(time)) {
			JsonUtil.writeError(response, "。");
			return;
		}
		String tempKey = JiaMiUtils.getkey(time);// 加密后的key
		JiaMiUtils.DESIV = JiaMiUtils.getiv(time);// 加密后的IV
		String tempMobile = "";
		try {
			tempMobile = DESUtil.decodeValue(tempKey, mobile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (ObjectUtils.isEmpty(tempMobile) || !tempMobile.substring(0, 1).equals("1")) {
			JsonUtil.writeApiJson(response, j);
			return;
		}
		if (ObjectUtils.isNotEmpty(isReg) && isReg == false) {
			j = identifyingService.getSmsCode(tempMobile, accountType, isReg, smsKey);
		} else {
			Account account = accountService.findByAccountName(tempMobile, "");
			if (ObjectUtils.isNotEmpty(account) && !account.getAccountType().equals(accountType)) {
				j.setAppError("手机号已经注册");
			} else {
				j = identifyingService.getSmsCode(tempMobile, accountType, isReg, smsKey);
			}
		}
		JsonUtil.writeApiJson(response, j);
	}

	/** 根据验证码修改密码 */
	@RequestMapping(value = "/update_pwd.rm")
	public void pwdForget(HttpServletResponse response, String mobile, String smscode, String newpwd, String accountType) {
		ApiJson j = accountService.updatePwd(mobile, smscode, newpwd, AccountTypeEnum.Vip.getValue());
		JsonUtil.writeApiJson(response, j);
	}
	/**
	 * 根据旧密码修改密码
	 */
	@RequestMapping(value = "/update_pwd2.rm")
	@Authorization
	public void passwordUpdate(@CurrentUser Account account, HttpServletResponse response, String oldpwd, String newpwd, String repeatpwd) {
		ApiJson j = accountService.updatePassword(newpwd, oldpwd, account);
		JsonUtil.writeApiJson(response, j);
	}

	/** 退出登录 */
	@RequestMapping("/logout_sub.rm")
	@Authorization
	public void logout(@CurrentUser Account account, HttpServletResponse response) {
		ApiJson j = new ApiJson();
		tokenManager.delRelationshipByKey(account.getAccountName());
		j.setAppInfo("退出成功");
		JsonUtil.writeApiJson(response, j);
	}

	/** 绑定手机修改 */
	@RequestMapping(value = "/mobile_update1.rm")
	public void updateMobile(HttpServletResponse response, String old_mobile, String old_vercode, String new_mobile, String new_vercode, String accountType) {
		ApiJson j = accountInfoService.updateMobile(old_mobile, old_vercode, new_mobile, new_vercode, accountType);
		JsonUtil.writeApiJson(response, j);
	}

	/**
	 * 个人信息
	 * 
	 * @param iWayBillService
	 * @throws Exception
	 */
	@RequestMapping(value = "/userinfo.rm")
	@Authorization
	public void accountInfo(@CurrentUser Account account, HttpServletRequest request, HttpServletResponse response, Object iWayBillService) throws Exception {
		CriterionMap map = new CriterionMap();
		CriterionMap user = accountInfoService.findMapById(account.getAccountId());
		user.put("mobile", account.getMobile());
		Identity identity = iIdentityService.findByUserInfo(account.getAccountId());
		if (ObjectUtils.isNotEmpty(identity)) {
			map.add("isPass", identity.getIsPass());
			map.put("identityUserName", identity.getUserName());
			map.add("failureInfo", identity.getReason());
		} else {
			map.add("isPass", Global.INT_2);
		}
		// 余额
		Balance balance=balanceService.findById(account.getAccountId());
		map.add("balance", balance.getAmount());
		// 余额
		Points points=pointsService.findById(account.getAccountId());
		map.add("points", points.getTotal());

		// 融云获取token
		/*RongCloud rongCloud = RongCloud.getInstance();
		TokenReslut tokenReslut = rongCloud.user.getToken(user.get("accountInfoId"), ObjectUtils.isEmpty(user.get("userName")) ? account.getMobile() : user.get("userName"),
				"http://www.jinxuan56.com/" + user.get("userhead"));
		if (tokenReslut.getCode() == 200) {
			map.add("rongtoken", tokenReslut.getToken());
		}*/

		// 未读消息数量
		Integer messageCount = msgReciveService.countByAccountId(account.getAccountId());
		int ucouponCount = ucouponService.countByAccountInfoId(account.getAccountId());
		
		
		map.put("messageCount", messageCount);
		map.put("ucouponCount", ucouponCount);
		map.putMap(user);
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 修改个人信息
	 */
	@RequestMapping(value = "/userinfo_edit.rm")
	@Authorization
	public void accountInfoManager(@CurrentUser Account account, HttpServletResponse response, AccountInfo accountInfo) {
		String msg = "修改失败！";
		accountInfo.setAccountInfoId(account.getAccountId());
		int i = accountInfoService.update(accountInfo);
		if (i > 0) {
			msg = "修改成功!";
		}
		JsonUtil.writeOk(response, msg);
	}

	/**
	 * 上传头像
	 */
	@RequestMapping(value = "/userhead_edit.rm")
	@Authorization
	public void updateUserHead(@CurrentUser Account account, HttpServletResponse response, HttpServletRequest request) {
		ApiJson j = accountInfoService.updateHead(request, account.getAccountId());
		JsonUtil.writeApiJson(response, j);
	}

	/**
	 * 上传身份证
	 */
	@RequestMapping(value = "/accountInfo_uploadpic_sub.rm")
	@Authorization
	public void updateCard(@CurrentUser Account account, HttpServletResponse response, HttpServletRequest request) {
		ApiJson j = accountInfoService.updateCard(request, account.getAccountId());
		JsonUtil.writeApiJson(response, j);
	}

	/***
	 * 用户认证
	 * 
	 * @param account
	 * @param request
	 * @param response
	 * @param identity
	 */
	@RequestMapping("/identification_sub.rm")
	@Authorization
	public void buscertification(@CurrentUser Account account, HttpServletRequest request, HttpServletResponse response, Identity identity) {
		UploadFile uploadFile = new UploadFile(request);
		String filePath = "identity";
		uploadFile.setCusPath(filePath);
		uploadFile.setSaveLocal(false);
		uploadFile.setObject(identity);
		mbsCommonService.upload(uploadFile);
		ApiJson j = identityService.buscertification(request, identity, account);
		JsonUtil.writeApiJson(response, j);
	}

	/** 绑定手机修改 */
	@RequestMapping(value = "/userinfo_list.rm")
	public void userinfoList(HttpServletResponse response, String ids) {
		CriteriaQuery cq = new CriteriaQuery(GroupOp.and);
		cq.addSqlField("accountInfoId,userName,userhead");
		cq.in("accountInfoId", ids);
		List<CriterionMap> list = accountInfoService.pagerMap(cq, false);
		JsonUtil.writeObj(response, list);
	}
}
