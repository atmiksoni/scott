package com.flyme.weixin.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flyme.base.rbac.accountinfo.pojo.AccountInfo;
import com.flyme.base.rbac.accountinfo.service.IAccountInfoService;
import com.flyme.base.system.address.pojo.Address;
import com.flyme.base.system.address.service.IAddressService;
import com.flyme.base.system.area.pojo.Area;
import com.flyme.base.system.area.service.IAreaService;
import com.flyme.base.system.balance.pojo.Balance;
import com.flyme.base.system.balance.service.IBalanceService;
import com.flyme.base.system.balancelog.service.IBalancelogService;
import com.flyme.base.system.sysconfig.pojo.SysConfig;
import com.flyme.base.system.sysconfig.service.ISysConfigService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.pqgrid.PqGrid;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.mybatis.filters.CriteriaQuery;
import com.flyme.core.mybatis.filters.GroupOp;
import com.flyme.core.mybatis.filters.Sort;
import com.flyme.core.shiro.util.BaseShiroUtils;
import com.flyme.core.shiro.util.SessionUtil;
import com.flyme.core.shiro.web.Constants;
import com.flyme.core.springmvc.authority.WxAuth;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 我的(个人资料)
 */
@Controller
@RequestMapping("/weixin")
public class WeChat_User extends MbsBaseController{
	
	
	@Autowired
	private IAreaService areaService;
	@Autowired
	private IAddressService addressService;
	@Autowired
	private IBalanceService balanceService;

	@Autowired
	private ISysConfigService sysConfigService;
	@Autowired
	private IBalancelogService balanceLogService;

	@Autowired
	private IAccountInfoService accountInfoService;

	
	
	/** 我的个人资料 */
	@RequestMapping(value = "/personal_data.wx")
	@WxAuth
	public String personalData(ModelMap modelMap) {
		AccountInfo accountInfo = BaseShiroUtils.getWxAccountInfo();
		modelMap.addAttribute("accountInfo", accountInfo);
		return "user/data";
	}
	/**
	 * 上传头像 
	 */
	@RequestMapping(value = "/userhead_update.wx") 
	@ResponseBody
	public ApiJson updateUserHead(HttpServletResponse response, HttpServletRequest request,String headImgId) {
		response.setCharacterEncoding("UTF-8");
		String accountInfoId = BaseShiroUtils.getWxAccountInfo().getAccountInfoId();
		ApiJson j = accountInfoService.uploadHead(response, accountInfoId, headImgId);
		return j;
	}
	/** 我的昵称页面 */
	@RequestMapping(value = "/personal_nick.wx")
	@WxAuth
	public String personalNick(ModelMap modelMap) {
		AccountInfo accountInfo = BaseShiroUtils.getWxAccountInfo();
		modelMap.addAttribute("accountInfo", accountInfo);
		return "user/nick";
	}
	/** 更新昵称 */
	@RequestMapping(value = "/update_nick.wx")
	@ResponseBody
	public ApiJson updateNick(AccountInfo accountInfo) {
		AccountInfo accountInfo1 = BaseShiroUtils.getWxAccountInfo();
		accountInfo.setAccountInfoId(accountInfo1.getAccountInfoId());
		int updateNum = accountInfoService.update(accountInfo);
		ApiJson j = new ApiJson();
		if(updateNum > 0){
			accountInfo = accountInfoService.findById(accountInfo1.getAccountInfoId());
			SessionUtil.set(Constants.WEIXIN_USERINFO, accountInfo);
			j.setInfo("更新成功！");
		}else{
			j.setError("更新失败！");
		}
		return j;
	}
	
	/** 我的账户*/
	@RequestMapping("/my_balance.wx")
	@WxAuth
	public String myBalance(ModelMap modelMap) {
		//1.返回余额
		//AccountInfo accountInfo = BaseShiroUtils.getAccountInfo();
		AccountInfo accountInfo = BaseShiroUtils.getWxAccountInfo();
		
		Balance balance = balanceService.findByAccountInfoId(accountInfo.getAccountInfoId());
		modelMap.addAttribute("balance", ObjectUtils.formatCurrency(balance.getAmount()));
		return "user/balance";
	}
	
	/** 我的账户-交易记录 */
	@RequestMapping("/my_balance_data.wx")
	@ResponseBody
	public PqGrid balanceLog(ModelMap modelMap, PagerInfo pagerInfo) {
		return null;
	}

	/** 我的积分*/
	@RequestMapping("/my_cent.wx")
	@WxAuth
	public String myCent(ModelMap modelMap) {
		AccountInfo accountInfo = BaseShiroUtils.getWxAccountInfo();
		return "user/cent";
	}
	
	/** 我的地址*/
	@RequestMapping("/my_address.wx")
	@WxAuth
	public String myAddress(ModelMap modelMap,String type) {
		modelMap.addAttribute("type", type);
		return "user/address";
	}
	/** 添加地址 */
	@RequestMapping("/address_add.wx")
	@WxAuth
	public String address_add(ModelMap modelMap,String type) {
		modelMap.addAttribute("type", type);
		return "user/address_add";
	}
	
