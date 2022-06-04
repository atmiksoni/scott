package com.flyme.base.activity.designer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flyme.core.springmvc.controller.MbsBaseController;

@Controller
@RequestMapping("/designer")
public class DesignerIndexController extends MbsBaseController {

	/**
	 * 设计器首页
	 */
	@RequestMapping("/index.do")
	public String index(String communityId, ModelMap modelMap) {
		return "designer/index";
	}

	/**
	 * 属性页面
	 */
	@RequestMapping("/properties.do")
	public String about_us(ModelMap modelMap) {
		return "designer/properties";
	}

	/**
	 * 获取流程
	 */
	@ResponseBody
	@RequestMapping("/get_process.do")
	public String getProcess(ModelMap modelMap) {
		return null;
	}

}
