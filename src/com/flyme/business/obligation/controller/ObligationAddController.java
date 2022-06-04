package com.flyme.business.obligation.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flyme.base.rbac.account.vo.Language;
import com.flyme.business.obligation.pojo.Obligation;
import com.flyme.business.obligation.service.IObligationService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.util.ObjectUtils;
import com.flyme.core.shiro.util.SessionUtil;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 设计职责管理(添加)
 * @author zyf
 * @date 2018-12-3
 */
@Controller
@RequestMapping("/obligation")
public class ObligationAddController  extends MbsBaseController{
	@Autowired
	private IObligationService obligationService;
	/**
	 * 页面跳转
	 */
	@RequestMapping(value = "/obligation_add_turn.do",method = RequestMethod.GET)
	public String turn(ModelMap map) {
	String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			map.addAttribute("languaged", languaged);
			map.addAttribute("required", Language.required);
		}
		return "business/obligation/add";
	}

	/**
	 * 提交
	 */
	@RequestMapping(value = "/obligation_add_sub.do",method = RequestMethod.POST)
	@ResponseBody
	public ApiJson add(ApiJson j,Obligation  obligation){
		ApiJson a=obligationService.add(obligation);
		String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			a.setInfo(Language.AddSuccess);
		}
		return a;
	}
}
