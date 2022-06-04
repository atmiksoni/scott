package com.flyme.weixin.base;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.base.rbac.accountinfo.pojo.AccountInfo;
import com.flyme.core.shiro.util.BaseShiroUtils;
import com.flyme.core.springmvc.authority.WxAuth;
import com.flyme.core.springmvc.controller.MbsBaseController;

/** 首页 */
@Controller
@RequestMapping("/weixin")
public class WeChat_index extends MbsBaseController {

	

	/***************
	 * 首页 2017/07/26
	 ********************/
	@RequestMapping(value = "/index.wx")
	@WxAuth
	public String homeData(ModelMap modelMap) {
		AccountInfo accountInfo = BaseShiroUtils.getWxAccountInfo();

		return "index/home";
	}
	

	

}
