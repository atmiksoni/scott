
package com.flyme.business.workdetails.controller;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.flyme.core.springmvc.controller.MbsBaseController;
import com.flyme.business.workdetails.service.IWorkdetailsService;
import com.flyme.business.workdetails.pojo.Workdetails;

/**
 * 
 * @author zyf
 * @date 2019-1-29
 */
@Controller
@RequestMapping("/workdetails")
public class WorkdetailsDetailController  extends MbsBaseController {

	@Autowired
	private IWorkdetailsService workdetailsService;
	/**
	 * 详细信息
	 */
	@RequestMapping(value = "/workdetails_details_turn.do")
	public String detail(ModelMap modelMap,String workdetailsId) {
		Workdetails workdetails = workdetailsService.findById(workdetailsId);
		modelMap.addAttribute("workdetails", workdetails);
		return "business/workdetails/edit";
	}
}