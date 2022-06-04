package com.flyme.app.base;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.app.core.authorization.Authorization;
import com.flyme.app.core.authorization.CurrentUser;
import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.system.collecon.pojo.Collecon;
import com.flyme.base.system.collecon.service.IColleconService;
import com.flyme.common.constants.ColleconTypeConstants;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 收藏关注
 */
@Controller
@RequestMapping("/api")
public class App_Collecon extends MbsBaseController {

	@Autowired
	private IColleconService colleconService;
	
	/**
	 * 收藏列表
	 */
	@RequestMapping(value = "/collecon_list.rm")
	@Authorization
	public void colleconList(@CurrentUser Account account, HttpServletResponse response, String targetType,PagerInfo pagerInfo,String lng,String lat ) {
		CriterionMap map = new CriterionMap();
		List<CriterionMap> colleconList=colleconService.listByPageInfo(account.getAccountId(), pagerInfo, targetType,lng,lat);
		map.put("colleconList", colleconList);
		JsonUtil.writeMap(response, map);
	}
	
	/**
	 * 用户添加/取消关注
	 */
	@RequestMapping(value = "/collecon_add.rm")
	@Authorization
	public void colleconAdd(@CurrentUser Account account, HttpServletResponse response, String targetId, String targetType) {
		Collecon collecon = colleconService.findByTargetId(account.getAccountId(), targetId, targetType);
		if (!ObjectUtils.isNotEmpty(collecon)) {
			collecon = new Collecon();
			collecon.setAccountInfoId(account.getAccountId());
			collecon.setTargetId(targetId);
			collecon.setColleconType(ColleconTypeConstants.ColleconType_GZ);
			collecon.setTargetType(targetType);
			colleconService.insert(collecon);
			JsonUtil.writeOk(response, "收藏成功");
		}else{
			JsonUtil.writeError(response, "不可重复收藏");
		}
	}

	/**
	 * 用户添加/取消收藏
	 */
	@RequestMapping(value = "/collecon_delete.rm")
	@Authorization
	public void colleconDelete(@CurrentUser Account account, HttpServletResponse response, String targetId, String targetType) {
		String accountId = account.getAccountId();
		Collecon collecon = colleconService.findByTargetId(accountId, targetId, targetType);
		if (ObjectUtils.isNotEmpty(collecon)) {
			colleconService.delById(collecon.getColleconId());
		}
		JsonUtil.writeOk(response, "取消成功");
	}
	
	/**
	 * 是否收藏
	 */
	@RequestMapping(value = "/collecon_is.rm")
	@Authorization
	public void colleconIs(@CurrentUser Account account, HttpServletResponse response, String targetId, String targetType) {
		Collecon collecon = colleconService.findByTargetId(account.getAccountId(), targetId, targetType);
		if (ObjectUtils.isNotEmpty(collecon)) {
			JsonUtil.writeOk(response, "1");
		}else{
			JsonUtil.writeOk(response, "0");
		}
	}
}
