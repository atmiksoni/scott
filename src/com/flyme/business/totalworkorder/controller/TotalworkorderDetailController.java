
package com.flyme.business.totalworkorder.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.business.totalworkorder.pojo.Totalworkorder;
import com.flyme.business.totalworkorder.service.ITotalworkorderService;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 总工作工单
 * @author zyf
 * @date 2019-2-1
 */
@Controller
@RequestMapping("/totalworkorder")
public class TotalworkorderDetailController  extends MbsBaseController {

	@Autowired
	private ITotalworkorderService totalworkorderService;
	/**
	 * 详细信息
	 */
	@RequestMapping(value = "/totalworkorder_details_turn.do")
	public String detail(ModelMap modelMap,String totalworkorderId) {
		Totalworkorder totalworkorder = totalworkorderService.findById(totalworkorderId);
		modelMap.addAttribute("totalworkorder", totalworkorder);
		return "business/totalworkorder/edit";
	}
}