package com.flyme.app.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.base.system.company.service.ICompanyService;
import com.flyme.common.json.pqgrid.PqGrid;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.alias.PagerInfo;
import com.flyme.core.mybatis.filters.CriteriaQuery;
import com.flyme.core.mybatis.filters.GroupOp;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 企业注册
 */
@Controller
@RequestMapping("/api")
public class App_Company extends MbsBaseController {

	@Autowired
	private ICompanyService companyService;


	/**
	 * 企业列表
	 */
	@RequestMapping(value = "/company_list.rm")
	public void getCompanyList(HttpServletRequest request, HttpServletResponse response, PagerInfo pagerInfo) {
		CriteriaQuery cq = new CriteriaQuery(pagerInfo, GroupOp.and);
		PqGrid pqGrid = companyService.selectPagerList(request, cq);
		JsonUtil.writePqGrid(response, pqGrid);
	}

	/**
	 * 企业详情
	 */
	@RequestMapping(value = "/company_details.rm")
	public void getCompanyDetails(HttpServletResponse response, String companyId) {
		if (ObjectUtils.isEmpty(companyId)) {
			JsonUtil.writeError(response, "参数异常");
		}
		CriterionMap company = companyService.findMapById(companyId);
		CriterionMap map = new CriterionMap();
		map.putObj("company", company);
		JsonUtil.writeMap(response, map);
	}
}
