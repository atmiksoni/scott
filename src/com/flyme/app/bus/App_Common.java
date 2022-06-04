package com.flyme.app.bus;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.flyme.business.checkitem.service.ICheckitemService;
import com.flyme.business.checkresult.service.ICheckresultService;
import com.flyme.business.equipmentstatus.service.IEquipmentstatusService;
import com.flyme.business.equipmenttype.service.IEquipmenttypeService;
import com.flyme.business.equipmenttypetwo.service.IEquipmenttypetwoService;
import com.flyme.business.equipmenttypetwo.service.impl.EquipmenttypetwoService;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.springmvc.controller.MbsBaseController;
/**
 * 公共接口
 */
@Controller
@RequestMapping("/api")
public class App_Common extends MbsBaseController {
	@Autowired
	private IEquipmenttypeService equipmenttypeService;
	@Autowired
	private ICheckitemService checkitemService;
	@Autowired
	private ICheckresultService checkresultService;
	@Autowired
	private IEquipmentstatusService equipmentstatusService;
	@Autowired
	private IEquipmenttypetwoService equipmenttypetwoService;
	/**
	 * 设备类型列表
	 */
	@RequestMapping(value = "/equipment_type_list.rm")
	public void equipmentTypeList(HttpServletResponse response, HttpServletRequest request,String equipmentTypeId) {
		CriterionMap map = new CriterionMap();
		List<CriterionMap> equipmentTypeList=equipmenttypeService.findListByTypeId("");
		map.putArray("equipmentTypeList", equipmentTypeList);
		JsonUtil.writeMap(response, map);
	}
	/**
	 * 设备类型列表二
	 */
	@RequestMapping(value = "/equipmentTwo_type_list.rm")
	public void equipmentTypeTwoList(HttpServletResponse response, HttpServletRequest request, String equipmentTypeId,Integer type) {
		CriterionMap map = new CriterionMap();
		List<CriterionMap> equipmentTypeOneList=equipmenttypetwoService.findListByTypeTwoId(equipmentTypeId,type=1);
		List<CriterionMap> equipmentTypetwoList=equipmenttypetwoService.findListByTypeTwoId(equipmentTypeId,type=2);
		map.putArray("equipmentTypeOneList", equipmentTypeOneList);
		map.putArray("equipmentTypetwoList", equipmentTypetwoList);
		JsonUtil.writeMap(response, map);
	}
	/**
	 * 检查项目列表 ????????
	 */
	@RequestMapping(value = "/check_item_list.rm")
	public void checkItemList(HttpServletResponse response, HttpServletRequest request, String equipmentTypeTwoId) {
		CriterionMap map = new CriterionMap();
		List<CriterionMap> checkItermList=checkitemService.findListByEquipmentTypeId(equipmentTypeTwoId);
		map.putArray("checkItermList", checkItermList);
		JsonUtil.writeMap(response, map);
	}
	
	/**
	 * 检查结果列表
	 */
	@RequestMapping(value = "/check_result_list.rm")
	public void checkResultList(HttpServletResponse response, HttpServletRequest request) {
		CriterionMap map = new CriterionMap();
		List<CriterionMap> checkResultList=checkresultService.findList();
		map.putArray("checkResultList", checkResultList);
		JsonUtil.writeMap(response, map);
	}
	/**
	 * 设备状态列表
	 */
	@RequestMapping(value = "/equipment_status_list.rm")
	public void EquipmentstautsList(HttpServletResponse response, HttpServletRequest request) {
		CriterionMap map = new CriterionMap();
		List<CriterionMap> equipmentstatusList=equipmentstatusService.findList();
		map.putArray("equipmentstatusList", equipmentstatusList);
		JsonUtil.writeMap(response, map);
	}
}
