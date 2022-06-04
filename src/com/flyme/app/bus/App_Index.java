package com.flyme.app.bus;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.rbac.account.vo.AccountTypeEnum;
import com.flyme.base.system.msgrecive.service.IMsgReciveService;
import com.flyme.base.system.notice.service.INoticeService;
import com.flyme.base.system.slider.pojo.Slider;
import com.flyme.base.system.slider.service.ISliderService;
import com.flyme.common.constants.Global;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 首页相关接口
 */
@Controller
@RequestMapping("/api")
public class App_Index extends MbsBaseController {

	@Autowired
	private ISliderService sliderService;
	@Autowired
	private IMsgReciveService msgReciveService;
	@Autowired
	private INoticeService noticeService;

	

	/**
	 * 首页数据
	 */
	@RequestMapping(value = "/index.rm")
	public void getIndex(HttpServletResponse response, String accountId, String lng, String lat) {
		CriterionMap map = new CriterionMap();
		map.put("msgCount", 0);
		/* 轮播图 */
		List<Slider> sliderList = sliderService.selectByType("App_Index");

		/** 首页公告 */
		List<CriterionMap>noticeList = noticeService.getListForIndex();
		
		/** 特色服务 */
		
		/** 热门服务 */
		
		
		/** 未读消息 */
		if (ObjectUtils.isNotEmpty(accountId)) {
			Account account = accountService.findById(accountId);
			if(account.getIsEnable().equals(Global.INT_DISABLE)){
				JsonUtil.writeError(response, "您的账号已被禁用，请联系客服人员");
				return;
			}
			account.setAccountType(AccountTypeEnum.Vip.getValue());
			msgReciveService.initMSgRecive(accountId, AccountTypeEnum.Vip.getValue());
			int msgCount = msgReciveService.countByAccountId(accountId);
			map.put("msgCount", msgCount);
		}
		
		map.putArray("sliderList", sliderList);
		map.putArray("noticeList", noticeList);
		JsonUtil.writeMap(response, map);
	}
	
	/**
	 * 首页数据
	 */
	@RequestMapping(value = "/index_search.rm")
	public void indexSearch(HttpServletResponse response, String accountId, String lng, String lat,String searchs) {
		CriterionMap map = new CriterionMap();
		map.put("msgCount", 0);
		
		/** 热门服务 */
		PagerInfo pagerInfo=new PagerInfo();
		pagerInfo.setIsPage(false);
		
		
		
		JsonUtil.writeMap(response, map);
	}
}