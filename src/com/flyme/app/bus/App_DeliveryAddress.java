package com.flyme.app.bus;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.app.core.authorization.Authorization;
import com.flyme.app.core.authorization.CurrentUser;
import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.vip.deliveryaddress.pojo.DeliveryAddress;
import com.flyme.base.vip.deliveryaddress.service.IDeliveryAddressService;
import com.flyme.common.constants.Global;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.springmvc.controller.MbsBaseController;

@Controller
@RequestMapping("/api")
public class App_DeliveryAddress extends MbsBaseController {
	@Autowired
	private IDeliveryAddressService deliveryAddressService;
	
	/**
	 * 地址列表
	 */
	@RequestMapping("/deliveryaddress_list.rm")
	@Authorization
	public void deliveryAddressList(@CurrentUser Account account, HttpServletRequest request, HttpServletResponse response,DeliveryAddress deliveryAddress) {
		CriterionMap map =new CriterionMap();
		List<CriterionMap> deliveryAddressList = deliveryAddressService.listByAccountInfo(account.getAccountId());
		map.put("deliveryAddressList", deliveryAddressList);
		JsonUtil.writeMap(response, map);
	}
	
	/**
	 * 添加地址
	 */
	@RequestMapping("/deliveryaddress_add.rm")
	@Authorization
	public void deliveryAddressAdd(@CurrentUser Account account, HttpServletRequest request, HttpServletResponse response,DeliveryAddress deliveryAddress) {
		deliveryAddress.setAccountInfoId(account.getAccountId());
		deliveryAddress.setStatus(Global.INT_ENABLE);
		ApiJson j = deliveryAddressService.add(deliveryAddress);
		JsonUtil.writeApiJson(response, j);
	}
	
	/**
	 * 修改地址
	 */
	@RequestMapping("/deliveryaddress_edit.rm")
	@Authorization
	public void deliveryAddressEdit(@CurrentUser Account account, HttpServletRequest request, HttpServletResponse response,DeliveryAddress deliveryAddress) {
		ApiJson j = deliveryAddressService.edit(deliveryAddress);
		JsonUtil.writeApiJson(response, j);
	}
	
	/**
	 * 删除地址
	 */
	@RequestMapping("/deliveryaddress_delete.rm")
	@Authorization
	public void deliveryAddressDelete(@CurrentUser Account account, HttpServletRequest request, HttpServletResponse response,String deliveryAddressId) {
		ApiJson j = deliveryAddressService.delById(deliveryAddressId);
		JsonUtil.writeApiJson(response, j);
	}
	
	/**
	 * 修改地址
	 */
	@RequestMapping("/deliveryaddress_default.rm")
	@Authorization
	public void deliveryAddressDefault(@CurrentUser Account account, HttpServletRequest request, HttpServletResponse response,String deliveryAddressId) {
		ApiJson j = deliveryAddressService.deliveryAddressDefault(deliveryAddressId,account.getAccountId());
		JsonUtil.writeApiJson(response, j);
	}
}
