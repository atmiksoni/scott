package com.flyme.business.workdetails.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * 管理(添加)
 * @author zyf
 * @date 2019-1-29
 */
@Controller
@RequestMapping("/workdetails")
public class WorkdetailsAddController  extends MbsBaseController{
	@Autowired
	private IWorkdetailsService workdetailsService;
	/**
	 * 页面跳转
	 */
	@RequestMapping(value = "/workdetails_add_turn.do",method = RequestMethod.GET)
	public String turn(ModelMap map) {
	String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			map.addAttribute("languaged", languaged);
			map.addAttribute("required", Language.required);
		}
		return "business/workdetails/add";
	}

	/**
	 * 提交
	 */
	@RequestMapping(value = "/workdetails_add_sub.do",method = RequestMethod.POST)
	@ResponseBody
	public ApiJson add(ApiJson j,Workdetails  workdetails){
		ApiJson a=workdetailsService.add(workdetails);
		String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			a.setInfo(Language.AddSuccess);
		}
		return a;
	}
}
