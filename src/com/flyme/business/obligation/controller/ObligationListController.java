package com.flyme.business.obligation.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flyme.base.rbac.account.vo.Language;
import com.flyme.business.obligation.service.IObligationService;
import com.flyme.common.json.pqgrid.PqGrid;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.mybatis.filters.CriteriaQuery;
import com.flyme.core.mybatis.filters.GroupOp;
import com.flyme.core.mybatis.filters.Sort;
import com.flyme.core.shiro.util.SessionUtil;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 设计职责管理(列表)
 * @author zyf
 * @date 2018-12-3
 */
@Controller
@RequestMapping("/obligation")
public class ObligationListController  extends MbsBaseController{

	@Autowired
	private IObligationService obligationService;
	@RequestMapping(value = "/obligation_list_turn.do")
	public String turn(ModelMap modelMap) {
	String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			modelMap.addAttribute("languaged", languaged);
		}
		return "business/obligation/list";
	}
	
	@RequestMapping(value = "/obligation_list_data.do")
	@ResponseBody
	public PqGrid data(PagerInfo pagerInfo) {
		CriteriaQuery cq = new CriteriaQuery(pagerInfo, GroupOp.and);
		cq.order("type",Sort.asc);
		List<CriterionMap> obligationList = obligationService.pagerMap(cq, true);
		return PqGrid.toPqGrid(obligationList, cq);
	}
}
