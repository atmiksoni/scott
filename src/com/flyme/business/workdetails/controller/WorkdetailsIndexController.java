package com.flyme.business.workdetails.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 管理
 * @author zyf
 * @date 2019-1-29
 */
@Controller
@RequestMapping("/workdetails")
public class WorkdetailsIndexController extends MbsBaseController {

	/**
	 * 模块入口
	 */
	@RequestMapping(value = "/workdetails_index.do",method = RequestMethod.GET)
	public String layout(ModelMap modelMap) {
		return "business/workdetails/layout";
	}
}