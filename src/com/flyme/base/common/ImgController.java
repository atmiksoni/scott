package com.flyme.base.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/img")
public class ImgController {
	
	/**
	 * 详细信息
	 */
	@RequestMapping(value = "/show_img_turn.do")
	public String detail(ModelMap modelMap, String img) {
		modelMap.addAttribute("img", img);
		return "component/center/img";
	}
}
