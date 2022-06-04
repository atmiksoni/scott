package com.flyme.base.system.component;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flyme.base.rbac.account.vo.Language;
import com.flyme.business.engineer.service.IEngineerService;
import com.flyme.business.firm.service.IFirmService;
import com.flyme.business.repaircompany.pojo.Repaircompany;
import com.flyme.common.constants.RoleConstants;
import com.flyme.common.json.pqgrid.PqGrid;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.mybatis.filters.CriteriaQuery;
import com.flyme.core.mybatis.filters.GroupOp;
import com.flyme.core.shiro.util.SessionUtil;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 *  组件选择
 * @author zyf
 * @date 2016-11-10
 */
@Controller
@RequestMapping("/component")
public class SelectEngineerController  extends MbsBaseController{

	@Autowired
	private IEngineerService engineerService;
	@Autowired
	private IFirmService firmService;
	
	/**
	 * 组件入口
	 */
	@RequestMapping("/select_engineer_turn.do")
	public String layout(ModelMap modelMap) {
		String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			modelMap.addAttribute("languaged", languaged);
		}
		return "component/center/engineer";
	}
	

	@RequestMapping(value = "/select_firm_turn.do")
	public String layout1(ModelMap modelMap) {
	String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			modelMap.addAttribute("languaged", languaged);
		}
		return "component/center/firm";
	}
	
	
	@RequestMapping(value = "/select_firm_data.do")
	@ResponseBody
	public PqGrid data1(PagerInfo pagerInfo) {
		String searchContent = ObjectUtils.getParameter("searchContent");
		CriteriaQuery cq = new CriteriaQuery(pagerInfo, GroupOp.and);
		cq.cn("companyName", searchContent);
		Repaircompany repaircompany=SessionUtil.get(RoleConstants.ROLE_COMPANY);
		cq.eq("repairCompanyId", repaircompany.getRepairCompanyId());
		List<CriterionMap> firmList = firmService.pagerMap(cq, true);
		return PqGrid.toPqGrid(firmList, cq);
	}
	
	
	@RequestMapping(value = "/select_engineer_data.do")
	@ResponseBody
	public PqGrid data(PagerInfo pagerInfo) {
		CriteriaQuery cq = new CriteriaQuery(pagerInfo, GroupOp.and);
		String searchContent = ObjectUtils.getParameter("searchContent");
		cq.cn("engineerName", searchContent);
		cq.createAlias("accountInfo", "accountInfo");
		Repaircompany repaircompany=SessionUtil.get(RoleConstants.ROLE_COMPANY);
		cq.eq("repairCompanyId", repaircompany.getRepairCompanyId());
		List<CriterionMap> engineerList = engineerService.pagerMap(cq, true);
		return PqGrid.toPqGrid(engineerList, cq);
	}
	
}
