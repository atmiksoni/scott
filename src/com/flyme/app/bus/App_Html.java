package com.flyme.app.bus;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.base.system.html.pojo.Html;
import com.flyme.base.system.html.service.IHtmlService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.core.springmvc.controller.MbsBaseController;

@Controller
@RequestMapping("/api")
public class App_Html extends MbsBaseController {

	@Autowired
	private IHtmlService htmlService;

	/**
	 * app静态页面
	 */
	@RequestMapping("/html.rm")
	public void index(HttpServletResponse response, String key) {
		Html html = htmlService.findByHtmlKey(key);
		JsonUtil.writeObj(response,html);
	}

	/**
	 * 查询静态页面
	 */
	@RequestMapping(value = "/html_find.rm")
	public void find(HttpServletResponse response, Integer type) {
		ApiJson j = new ApiJson();
		JsonUtil.writeApiJson(response, j);
	}
}
