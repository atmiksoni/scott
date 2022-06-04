package com.flyme.business.workdetails.controller;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.flyme.common.json.pqgrid.PqGrid;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.mybatis.filters.CriteriaQuery;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.filters.GroupOp;
import com.flyme.core.shiro.util.SessionUtil;
import com.flyme.core.springmvc.controller.MbsBaseController;
import com.flyme.base.rbac.account.vo.Language;
import com.flyme.business.workdetails.service.IWorkdetailsService;

/**
 * 管理(列表)
 * @author zyf
 * @date 2019-1-29
 */
@Controller
@RequestMapping("/workdetails")
public class WorkdetailsListController  extends MbsBaseController{

	@Autowired
	private IWorkdetailsService workdetailsService;
	@RequestMapping(value = "/workdetails_list_turn.do")
	public String turn(ModelMap modelMap) {
	String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			modelMap.addAttribute("languaged", languaged);
		}
		return "business/workdetails/list";
	}
	
	@RequestMapping(value = "/workdetails_list_data.do")
	@ResponseBody
	public PqGrid data(PagerInfo pagerInfo) {
		CriteriaQuery cq = new CriteriaQuery(pagerInfo, GroupOp.and);
		List<CriterionMap> workdetailsList = workdetailsService.pagerMap(cq, true);
		return PqGrid.toPqGrid(workdetailsList, cq);
	}
}
