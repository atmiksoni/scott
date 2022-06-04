package com.flyme.business.workdetails.controller;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.util.ObjectUtils;
import com.flyme.core.shiro.util.SessionUtil;
import com.flyme.core.springmvc.controller.MbsBaseController;
import com.flyme.business.workdetails.service.IWorkdetailsService;
import com.flyme.base.rbac.account.vo.Language;
import com.flyme.business.workdetails.pojo.Workdetails;

/**
 * 管理(修改)
 * @author zyf
 * @date 2019-1-29
 */
@Controller
@RequestMapping("/workdetails")
public class WorkdetailsUpdateController extends MbsBaseController {
	@Autowired
	private IWorkdetailsService workdetailsService;
	/**
	 * 页面跳转
	 */
	@RequestMapping(value = "/workdetails_edit_turn.do",method = RequestMethod.GET)
	public String turn(ModelMap modelMap,String workdetailsId) {
		Workdetails workdetails = workdetailsService.findById(workdetailsId);
		modelMap.addAttribute("workdetails", workdetails);
		String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			modelMap.addAttribute("languaged", languaged);
			modelMap.addAttribute("required", Language.required);
		}
		return "business/workdetails/edit";
	}

	/**
	 * 提交
	 */
	@RequestMapping(value = "/workdetails_edit_sub.do",method = RequestMethod.POST)
	@ResponseBody
	public ApiJson sub(ApiJson j,Workdetails workdetails) {
		ApiJson a=workdetailsService.edit(workdetails);
		String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			a.setInfo(Language.ModifySuccess);
		}
		return a;
	}
}