	/** 编辑地址 */
	@RequestMapping("/address_edit.wx")
	@WxAuth
	public String address_edit(ModelMap modelMap,String addressId,String type) {
		Address address = addressService.findById(addressId);
		String area = areaService.getAreaName(address.getArea().getAreaId(), "");
		modelMap.addAttribute("area", area);
		modelMap.addAttribute("address", address);
		
		Area area1 = areaService.findById(address.getArea().getAreaId());
		modelMap.addAttribute("area1", area1);
		modelMap.addAttribute("type", type);
		return "user/address_edit";
	}
	
	/** 设为默认地址  */
	@RequestMapping(value = "/address_default.wx")
	@ResponseBody
	public ApiJson addressDefault(String addressId,String type) {
		ApiJson j = new ApiJson();
		AccountInfo accountInfo = BaseShiroUtils.getWxAccountInfo();
		Address address = addressService.findById(addressId);
		if (ObjectUtils.isNotEmpty(address)) {
			j = addressService.defaultAddressSet(accountInfo, addressId);
			CriterionMap cm = new CriterionMap();
			cm.add("isDefault", 1);
			cm.add("accountInfo", accountInfo);
			Address address1 = addressService.findByMap(cm);
			String areaId = address1.getArea().getAreaId();
			String areaName1 = areaService.getAreaName(areaId, "");
			if(address1 != null){
				cm.clear();
				cm.add("address1", address1);
				cm.add("areaName1", areaName1);
				j.setObject(cm);
			}
		} else {
			j.setError("没有找到该地址！");
		}
		return j;
	}
	
	/** 删除地址 */
	@RequestMapping(value = "/address_del.wx")
	@ResponseBody
	public ApiJson addressDel(String addressId) {
		ApiJson j = new ApiJson();
		AccountInfo accountInfo = BaseShiroUtils.getWxAccountInfo();
		Address address = addressService.findById(addressId);
		if (ObjectUtils.isNotEmpty(address)) {
			j = addressService.delAddress(addressId, accountInfo);
			
			CriterionMap cm = new CriterionMap();
			cm.add("isDefault", 1);
			cm.add("accountInfo", accountInfo);
			Address address1 = addressService.findByMap(cm);
			if(address1 != null){
				String areaId = address1.getArea().getAreaId();
				String areaName1 = areaService.getAreaName(areaId, "");
				cm.clear();
				cm.add("address1", address1);
				cm.add("areaName1", areaName1);
				j.setObject(cm);
			}
		} else {
			j.setError("没有找到该地址！");
		}
		return j;
	}
	
	/** 地址保存  */
	@RequestMapping(value = "/address_save.wx")
	@ResponseBody
	public ApiJson addressSave(Address address,String areaName,String type) {
		int i = 0;
		ApiJson j = new ApiJson();
		if (ObjectUtils.isEmpty(areaName)) {
			return j.setError("请选择省市区！");
		}
		CriterionMap cm = new CriterionMap();
		cm.add("areaName", areaName);
		Area area = areaService.findByMap(cm);
		/************ 修改或保存  ************/
		Address address2 = addressService.findById(address.getAddressId());
		String msg = "保存";
		if(ObjectUtils.isNotEmpty(address2)){//修改
			msg = "修改";
			address.setArea(area);
			i = addressService.update(address);
		}else{//保存
			AccountInfo accountInfo = BaseShiroUtils.getWxAccountInfo();
			int k = addressService.getDefaultAddressCount(accountInfo);// 默认地址数量
			int isDefault = (k > 0) ? 0 : 1;
			address.setArea(area);
			address.setIsDel(0);
			address.setAccountInfo(accountInfo);
			address.setIsDefault(isDefault);
			i = addressService.insert(address);
		}
		/************ 修改或保存  ************/
		if (i > 0) {
			CriterionMap map = addressService.getAddressDetail(address.getAddressId());
			String areaId = map.get("areaId");
			String areaName1 = areaService.getAreaName(areaId, "");
			map.add("area", areaName1);
			map.add("type", type);
			j.setObject(map);
			j.setInfo("地址" + msg + "成功！");
		} else {
			j.setError("地址" + msg + "失败！");
		}
		return j;
	}
	
	/** 取送员招聘 */
	@RequestMapping(value = "/join_us.wx")
	@WxAuth
	public String introUs(ModelMap modelMap) {
		SysConfig joinUs = sysConfigService.getByName("JOIN_US");
		modelMap.addAttribute("joinUs", joinUs);
		return "user/join_us";
	}
	
	/** 我的分销 */
	@RequestMapping("/my_distribution.wx")
	@WxAuth
	public String myDistribution(ModelMap modelMap) {
		/*AccountInfo accountInfo = BaseShiroUtils.getAccountInfo();
		BigDecimal levelOneAmount = distributionService.getUserLevelOneAmount(accountInfo.getAccountInfoId());
		BigDecimal levelTwoAmount = distributionService.getUserLevelTwoAmount(accountInfo.getAccountInfoId());
		modelMap.addAttribute("totalAmount", levelOneAmount.add(levelTwoAmount));*/
		return "user/download_app";
	}
	
	
	/***************
	 * 在线客服 跳转 2017/07/26
	 ********************/
	@RequestMapping(value = "/online_service.wx")
	@WxAuth
	public String online_service(ModelMap modelMap) {
		return "user/online";
	}
}
