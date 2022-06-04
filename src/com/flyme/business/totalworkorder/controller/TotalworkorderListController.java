package com.flyme.business.totalworkorder.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flyme.base.rbac.account.vo.Language;
import com.flyme.business.totalworkorder.service.ITotalworkorderService;
import com.flyme.common.json.pqgrid.PqGrid;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.mybatis.filters.CriteriaQuery;
import com.flyme.core.mybatis.filters.GroupOp;
import com.flyme.core.shiro.util.SessionUtil;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 总工作工单管理(列表)
 * @author zyf
 * @date 2019-2-1
 */
@Controller
@RequestMapping("/totalworkorder")
public class TotalworkorderListController  extends MbsBaseController{

	@Autowired
	private ITotalworkorderService totalworkorderService;
	@RequestMapping(value = "/totalworkorder_list_turn.do")
	public String turn(ModelMap modelMap) {
	String  languaged=SessionUtil.get(Language.English);
		if(ObjectUtils.isNotEmpty(languaged)){
			modelMap.addAttribute("languaged", languaged);
		}
		return "business/totalworkorder/list";
	}
	
	@RequestMapping(value = "/totalworkorder_list_data.do")
	@ResponseBody
	public PqGrid data(PagerInfo pagerInfo) {
		CriteriaQuery cq = new CriteriaQuery(pagerInfo, GroupOp.and);
		List<CriterionMap> totalworkorderList = totalworkorderService.pagerMap(cq, true);
		return PqGrid.toPqGrid(totalworkorderList, cq);
	}
}
