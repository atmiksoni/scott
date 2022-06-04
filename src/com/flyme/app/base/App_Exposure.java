package com.flyme.app.base;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.app.core.authorization.Authorization;
import com.flyme.app.core.authorization.CurrentUser;
import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.system.exposure.service.IExposureService;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.date.DateUtil;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.filters.CriteriaQuery;
import com.flyme.core.mybatis.filters.GroupOp;
import com.flyme.core.mybatis.filters.Sort;
import com.flyme.core.springmvc.controller.MbsBaseController;

@Controller
@RequestMapping("/api")
public class App_Exposure extends MbsBaseController {
	
	@Autowired
	private IExposureService exposureService;
	
	/**
	 *曝光台信息
	 */
	@RequestMapping(value = "/exposure_info.rm")
	@Authorization
	public void  shareData(@CurrentUser Account account,HttpServletResponse response){
		CriterionMap map = new CriterionMap();
		CriteriaQuery cq = new CriteriaQuery(GroupOp.and);
		cq.addSqlField("*");
		cq.ge("createDate",DateUtil.getFirstDayOfMonth());
		cq.order("createDate",Sort.desc);
		List<CriterionMap> exposureList=exposureService.pagerMap(cq, false);
		map.put("exposureList", exposureList);
		JsonUtil.writeMap(response, map);
	}
}
