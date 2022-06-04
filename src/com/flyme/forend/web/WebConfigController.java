package com.flyme.forend.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.flyme.base.system.area.pojo.Area;
import com.flyme.base.system.area.service.IAreaService;
import com.flyme.base.system.industry.pojo.Industry;
import com.flyme.base.system.industry.service.impl.IndustryService;
import com.flyme.common.util.map.CriterionMap;

@Controller
@RequestMapping("/web")
public class WebConfigController {
	
	@Autowired
	private IndustryService industryService;
	@Autowired
	private IAreaService areaService;
	
	/**
	 * 供货、采购、合作面板
	 */
	@RequestMapping("/panel.hm")
	public String panel(ModelMap modelMap,String flag) {
		modelMap.addAttribute("flag", flag);
		return "web/panel/panel";
	}
	/**
	 * 公司、产品面板
	 */
	@RequestMapping("/searchPanel.hm")
	public String searchPanel(ModelMap modelMap,String flag,String keyword) {
		modelMap.addAttribute("flag", flag);
		modelMap.addAttribute("keyword", keyword);
		return "web/searchpanel/searchpanel";
	}
	
	/**
	 * 省
	 */
	@RequestMapping("/provinceList.hm")
	@ResponseBody
	public CriterionMap provinceList(ModelMap modelMap) {
		CriterionMap map = new CriterionMap();
		List<CriterionMap> provinceList = areaService.selectByMap("selectProvince", map);
		map.clear();
		map.add("provinceList", provinceList);
		return map;
	}
	
	/**
	 * 市
	 */
	@RequestMapping("/cityList.hm")
	@ResponseBody
	public CriterionMap cityList(ModelMap modelMap,String parentId) {
		CriterionMap map = new CriterionMap();
		map.add("parentId", parentId);
		List<Area> cityList = areaService.selectByMap(map);
		map.clear();
		map.add("cityList", cityList);
		return map;
	}
	
	/**
	 * 区域
	 */
	@RequestMapping("/areaList.hm")
	@ResponseBody
	public CriterionMap areaList(ModelMap modelMap,String parentId) {
		CriterionMap map = new CriterionMap();
		map.add("parentId", parentId);
		List<Area> areaList = areaService.selectByMap(map);
		map.clear();
		map.add("areaList", areaList);
		return map;
	}
	/**
	 * 行业一级类型
	 */
	@RequestMapping("/industryTypeOneList.hm")
	@ResponseBody
	public CriterionMap industryTypeOneList() {
		CriterionMap map = new CriterionMap();
		List<Industry> industryOneList = industryService.selectByMap(map);
		map.clear();
		map.add("industryOneList", industryOneList);
		return map;
	}
	/**
	 * 行业二级类型
	 */
	@RequestMapping("/industryTypeTwoList.hm")
	@ResponseBody
	public CriterionMap industryTypetwoList(String industryPid) {
		CriterionMap map = new CriterionMap();
		map.add("industryPid", industryPid);
		List<Industry> industryTwoList = industryService.selectByMap(map);
		map.clear();
		map.add("industryTwoList", industryTwoList);
		return map;
	}
}
