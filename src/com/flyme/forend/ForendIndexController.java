package com.flyme.forend;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.springmvc.controller.MbsBaseController;
import com.flyme.forend.web.core.springmvc.webority.WebAuth;

@Controller
@RequestMapping("/web")
public class ForendIndexController extends MbsBaseController {

	/**
	 * 首页
	 */
	@RequestMapping("/index.hm")
	@WebAuth(webCheck = false)
	public String index(HttpServletRequest request) {
		return "scott";
	}
	
	/**
	 * 公共头部
	 */
	@RequestMapping("/head.hm")
	@WebAuth(webCheck = false)
	public String head(HttpServletRequest request) {
		return "head";
	}

	/**
	 * 公共底部
	 */
	@RequestMapping("/footer.hm")
	@WebAuth(webCheck = false)
	public String footer(ModelMap modelMap) {
		return "footer";
	}

	/**
	 * 公共侧栏
	 */
	@RequestMapping("/sidebar.hm")
	public String sidebar(HttpServletRequest request, PagerInfo pagerInfo, ModelMap modelMap) {
		return "index/sidebar";
	}
}
