package com.flyme.base.system.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;
import com.flyme.base.system.area.pojo.Area;
import com.flyme.common.json.tree.TreeData;
import com.flyme.common.json.tree.TreeParams;
import com.flyme.common.json.view.TreeView;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.springmvc.controller.MbsBaseController;

/**
 * 区域选择组件
 * 
 * @author zyf
 * @date 2016-11-9
 */
@Controller
@RequestMapping("/component")
public class Tree_AreaController extends MbsBaseController {	
	
	/**
	 * 组件入口
	 */
	@RequestMapping("/area_tree_turn.do")
	public String layout(ModelMap modelMap) {
		return "component/left/area";
	}
		
	/**
	 * 区域树形
	 */
	@RequestMapping(value="/area_tree_data.do",method = RequestMethod.POST)
	@ResponseBody
	@JsonView(TreeView.class)
	public TreeData treedata(TreeData treeData, TreeParams treeParams) {
		List<Area> data = new ArrayList<Area>();
		CriterionMap map = new CriterionMap();
		String parentId=ObjectUtils.getString(treeParams.getId(),"");
		map.put("parentId", parentId);
		data = areaService.treeData(map);
		treeData.setData(data);
		return treeData;
	}
	
	
	
}