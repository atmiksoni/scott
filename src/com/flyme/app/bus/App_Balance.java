package com.flyme.app.bus;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.app.core.authorization.Authorization;
import com.flyme.app.core.authorization.CurrentUser;
import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.system.balance.pojo.Balance;
import com.flyme.base.system.balance.service.IBalanceService;
import com.flyme.base.system.balancelog.service.IBalancelogService;
import com.flyme.base.system.identifying.service.IIdentifyingService;
import com.flyme.base.system.rechargepackage.service.IRechargepackageService;
import com.flyme.base.system.sysconfig.pojo.SysConfig;
import com.flyme.base.system.sysconfig.service.ISysConfigService;
import com.flyme.base.system.ucoupon.service.IUcouponService;
import com.flyme.base.vip.points.pojo.Points;
import com.flyme.base.vip.points.service.IPointsService;
import com.flyme.base.vip.pointsdetails.service.IPointsDetailsService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.springmvc.controller.MbsBaseController;

@Controller
@RequestMapping("/api")
public class App_Balance extends MbsBaseController {

	@Autowired
	private IBalanceService balanceService;
	@Autowired
	private IBalancelogService balanceLogService;
	@Autowired
	private ISysConfigService sysConfigService;
	@Autowired
	private IPointsService pointsService;
	@Autowired
	private IPointsDetailsService pointsDetailsService;
	@Autowired
	private IUcouponService ucouponService;
	@Autowired
	private IRechargepackageService rechargepackageService;
	@Autowired
	private IIdentifyingService identifyingService;

	/*************
	 * 我的钱包
	 */
	@RequestMapping(value = "/my_balance.rm")
	@Authorization
	public void myBalance(@CurrentUser Account account, HttpServletResponse response, PagerInfo pagerInfo) {
		// 1.返回余额
		CriterionMap map = new CriterionMap("balance");
		Balance balance = balanceService.findByAccountInfoId(account.getAccountId());
		map.add("balance", balance.getAmount());
		Points points = pointsService.findById(account.getAccountId());
		map.add("points", points.getTotal());
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 账户记录
	 */
	@RequestMapping(value = "/balance_log.rm")
	@Authorization
	public void balanceLog(@CurrentUser Account account, HttpServletResponse response, PagerInfo pagerInfo, Integer type, String beginDate, String endDate) {
		CriterionMap map = new CriterionMap();
		List<CriterionMap> balanceLogList = balanceLogService.getBalanceLogList(pagerInfo, account.getAccountId(), type, beginDate, endDate);
		map.put("balanceLogList", balanceLogList);
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 积分记录
	 */
	@RequestMapping(value = "/points_details.rm")
	@Authorization
	public void pointsDetails(@CurrentUser Account account, HttpServletResponse response, PagerInfo pagerInfo, Integer type, Integer pointsType, String beginDate, String endDate) {
		CriterionMap map = new CriterionMap();
		List<CriterionMap> pointsDetailsList = pointsDetailsService.listByPageInfo(pagerInfo, account.getAccountId(), type, pointsType, beginDate, endDate);
		map.put("pointsDetailsList", pointsDetailsList);
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 可提现金额
	 */
	@RequestMapping(value = "/able_amount.rm")
	@Authorization
	public void drawPage(@CurrentUser Account account, HttpServletResponse response) {
		CriterionMap map = new CriterionMap();
		BigDecimal ableAmount = balanceService.getAbleAmount(account.getAccountId());
		map.put("ableAmount", ObjectUtils.formatCurrency(ableAmount));
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 提现
	 */
	@RequestMapping(value = "/withdraw_deposit.rm")
	@Authorization
	public void withdraw_deposit(@CurrentUser Account account, HttpServletResponse response, BigDecimal amount,String bankCardId,String smscode,String mobile) {
		ApiJson j = new ApiJson();
		// 验证码校对
		j = identifyingService.verify(smscode, mobile);
		if (j.fail()) {
			JsonUtil.writeError(response, "验证码错误");
			return;
		}
		j = balanceService.withdrawDeposit(account.getAccountId(), amount,bankCardId);
		if (j.success()) {
			j.setInfo("提现请求已提交，请耐心等待！");
		}
		JsonUtil.writeApiJson(response, j);
	}

	/****************
	 * 充值说明
	 */
	@RequestMapping(value = "/charge_intro.rm")
	public void chargeIntro(HttpServletResponse response) {
		ApiJson j = new ApiJson();
		CriterionMap map = new CriterionMap();
		SysConfig chargeIntro = sysConfigService.getByName("CHARGE_INTRO");
		map.add("CHARGE_INTRO", chargeIntro.getConfigVal());
		j.setObject(map);
		JsonUtil.writeApiJson(response, j);
	}

	/****************
	 * 提现说明
	 */
	@RequestMapping(value = "/draw_intro.rm")
	public void drawIntro(HttpServletResponse response) {
		ApiJson j = new ApiJson();
		CriterionMap map = new CriterionMap();
		SysConfig drawIntro = sysConfigService.getByName("DRAW_INTRO");
		map.add("DRAW_INTRO", drawIntro.getConfigVal());
		j.setObject(map);
		JsonUtil.writeApiJson(response, j);
	}

	/** 优惠券列表 */
	@RequestMapping(value = "/my_coupon_list.rm")
	@Authorization
	public void couponList(@CurrentUser Account account, HttpServletResponse response, PagerInfo pagerInfo, String couponType) {
		ApiJson j = new ApiJson();
		CriterionMap map = new CriterionMap();
		List<CriterionMap> couponList = ucouponService.findListByUserId(account.getAccountId(), couponType);
		map.add("couponList", couponList);
		j.setObject(map);
		JsonUtil.writeApiJson(response, j);
	}

	/** 充值优惠列表 */
	@RequestMapping(value = "/charge_package_list.rm")
	@Authorization
	public void chargeList(@CurrentUser Account account, HttpServletResponse response) {
		ApiJson j = new ApiJson();
		CriterionMap map = new CriterionMap();
		List<CriterionMap> packageList = rechargepackageService.findPackageList();
		map.add("packageList", packageList);
		// ucouponService.sendUcoupon(account.getAccountId(), new
		// BigDecimal(1000));
		j.setObject(map);
		JsonUtil.writeApiJson(response, j);
	}
}
