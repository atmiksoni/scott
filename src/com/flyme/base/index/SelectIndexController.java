package com.flyme.base.index;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.flyme.common.util.ObjectUtils;
import com.flyme.core.springmvc.controller.MbsBaseController;
/**
 *  组件选择
 * @author zyf
 * @date 2016-11-10
 */
@Controller
@RequestMapping("/component")
public class SelectIndexController  extends MbsBaseController{

	
	@RequestMapping(value = "/select_index.do",method = RequestMethod.GET)
	public String turn(HttpServletRequest request,ModelMap modelMap) {
		String left=ObjectUtils.getParameter("left");
		String center=ObjectUtils.getParameter("center");//engineer
		modelMap.addAttribute("left",left);
		modelMap.addAttribute("center",center);
		return "component/index/index";
	}
}
