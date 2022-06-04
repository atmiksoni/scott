package com.flyme.app.bus;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.app.core.authorization.Authorization;
import com.flyme.app.core.authorization.CurrentUser;
import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.system.bank.service.IBankService;
import com.flyme.base.vip.bankcard.pojo.BankCard;
import com.flyme.base.vip.bankcard.service.IBankCardService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.cardno.BankcardValidator;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.springmvc.controller.MbsBaseController;

@Controller
@RequestMapping("/api")
public class App_BankCard extends MbsBaseController {

	@Autowired
	private IBankCardService bankCardService;
	@Autowired
	private IBankService bankService;
	/**
	 * 绑定银行卡 获取开户银行
	 */
	@RequestMapping(value = "/get_bankname.rm")
	public void getSellType(HttpServletResponse response,PagerInfo pagerInfo) {
		CriterionMap map = new CriterionMap();
		List<CriterionMap> data = bankService.getBankList(pagerInfo);
		map.putArray("data", data);
		JsonUtil.writeMap(response, map);
	}

	

	/** 绑定银行卡 列表 */
	@RequestMapping(value = "/bankcard_list.rm")
	@Authorization
	public void getBankCardList(@CurrentUser Account account, HttpServletResponse response, PagerInfo pagerInfo) {
		CriterionMap map = new CriterionMap();
		List<CriterionMap> bankCardList = bankCardService.getBankCardListFindByAccountInfoId(pagerInfo, account.getAccountId());
		map.add("bankCardList", bankCardList);
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 绑定 银行卡
	 */
	@RequestMapping(value = "/bankcard_add.rm")
	@Authorization
	public void bindBankCard(@CurrentUser Account account, HttpServletResponse response, BankCard bankCard) {
		ApiJson j = new ApiJson();
		j = bankCardService.bankCardAdd(account, bankCard);
		JsonUtil.writeApiJson(response, j);
	}
	
	/**
	 * 修改银行卡
	 */
	@RequestMapping(value = "/bankcard_edit.rm")
	@Authorization
	public void updatBankCard(@CurrentUser Account account, HttpServletResponse response, BankCard bankCard) {
		ApiJson j = new ApiJson();
		if (BankcardValidator.checkBankCard(bankCard.getCardNo())){
			j = bankCardService.edit(bankCard);
		}else{
			j.setError("请输入正确的银行卡号");
		}
		JsonUtil.writeApiJson(response, j);
	}
	
	/**
	 * 删除 绑定信用卡信息
	 */
	@RequestMapping(value = "/bankcard_delete.rm")
	@Authorization
	public void deleteBankCard(@CurrentUser Account account, HttpServletResponse response, String bankCardId) {
		ApiJson j = bankCardService.delById(bankCardId);
		JsonUtil.writeApiJson(response, j);
	}

	/**
	 * 获取卡片信息
	 */
	@RequestMapping(value = "/get_bankcard.rm")
	@Authorization
	public void getBankCardMessage(@CurrentUser Account account, HttpServletResponse response, String bankCardId) {
		CriterionMap map = new CriterionMap("bankCardId", "bankName", "cardNo", "safetyCode", "validDate", "cardHolderName", "papersType", "papersCardNo");
		CriterionMap bankcard = bankCardService.findByBankCardId(bankCardId);
		map.putMap(bankcard);
		JsonUtil.writeMap(response, map);
	}
}