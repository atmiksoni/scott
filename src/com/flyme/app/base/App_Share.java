package com.flyme.app.base;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.app.core.authorization.Authorization;
import com.flyme.app.core.authorization.CurrentUser;
import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.system.share.pojo.Share;
import com.flyme.base.system.share.service.IShareService;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.springmvc.controller.MbsBaseController;

@Controller
@RequestMapping("/api")
public class App_Share extends MbsBaseController {
	
	@Autowired
	private IShareService shareService;
	
	/**
	 *分享信息
	 */
	@RequestMapping(value = "/share_info.rm")
	@Authorization
	public void  shareData(@CurrentUser Account account,HttpServletResponse response,Share share){
		String title="";
		String remark="";
		share.setShareType(account.getAccountType());
		share.setAccountInfoId(account.getAccountId());
		shareService.add(share);
		CriterionMap map = new CriterionMap();
		map.put("shareId", share.getShareId());
		map.put("shareTitle", title);
		map.put("shareDetails", remark);
		map.put("shareImg", "1");
		map.put("shareUrl", "1");
		map.put("shareType",share.getShareType());
		JsonUtil.writeMap(response, map);
	}

	/**
	 *分享信息
	 */
	@RequestMapping(value = "/share_app.rm")
	@Authorization
	public void  shareApp(@CurrentUser Account account,HttpServletResponse response){
		CriterionMap map = new CriterionMap();
		map.put("shareTitle", "1");
		map.put("shareDetails", "1");
		map.put("shareImg", "1");
		map.put("shareUrl", "1");
		JsonUtil.writeMap(response, map);
	}
}
