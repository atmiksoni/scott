package com.flyme.weixin.base;

import java.math.BigDecimal;

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
import com.flyme.base.system.balancelog.service.IBalancelogService;
import com.flyme.base.system.sysconfig.pojo.SysConfig;
import com.flyme.base.system.sysconfig.service.ISysConfigService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.pqgrid.PqGrid;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.mybatis.filters.CriteriaQuery;
import com.flyme.core.mybatis.filters.GroupOp;
import com.flyme.core.mybatis.filters.Sort;
import com.flyme.core.shiro.util.BaseShiroUtils;
import com.flyme.core.springmvc.authority.WxAuth;
import com.flyme.core.springmvc.controller.MbsBaseController;

@Controller
@RequestMapping("/weixin")
public class WeChar_Balance  extends MbsBaseController{
	
	@Autowired
	private IBalanceService balanceService;
	@Autowired
	private IAccountInfoService accountInfoService;
	@Autowired
	private IBalancelogService balanceLogService;
	@Autowired
	private ISysConfigService sysConfigService;

	@RequestMapping(value = "/recharge.wx")
	@WxAuth
	public String recharge(ModelMap modelMap){
		AccountInfo accountInfo = BaseShiroUtils.getWxAccountInfo();
		// 1.返回账户余额
		Balance balance = balanceService.findByAccountInfoId(accountInfo.getAccountInfoId());
		BigDecimal amount = balance.getAmount();
		modelMap.addAttribute("balance", ObjectUtils.formatCurrency(amount));
		return "user/recharge";
	}
	@RequestMapping(value = "/recharge_succeed.wx")
	@WxAuth
	public String recharge(){
		return "user/recharge_succeed";
	}
	/**
	 * 充值说明 2017/08/21
	 */
	@RequestMapping(value = "/charge_intro.wx")
	@WxAuth
	public String chargeIntro(ModelMap modelMap) {
		SysConfig chargeIntro = sysConfigService.getByName("CHARGE_INTRO");
		modelMap.addAttribute("configVal", chargeIntro.getConfigVal());
		return "user/recharge_explain";
	}
	/**
	 * 充值记录-提现记录
	 * 2017/08/21
	 */
	@RequestMapping(value = "/balance_log.wx")
	@WxAuth
	public String balanceLog(ModelMap modelMap,PagerInfo pagerInfo, Integer type) {
		return "user/recharge_record";
	}
	
	/**
	 * 可提现金额
	 * 2017/08/23
	 */
	@RequestMapping(value = "/able_amount.wx")
	@Authorization
	public void drawPage(@CurrentUser Account account, HttpServletResponse response) {
		ApiJson j = new ApiJson();
		AccountInfo accountInfo = accountInfoService.findByAccountId(account.getAccountId());
		CriterionMap map = new CriterionMap();
		BigDecimal ableAmount = balanceService.getAbleAmount(account.getAccountId());
		map.put("ableAmount", ObjectUtils.formatCurrency(ableAmount));
		String alipay = accountInfo.getAlipay();
		map.put("alipay", alipay);
		j.setObject(map);
		JsonUtil.writeApiJson(response, j);
	}
	
	/**
	 * 提现
	 * 2017/08/23
	 */
	@RequestMapping(value = "/withdraw_deposit.wx")
	@Authorization
	public void withdraw_deposit(@CurrentUser Account account, HttpServletResponse response,BigDecimal amount) {
		ApiJson j = new ApiJson();
		BigDecimal ableAmount = balanceService.getAbleAmount(account.getAccountId());//可提现金额
		if(ObjectUtils.gt(amount, ableAmount)){
			j.setError("可提现余额不足！");
			JsonUtil.writeApiJson(response, j);
			return;
		}else{
			//j = balanceService.withdrawDeposit(account.getAccountId(), amount);
			if(j.success()){
				j.setInfo("提现请求已提交，将在2小时内到账，请耐心等待！");
			}
		}
		JsonUtil.writeApiJson(response, j);
	}
	
	
	
	/****************
	 * 提现说明 2017/08/21
	 **********************/
	@RequestMapping(value = "/draw_intro.wx")
	public void drawIntro(HttpServletResponse response) {
		ApiJson j = new ApiJson();
		CriterionMap map = new CriterionMap();
		SysConfig drawIntro = sysConfigService.getByName("DRAW_INTRO");
		map.add("DRAW_INTRO", drawIntro.getConfigVal());
		j.setObject(map);
		JsonUtil.writeApiJson(response, j);
	}
}
