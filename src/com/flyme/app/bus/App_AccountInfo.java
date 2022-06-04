package com.flyme.app.bus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.app.core.authorization.Authorization;
import com.flyme.app.core.authorization.CurrentUser;
import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.rbac.accountinfo.pojo.AccountInfo;
import com.flyme.base.rbac.accountinfo.service.IAccountInfoService;
import com.flyme.base.system.balance.pojo.Balance;
import com.flyme.base.system.balance.service.IBalanceService;
import com.flyme.base.vip.identity.service.IIdentityService;
import com.flyme.base.vip.points.service.IPointsService;
import com.flyme.base.vip.pointsdetails.service.IPointsDetailsService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 账户扩展信息相关接口
 */
@Controller
@RequestMapping("/api")
public class App_AccountInfo extends MbsBaseController {

	@Autowired
	private IBalanceService balanceService;
	@Autowired
	private IAccountInfoService accountInfoService;
	@Autowired
	private IIdentityService iIdentityService;
	@Autowired
	private IPointsService pointsService;
	@Autowired
	private IPointsDetailsService pointsDetailsService;

	/** 我的积分 */
	@RequestMapping(value = "/my_cent.rm")
	@Authorization
	public void myCent(@CurrentUser Account account, HttpServletResponse response, PagerInfo pagerInfo) {
		ApiJson j = new ApiJson();
		AccountInfo accountInfo = accountInfoService.findById(account.getAccountId());
		if (ObjectUtils.isEmpty(accountInfo)) {
			j.setError("未获取用户信息！");
		} else {
			// 1.积分总额
			CriterionMap map = new CriterionMap("cent", "centLog");
			j.setObject(map);
		}
		JsonUtil.writeApiJson(response, j);
	}

	/************
	 * 签到
	 *******************/
	@RequestMapping(value = "/sign.rm")
	@Authorization
	public void sign(@CurrentUser Account account, HttpServletResponse response) {
		ApiJson j = new ApiJson();
		if(!pointsDetailsService.isSign(account.getAccountId())){
			pointsService.sign(account.getAccountId());
			 j.setInfo("签到成功");
		}else{
			 j.setInfo("不可重复签到");
		}
		JsonUtil.writeApiJson(response, j);
	}
	
	/************
	 * 分享到朋友圈
	 *******************/
	@RequestMapping(value = "/share.rm")
	public String share(String token, ModelMap modelMap) {
		AccountInfo accountInfo = accountInfoService.findById(token);
		String qrcode = accountInfo.getQrode();
		String userhead = accountInfo.getUserHead();
		// String inviteCode = accountInfo.getInviteCode();
		modelMap.addAttribute("qrcode", qrcode);
		modelMap.addAttribute("userhead", userhead);
		// modelMap.addAttribute("inviteCode", inviteCode);
		return "share_download";
	}

	/** AW01 账户首页 工人端 */
	@RequestMapping(value = "/worker_account_index.rm")
	@Authorization
	public void workerAccountIndex(@CurrentUser Account account, HttpServletRequest request, HttpServletResponse response) {
		CriterionMap map = new CriterionMap();
		// 1.账户信息
		Balance balance = balanceService.findByAccountInfoId(account.getAccountId());
		map.add("balance", ObjectUtils.formatCurrency(balance.getAmount()));
		JsonUtil.writeMap(response, map);
	}
	
	/** 实名认证信息*/
	@RequestMapping(value = "/buscertification_info.rm")
	@Authorization
	public void buscertificationInfo(@CurrentUser Account account, HttpServletRequest request, HttpServletResponse response) {
		// 1.账户信息
		CriterionMap  identity = iIdentityService.findAllByAccountInfoId(account.getAccountId());
		JsonUtil.writeObj(response, identity);
	}
	
}
