package com.flyme.app.base;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.app.core.authorization.Authorization;
import com.flyme.app.core.authorization.CurrentUser;
import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.system.area.pojo.Area;
import com.flyme.base.system.consult.pojo.Consult;
import com.flyme.base.system.consult.service.IConsultService;
import com.flyme.base.system.contact.pojo.Contact;
import com.flyme.base.system.contact.service.IContactService;
import com.flyme.base.system.html.pojo.Html;
import com.flyme.base.system.html.service.IHtmlService;
import com.flyme.base.system.push.pojo.Pushs;
import com.flyme.base.system.push.service.IPushService;
import com.flyme.base.system.sysconfig.pojo.SysConfig;
import com.flyme.base.system.sysconfig.service.ISysConfigService;
import com.flyme.common.constants.Global;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.emoji.EmojiFilter;
import com.flyme.common.util.html.HTMLSpirit;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.mybatis.filters.CriteriaQuery;
import com.flyme.core.mybatis.filters.GroupOp;
import com.flyme.core.mybatis.filters.Sort;
import com.flyme.core.springmvc.controller.MbsBaseController;
import com.flyme.thirdsdk.baidu.BaiduMapUtil;
import com.flyme.thirdsdk.baidu.models.AddressComponent;


@Controller
@RequestMapping("/api")
public class App_Sys extends MbsBaseController {
	
	@Autowired
	private IHtmlService htmlService;
	@Autowired
	private IConsultService consultService;
	@Autowired
	private IPushService pushService;
	@Autowired
	private IContactService contactService;
	@Autowired
	private ISysConfigService sysConfigService;
	
