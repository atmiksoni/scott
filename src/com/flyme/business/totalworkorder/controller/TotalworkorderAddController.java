package com.flyme.business.totalworkorder.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flyme.base.rbac.account.vo.Language;
import com.flyme.business.totalworkorder.pojo.Totalworkorder;
import com.flyme.business.totalworkorder.service.ITotalworkorderService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.util.ObjectUtils;
import com.flyme.core.shiro.util.SessionUtil;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 总工作工单管理(添加)
 * @author zyf
 * @date 2019-2-1
 */
@Controller
@RequestMapping("/totalworkorder")
public class TotalworkorderAddController  extends MbsBaseController{
	@Autowired
	private ITotalworkorderService totalworkorderService;
	/**
	 * 页面跳转
	 */
	@RequestMapping(value = "/totalworkorder_add_turn.do",method = RequestMethod.GET)
	public String turn(ModelMap map) {
	String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			map.addAttribute("languaged", languaged);
			map.addAttribute("required", Language.required);
		}
		return "flyme/business/totalworkorder/add";
	}

	/**
	 * 提交
	 */
	@RequestMapping(value = "/totalworkorder_add_sub.do",method = RequestMethod.POST)
	@ResponseBody
	public ApiJson add(ApiJson j,Totalworkorder  totalworkorder){
		ApiJson a=totalworkorderService.add(totalworkorder);
		String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			a.setInfo(Language.AddSuccess);
		}
		return a;
	}
}
