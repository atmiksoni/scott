
package com.flyme.business.obligation.controller;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.flyme.core.springmvc.controller.MbsBaseController;
import com.flyme.business.obligation.service.IObligationService;
import com.flyme.business.obligation.pojo.Obligation;

/**
 * 设计职责
 * @author zyf
 * @date 2018-12-3
 */
@Controller
@RequestMapping("/obligation")
public class ObligationDetailController  extends MbsBaseController {

	@Autowired
	private IObligationService obligationService;
	/**
	 * 详细信息
	 */
	@RequestMapping(value = "/obligation_details_turn.do")
	public String detail(ModelMap modelMap,String obligationId) {
		Obligation obligation = obligationService.findById(obligationId);
		modelMap.addAttribute("obligation", obligation);
		return "flyme/business/obligation/edit";
	}
}