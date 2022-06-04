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
 * 设计职责管理(修改)
 * @author zyf
 * @date 2018-12-3
 */
@Controller
@RequestMapping("/obligation")
public class ObligationUpdateController extends MbsBaseController {
	@Autowired
	private IObligationService obligationService;
	/**
	 * 页面跳转
	 */
	@RequestMapping(value = "/obligation_edit_turn.do",method = RequestMethod.GET)
	public String turn(ModelMap modelMap,String obligationId) {
		Obligation obligation = obligationService.findById(obligationId);
		modelMap.addAttribute("obligation", obligation);
		String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			modelMap.addAttribute("languaged", languaged);
			modelMap.addAttribute("required", Language.required);
		}
		return "business/obligation/edit";
	}

	/**
	 * 提交
	 */
	@RequestMapping(value = "/obligation_edit_sub.do",method = RequestMethod.POST)
	@ResponseBody
	public ApiJson sub(ApiJson j,Obligation obligation) {
		ApiJson a=obligationService.edit(obligation);
		String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			a.setInfo(Language.ModifySuccess);
		}
		return a;
	}
}
