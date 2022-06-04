package com.flyme.base.system.component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 *  组件选择
 * @author zyf
 * @date 2016-11-10
 */
@Controller
@RequestMapping("/component")
public class SelectManagerController  extends MbsBaseController{

	/**
	 * 组件入口
	 */
	@RequestMapping("/select_manager_turn.do")
	public String layout(ModelMap modelMap) {
		return "component/center/manager";
	}
	
	/**
	 * 组件入口
	 */
	@RequestMapping("/select_factorymanager_turn.do")
	public String factorylayout(ModelMap modelMap) {
		return "component/center/factorymanager";
	}
}