	/**
	 * 关于我们
	 */
	@RequestMapping(value = "/about_us.rm")
	public void aboutus(HttpServletResponse response, PagerInfo pagerInfo, Model model) {
		CriterionMap map = new CriterionMap(true);
		Html html = htmlService.findByHtmlKey("gywm");
		map.add("about", html.getContent());
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 注册协议
	 */
	@RequestMapping(value = "/gvrp.rm")
	public void gvrp(HttpServletResponse response, PagerInfo pagerInfo, Model model) {
		CriterionMap map = new CriterionMap(true);
		Html html = htmlService.findByHtmlKey("zcxy");
		map.put("msgcode", 100);
		map.put("success", true);
		map.add("zcxy", html.getContent());
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 帮助中心列表
	 */
	@RequestMapping(value = "/help_list.rm")
	public void helplist(HttpServletResponse response, PagerInfo pagerInfo, Model model) {
		CriterionMap map = new CriterionMap(true);
		CriteriaQuery cq = new CriteriaQuery(pagerInfo, GroupOp.and, model);
		cq.addSqlField("h.htmlId,h.htmlKey,h.title");
		cq.ne("htmlKey", "gywm");
		cq.ne("htmlKey", "zcxy");
		cq.order("htmlsort", Sort.desc);
		List<CriterionMap> htmls = htmlService.pagerMap(cq, false);
		map.put("msgcode", 100);
		map.put("success", true);
		map.add("helpList", htmls);
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 帮助中心明细
	 */
	@RequestMapping(value = "/help_center.rm")
	public void helpcenter(HttpServletResponse response,String htmlKey) {
		CriterionMap map = new CriterionMap(true);
		Html html = htmlService.findByHtmlKey(htmlKey);
		map.put("msgcode", 100);
		map.put("success", true);
		map.add("help", HTMLSpirit.toHtml(html.getTitle(), html.getContent()));
		JsonUtil.writeMap(response, map);
	}
	
	/**
	 * 注册协议
	 */
	@RequestMapping(value = "/zcxy_center.rm")
	public void zcxy(HttpServletResponse response,String accountType) {
		CriterionMap map = new CriterionMap(true);
		Html html = htmlService.findByHtmlKey("zcxy");
		map.put("msgcode", 100);
		map.put("success", true);
		map.add("help", HTMLSpirit.toHtml(html.getTitle(), html.getContent()));
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 注册协议
	 */
	@RequestMapping(value = "/gywm_center.rm")
	public void gywm(HttpServletResponse response,String accountType) {
		CriterionMap map = new CriterionMap(true);
		Html html = htmlService.findByHtmlKey("gywm");
		map.put("msgcode", 100);
		map.put("success", true);
		map.add("help", HTMLSpirit.toHtml(html.getTitle(), html.getContent()));
		JsonUtil.writeMap(response, map);
	}
	
	/**
	 * 意见反馈
	 */
	@RequestMapping(value = "/consult_sub.rm")
	@Authorization
	public void consultSub(@CurrentUser Account account, HttpServletResponse response,Consult consult) {
		String content = EmojiFilter.filterEmoji(consult.getContent());
		consult.setContent(content);
		consult.setAccountInfoId(account.getAccountId());
		ApiJson j = new ApiJson();
		if (ObjectUtils.isNotEmpty(consult.getType()) && consult.getType() == 1) {
		} else {
			j = consultService.add(consult);
		}
		JsonUtil.writeApiJson(response, j);
	}
	
	/**
	 * 意见反馈列表
	 */
	@RequestMapping(value = "/consult_list.rm")
	@Authorization
	public void consultList(@CurrentUser Account account, HttpServletResponse response) {
		CriterionMap map =  new CriterionMap();
		map.put("msgcode", 100);
		map.put("success", true);
		CriteriaQuery cq =  new CriteriaQuery(GroupOp.and);
		cq.addSqlField("content,reply,createDate");
		cq.eq("accountInfoId", account.getAccountId());
		cq.order("createDate", Sort.desc);
		List<CriterionMap> consultList=consultService.pagerMap(cq, false);
		map.put("consultList", consultList);
		JsonUtil.writeMap(response, map);
	}
	
	/**
	 * 联系我们
	 */
	@RequestMapping(value = "/contact_sub.rm")
	public void contactSub(HttpServletResponse response,Contact contact) {
		String content = EmojiFilter.filterEmoji(contact.getContent());
		contact.setContent(content);
		ApiJson j = new ApiJson();
		j = contactService.add(contact);
		JsonUtil.writeApiJson(response, j);
	}
	
	/**
	 * 联系我们_客服电话
	 */
	@RequestMapping(value = "/contact_phone.rm")
	public void contactPhone(HttpServletResponse response) {
		CriterionMap map = new CriterionMap();
		map.put("configName", Global.SYSCONFIG_PHONE);
		SysConfig phone = sysConfigService.findByMap(map);
		map.clear();
		map.add("phone", phone.getConfigVal());
		JsonUtil.writeMap(response, map);
	}
	
	/**
	 * 获取所有城市
	 */
	@RequestMapping(value = "/city_all.rm")
	public void city(HttpServletResponse response, PagerInfo pagerInfo, Model model) {
		CriterionMap map = new CriterionMap();
		// 全国城市
		CriteriaQuery cq = new CriteriaQuery(pagerInfo, GroupOp.and, model);
		cq.addSqlField("a.areaId,a.areaName,a.areaCode,a.firstLetter,a.lng,a.lat");
		cq.eq("areaLevel", Global.AREA_LEVEL_2);
		cq.order("firstLetter", Sort.asc);
		List<CriterionMap> citys = areaService.pagerMap(cq, false);
		// 热门城市
		pagerInfo = new PagerInfo();
		CriteriaQuery cq2 = new CriteriaQuery(pagerInfo, GroupOp.and, model);
		cq2.addSqlField("a.areaId,a.areaName,a.areaCode,a.firstLetter,a.lng,a.lat");
		cq2.eq("areaLevel", Global.AREA_LEVEL_2);
		cq2.eq("hot", Global.INT_1);
		cq2.order("areaCode", Sort.asc);
		List<CriterionMap> hots = areaService.pagerMap(cq, false);
		map.add("citys", citys);
		map.add("hots", hots);
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 获取省份
	 */
	@RequestMapping(value = "/area_all.rm")
	public void areaAll(HttpServletResponse response, PagerInfo pagerInfo, Model model) {
		CriterionMap map = new CriterionMap();
		CriteriaQuery cq = new CriteriaQuery(pagerInfo, GroupOp.and, model);
		cq.addSqlField("a.areaId,a.areaName,a.areaCode,a.parentId,a.areaLevel");
		cq.order("areaId", Sort.asc);
		List<CriterionMap> areas = areaService.pagerMap(cq, false);
		map.add("areas", areas);
		JsonUtil.writeMap(response, map);
	}
	
	/**
	 * 获取省份
	 */
	@RequestMapping(value = "/prov_list.rm")
	public void provList(HttpServletResponse response, PagerInfo pagerInfo, Model model) {
		CriterionMap map = new CriterionMap();
		CriteriaQuery cq = new CriteriaQuery(pagerInfo, GroupOp.and, model);
		cq.addSqlField("a.areaId,a.areaName,a.areaCode,a.lng,a.lat");
		cq.eq("areaLevel", Global.AREA_LEVEL_1);
		cq.order("areaId", Sort.asc);
		List<CriterionMap> areas = areaService.pagerMap(cq, false);
		map.add("areas", areas);
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 获取城市
	 */
	@RequestMapping(value = "/city_list.rm")
	public void cityList(HttpServletResponse response, PagerInfo pagerInfo, Model model, String areaId) {
		CriterionMap map = new CriterionMap(true);
		CriteriaQuery cq = new CriteriaQuery(pagerInfo, GroupOp.and, model);
		cq.addSqlField("a.areaId,a.areaName,a.areaCode,a.lng,a.lat");
		cq.eq("areaLevel", Global.AREA_LEVEL_2);
		cq.eq("parentId", areaId);
		cq.order("areaId", Sort.asc);
		List<CriterionMap> areas = areaService.pagerMap(cq, false);
		map.add("areas", areas);
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 获取区域
	 */
	@RequestMapping(value = "/district_list.rm")
	public void districtList(HttpServletResponse response, PagerInfo pagerInfo, Model model, String areaId) {
		String enabled=ObjectUtils.getParameter("enabled"); 
		CriterionMap map = new CriterionMap(true);
		if (ObjectUtils.isChinese(areaId)) {
			areaId = areaService.getAreaIdByAreaName(areaId);
		}
		// 区域
		CriteriaQuery cq = new CriteriaQuery(pagerInfo, GroupOp.and, model);
		cq.addSqlField("a.areaId,a.areaName,a.areaCode,a.lng,a.lat");
		cq.eq("areaLevel", Global.AREA_LEVEL_3);
		cq.eq("parentId", areaId);
		cq.order("areaId", Sort.asc);
		if(ObjectUtils.isNotEmpty(enabled)){
			cq.eq("enabled", enabled);
		}
		List<CriterionMap> areas = areaService.pagerMap(cq, false);
		map.add("areas", areas);
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 获取区域代码
	 */
	@RequestMapping(value = "/area_code.rm")
	public void areaCode(HttpServletResponse response, PagerInfo pagerInfo, Model model, String areaName) {
		CriterionMap map = new CriterionMap(true);
		Area area = areaService.getCityByName(areaName);
		if (ObjectUtils.isNullOrUndefined(area)) {
		} else {
			map.add("areaCode", area.getAreaId());
		}
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 获取区域代码
	 */
	@RequestMapping(value = "/allarea_code.rm")
	public void allAreaCode(HttpServletResponse response, PagerInfo pagerInfo, Model model, String provinceName, String cityName, String districtName) {
		CriterionMap map = new CriterionMap(true);
		Area province = areaService.findByAreaNameAndLevel(provinceName, 1);
		Area city = areaService.findByAreaNameAndLevel(cityName, 2);
		Area district = areaService.findByAreaNameAndLevel(districtName, 3);
		if (ObjectUtils.isNullOrUndefined(province) || ObjectUtils.isNullOrUndefined(city) || ObjectUtils.isNullOrUndefined(district)) {
		} else {
			map.add("province", province.getAreaId());
			map.add("city", city.getAreaId());
			map.add("district", district.getAreaId());
		}
		JsonUtil.writeMap(response, map);
	}

	/**
	 * 获取区域代码
	 * @throws IOException 
	 */
	@RequestMapping(value = "/address_code.rm")
	public void addressCode(HttpServletResponse response, PagerInfo pagerInfo, Model model, String lng, String lat) throws IOException {
		CriterionMap map = new CriterionMap();
		if(ObjectUtils.isEmpty(lng)||ObjectUtils.isEmpty(lat)){
			JsonUtil.writeMap(response, map);
		}
		AddressComponent addressComponent = BaiduMapUtil.getAddress(lng, lat);
		map.put("areaName", addressComponent.getProvince().replace("省", "").replace("市",""));
		map.put("areaLevel", 1);
		Area province = areaService.findByMap(map);
		map.put("areaName", addressComponent.getCity().replace("市", ""));
		map.put("areaLevel", 2);
		map.put("parentId", province.getAreaId());
		Area city = areaService.findByMap(map);
		map.put("areaName", addressComponent.getDistrict().replace("市", ""));
		map.put("areaLevel", 3);
		map.put("parentId", city.getAreaId());
		Area district = areaService.findByMap(map);
		map.clear();
		map.put("msgcode", 100);
		map.put("success", true);
		map.put("province", province.getAreaId());
		map.put("city", city.getAreaId());
		map.put("district", district.getAreaId());
		JsonUtil.writeMap(response, map);
	}
	
	/**
	 * 根据城市ID获取距离
	 */
	@RequestMapping(value = "/distance_area.rm")
	public void distanceArea(HttpServletResponse response,String beginAreaId,String endAreaId) {
		String distance=areaService.distanceArea(beginAreaId, endAreaId);
		JsonUtil.writeObj(response, distance);
	}
	
	/**
	 * 获取推送详情
	 */
	@RequestMapping(value = "/get_push.rm")
	public void getPush(HttpServletResponse response, String pushId) {
		Pushs push = pushService.findById(pushId);
		JsonUtil.writeObj(response, push);
	}
	
	/**
	 * 获取提现收费比率
	 */
	@RequestMapping(value = "/get_cash_rate.rm")
	public void getCashRate(HttpServletResponse response) {
		CriterionMap map =new CriterionMap();
		SysConfig sysConfig=sysConfigService.getByName("CASH_RATE");
		map.put("cashRate", sysConfig.getConfigVal());
		JsonUtil.writeMap(response, map);
	}
}
