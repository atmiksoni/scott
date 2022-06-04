package com.flyme.business.totalworkorder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 总工作工单管理
 * @author zyf
 * @date 2019-2-1
 */
@Controller
@RequestMapping("/totalworkorder")
public class TotalworkorderIndexController extends MbsBaseController {

	/**
	 * 模块入口
	 */
	@RequestMapping(value = "/totalworkorder_index.do",method = RequestMethod.GET)
	public String layout(ModelMap modelMap) {
		return "business/totalworkorder/layout";
	}
}