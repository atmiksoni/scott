package com.flyme.business.obligation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 设计职责管理
 * @author zyf
 * @date 2018-12-3
 */
@Controller
@RequestMapping("/obligation")
public class ObligationIndexController extends MbsBaseController {

	/**
	 * 模块入口
	 */
	@RequestMapping(value = "/obligation_index.do",method = RequestMethod.GET)
	public String layout(ModelMap modelMap) {
		return "business/obligation/layout";
	}
}