package com.flyme.app.bus;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flyme.base.system.serialno.service.ISerialNoService;
import com.flyme.business.checkorder.pojo.Checkorder;
import com.flyme.business.checkorder.service.ICheckorderService;
import com.flyme.business.engineer.pojo.Engineer;
import com.flyme.business.engineer.service.IEngineerService;
import com.flyme.business.equipment.pojo.Equipment;
import com.flyme.business.equipment.service.IEquipmentService;
import com.flyme.business.equipmentcheckresult.service.IEquipmentcheckresultService;
import com.flyme.business.equipmenttypeconect.service.IEquipmenttypeconectService;
import com.flyme.business.equipmenttypetwo.service.IEquipmenttypetwoService;
import com.flyme.business.parts.service.IPartsService;
import com.flyme.business.purchaseno.service.IPurchaseNoService;
import com.flyme.business.workorder.pojo.Workorder;
import com.flyme.business.workorder.service.IWorkorderService;
import com.flyme.business.worktime.service.IWorktimeService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.date.DateUtil;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.filters.CriteriaQuery;
import com.flyme.core.mybatis.filters.GroupOp;
import com.flyme.core.mybatis.filters.Sort;
import com.flyme.core.springmvc.controller.MbsBaseController;
/**
 * 检查工单接口
 */
@Controller
@RequestMapping("/api")
public class App_CheckOrder extends MbsBaseController {
	@Autowired
	private ICheckorderService checkorderService;
	@Autowired
	private ISerialNoService serialNoService;
	@Autowired
	private IEquipmentService equipmentService;
	@Autowired
	private IEquipmenttypetwoService equipmenttypetwoService;
	@Autowired
	private IPartsService partsService;
	@Autowired
	private IEquipmentcheckresultService equipmentcheckresultService;
	@Autowired
	private IWorkorderService workorderService;
	@Autowired
	private IWorktimeService worktimeService;
	@Autowired
	private IEngineerService engineerService;
	@Autowired
	private IEquipmenttypeconectService equipmenttypeconectService;
	@Autowired
	private IPurchaseNoService purchasenoService;
	/**
	 * 检测单号生成
	 */
	@RequestMapping(value = "/detectionNumber_add.rm")
	public void detectionNumber(HttpServletResponse response, HttpServletRequest request) {
		CriterionMap map = new CriterionMap();
		//GetTime getTime = new GetTime();
		String detectionNumber = serialNoService.updateSerialNo("", DateUtil.getSimDate());
		map.add("detectionNumber", detectionNumber);
		JsonUtil.writeMap(response, map);
	}
	/**
	 * 工作单号生成
	 */
	@RequestMapping(value = "/workNumber_add.rm")
	public void workNumber(HttpServletResponse response, HttpServletRequest request) {
		CriterionMap map = new CriterionMap();
		String workNumber =purchasenoService.getNewPurchaseNo();
		map.add("workNumber", workNumber);
		JsonUtil.writeMap(response, map);
	}
	/**
	 * 订购单号生成
	 */
	@RequestMapping(value = "/orderNumber_add.rm")
	public void orderNumber(HttpServletResponse response, HttpServletRequest request) {
		CriterionMap map = new CriterionMap();
		String orderNumber = serialNoService.updateSerialNo("", DateUtil.getSimDate());
		map.add("orderNumber", orderNumber);
		JsonUtil.writeMap(response, map);
	}
	/**
	 * 服务检测 检查工单添加
	 */
	@RequestMapping(value = "/checkorder_add.rm")
	public void checkOrderAdd(String engineerId,HttpServletResponse response, HttpServletRequest request,Checkorder checkorder,Equipment equipment,String equipmenttypeconect,String parts,String equipmentcheckresult) {
		ApiJson apiJson	= checkorderService.addCheckOrder(checkorder,equipment,equipmenttypeconect,parts,equipmentcheckresult,engineerId);
		JsonUtil.writeObj(response, apiJson);
	}
	/**
	 *工作卡 工单添加
	 */
	@RequestMapping(value = "/workCardOrder_add.rm")
	public void workCardOrderAdd(HttpServletResponse response, HttpServletRequest request,Equipment equipment,String engineerId,Workorder workorder,String worktime,String parts) {
		ApiJson apiJson=workorderService.addWorkCardOrder(equipment,workorder,worktime,parts,engineerId);
		JsonUtil.writeObj(response, apiJson);
	}	
	/**
	 *工作卡再来一单
	 */
	@RequestMapping(value = "/workCardOrder_next_add.rm")
	public void nextWorkCardOrderAdd(HttpServletResponse response, HttpServletRequest request,String workOrderId) {
		ApiJson apiJson=workorderService.addNextWorkCardOrder(workOrderId);
		JsonUtil.writeObj(response, apiJson);
	}	
	/**
	 * 检查工单修改
	 */
	@RequestMapping(value = "/checkorder_edit.rm")
	public void checkOrderEdit(HttpServletResponse response, HttpServletRequest request,String checkOrderId,String engineerId,Checkorder checkorder,Equipment equipment,String equipmenttypeconect,String parts,String equipmentcheckresult) {
		ApiJson apiJson	= checkorderService.editCheckOrder(checkOrderId,checkorder,equipment,equipmenttypeconect,parts,equipmentcheckresult,engineerId);
		JsonUtil.writeObj(response, apiJson);
	}
	/**
	 *工作卡 工单修改
	 */
	@RequestMapping(value = "/workCardOrder_edit.rm")
	public void workCardOrderEdit(HttpServletResponse response, HttpServletRequest request,String workOrderId,String engineerId,Equipment equipment,Workorder workorder,String worktime,String parts) {
		ApiJson apiJson= workorderService.editWorkCardOrder(equipment,workorder,worktime,parts,engineerId);
		JsonUtil.writeObj(response, apiJson);
	}
	/**
	 *工作卡 详情
	 */
	@RequestMapping(value = "/workCardOrder_details.rm")
	public void workCardOrderdetails(HttpServletResponse response, HttpServletRequest request,String workOrderId) {
		CriterionMap map = new CriterionMap();
		ApiJson j = new ApiJson();
		CriteriaQuery cq = new CriteriaQuery(GroupOp.and);
		CriteriaQuery cq1 = new CriteriaQuery(GroupOp.and);
		CriteriaQuery cq2 = new CriteriaQuery(GroupOp.and);
		CriteriaQuery cq3 = new CriteriaQuery(GroupOp.and);
		//基本信息
		cq.addSqlField("workOrderNo,firm.companyName,maintainDate,equipmentType.equipmentTypeName,w.workOrderId,w.equipmentId,situationInfo,repairStatus,repairContent,repairAdvise,imgs,signature,w.equipmentTypeId,w.firmId,w.pdfImg,equipmentType.equipmentTypeEnglishName,engineerSignature,nextRepairTime,totalWorkOrderNo");
		cq.eq("w.workOrderId", workOrderId);
		cq.createAlias("equipment", "equipment");
		cq.createAlias("equipmentType", "equipmentType");
		cq.createAlias("firm", "firm");
		List<CriterionMap> workorderList = workorderService.pagerMap(cq, true);
		if(ObjectUtils.isNotEmpty(workorderList)){
			map.add("workorderList",  workorderList.get(0));
		}
		String equipmentId = null;
		for(CriterionMap equipmentid : workorderList ){
			equipmentId = equipmentid.get("equipmentId");
		}
		//设备信息
		cq1.addSqlField("equipmentNo,equipmentAddress,manufacturer,factoryDate,isoNormal,capacity,orderNo");
		if(ObjectUtils.isEmpty(equipmentId)){
			cq1.eq("equipmentId", "88222");
			
		}else{
			cq1.eq("equipmentId", equipmentId);
		}	
		List<CriterionMap> equipmentList = equipmentService.pagerMap(cq1, true);
		if(ObjectUtils.isNotEmpty(equipmentList)){
			map.add("equipmentList",  equipmentList.get(0));
		}
		//工作时间
		cq2.addSqlField("engineer.engineerName,workTime,roadTime,overTime,doubleTime,workTimeId,w.engineerId,w.workTimeStart,w.roadTimeStart,w.overTimeStart,w.doubleTimeStart,w.workTimeEnd,w.roadTimeEnd,w.overTimeEnd,w.doubleTimeEnd,w.maintainDate");
		cq2.eq("w.workOrderId", workOrderId);
		cq2.createAlias("engineer", "engineer");
		
		List<CriterionMap> workTimeList = worktimeService.pagerMap(cq2, true);
		//配件信息
		cq3.addSqlField("partsName,partsNumber,partsId");
		//cq3.createAlias("", );
		if(ObjectUtils.isEmpty(equipmentId)){
			cq3.eq("equipmentId", "88222");
		}else{
			cq3.eq("p.equipmentId", equipmentId);
		}
		List<CriterionMap> partsList = partsService.pagerMap(cq3, true);
		map.add("workTimeList", workTimeList);
		map.add("partsList", partsList);
		j.setObject(map);
		JsonUtil.writeApiJson(response, j);
	}
	/**
	 * 检查工单删除
	 */
	@RequestMapping(value = "/checkorder_del.rm")
	public void checkOrderDel(HttpServletResponse response, HttpServletRequest request,String checkOrderId) {
		ApiJson apiJson	= checkorderService.delCheckOrder(checkOrderId);
		JsonUtil.writeObj(response, apiJson);
	}
	/**
	 * 工作卡删除
	 */
	@RequestMapping(value = "/workOrderDel_del.rm")
	public void woekOrderDel(HttpServletResponse response, HttpServletRequest request,String workOrderId) {
		ApiJson apiJson	= workorderService.delWorkOrder(workOrderId);
		JsonUtil.writeObj(response, apiJson);
	}
	/**
	 * 检查工单pdf
	 */
	@RequestMapping(value = "/checkorder_pdf.rm")
	public void checkOrderPdf(HttpServletResponse response, HttpServletRequest request,String checkOrderId,Integer type) {
		//ApiJson apiJson = checkorderService.CheckOrderDetails(checkOrderId);
		checkorderService.createPdf(checkOrderId,1);
	}
	
	/**
	 * 工作工单pdf
	 */
	@RequestMapping(value = "/workorder_pdf.rm")
	public void workorderPdf(HttpServletResponse response, HttpServletRequest request,String workOrderId,Integer type) {
		//ApiJson apiJson = workorderService.workorderPdf(workOrderId);
		workorderService.createPdf(workOrderId,2);
	}
	/**
	 * 检查工单详情
	 */
	@RequestMapping(value = "/checkorder_details.rm")
	public void checkOrderDetails(HttpServletResponse response, HttpServletRequest request,String checkOrderId,String equipmentTypeTwoId) {
		CriterionMap map = new CriterionMap();
		ApiJson j = new ApiJson();
		CriteriaQuery cq = new CriteriaQuery(GroupOp.and);
		CriteriaQuery cq1 = new CriteriaQuery(GroupOp.and);
		CriteriaQuery cq2 = new CriteriaQuery(GroupOp.and);
		CriteriaQuery cq3 = new CriteriaQuery(GroupOp.and);
		CriteriaQuery cq4 = new CriteriaQuery(GroupOp.and);
		CriteriaQuery cq5 = new CriteriaQuery(GroupOp.and);
		//基本信息
		cq.addSqlField("checkOrderNo,engineer.engineerName,firm.companyName,firm.firmId,maintainDate,equipmentType.equipmentTypeName,signature,c.equipmentTypeId,c.equipmentId,c.equipmentTypeRemarkId,c.equipmentTypeRemarkTwoId,c.pdfImg,equipmentType.equipmentTypeEnglishName,c.engineerSignature");
		cq.eq("c.checkOrderId", checkOrderId);
		cq.createAlias("equipmentType", "equipmentType");
		cq.createAlias("firm", "firm");
		cq.createAlias("engineer", "engineer");
		List<CriterionMap> checkOrderList = checkorderService.pagerMap(cq, false);
		String equipmentTypeId = null;
		String equipmentid = null;
		for(CriterionMap equipmentTypeid : checkOrderList ){
			equipmentTypeId = equipmentTypeid.get("equipmentTypeId");
			equipmentid = equipmentTypeid.get("equipmentId");
		}
		//设备信息
		cq1.addSqlField("equipmentNo,equipmentAddress,manufacturer,factoryDate,isoNormal,capacity,equipmentId,equipmentIntroduce");
		cq1.eq("equipmentId", equipmentid);
		cq1.eq("equipmentTypeId", equipmentTypeId);
		List<CriterionMap> equipmentList = equipmentService.pagerMap(cq1, false);
		
		//设备类型
		cq2.addSqlField("checkOrder.checkOrderNo,equipmentTypeTwo.equipmentTypeTwoName,equipmentTypeConectId,e.equipmentTypeTwoId");
		cq2.createAlias("checkOrder","checkOrder");
		cq2.createAlias("equipmentTypeTwo","equipmentTypeTwo");
		cq2.eq("e.checkOrderId", checkOrderId);
		List<CriterionMap> equipmenttypeconectList  = equipmenttypeconectService.pagerMap(cq2, false);
			List<String>netId=ObjectUtils.getArrayList();
				for (CriterionMap equipmenttypeconect : equipmenttypeconectList) {
					String nequipmentTypeTwoId=equipmenttypeconect.get("equipmentTypeTwoId");
					if(ObjectUtils.isNotEmpty(nequipmentTypeTwoId)){
						netId.add(nequipmentTypeTwoId);
					}
				}
		List<CriterionMap> equipmentTypeOneList=equipmenttypetwoService.findListByTypeTwoIds(equipmentTypeId,1,netId);
		List<CriterionMap> equipmentTypetwoList=equipmenttypetwoService.findListByTypeTwoIds(equipmentTypeId,2,netId);
		map.putArray("equipmentTypeOneList", equipmentTypeOneList); 
		map.putArray("equipmentTypetwoList", equipmentTypetwoList);
		//配件信息
		cq4.addSqlField("partsName,isoNormal,capacity,machinalModel,serialNo,brand,factoryDate,partsId");
		cq4.order("p.createDate",Sort.asc);
		cq4.eq("equipmentId", equipmentid);
		List<CriterionMap> partsList = partsService.pagerMap(cq4, false);
		//检查信息 equipmentTypeTwoId设备类型Id
		cq5.addSqlField("checkItem.checkItemId,checkItem.checkItemName,checkResult.checkResultName,equipmentStatus.equipmentStatusName,remark,imgs,e.equipmentTypeTwoId,e.checkResultId,e.equipmentStatusId,e.equipmentCheckResultId,checkItem.checkItemEnglishName,checkResult.checkResultEnglishName,equipmentStatus.equipmentStatusEnglishName");
		cq5.eq("e.equipmentId", equipmentid);
		cq5.order("e.indexs",Sort.asc);
		cq5.createAlias("checkItem", "checkItem");
		cq5.createAlias("checkResult", "checkResult"); 
		cq5.createAlias("equipmentStatus", "equipmentStatus");
		List<CriterionMap> equipmentcheckresultList = equipmentcheckresultService.pagerMap(cq5, false);
		map.add("equipmentcheckresult", equipmentcheckresultList);
		map.add("partsList", partsList);
		if(equipmentList.size()>0){
			map.add("equipmentList", equipmentList.get(0));
		}else{
			map.putObj("equipmentList", "");
		}
		if(checkOrderList.size()>0){
			map.add("checkOrderList", checkOrderList.get(0));
		}else{
			map.putObj("checkOrderList", "");
		}
	
		map.add("equipmenttypeconectList", equipmenttypeconectList);
		j.setObject(map);
		JsonUtil.writeApiJson(response, j);
	}
	/**
	 * 检查工单列表
	 */
	@RequestMapping(value = "/checkorder_list.rm")
	public void checkOrderList(HttpServletResponse response, HttpServletRequest request,String engineerId,BigDecimal lastLng,BigDecimal lastLat) {
		CriterionMap map = new CriterionMap();
		List<CriterionMap> checkOrderList=checkorderService.findListByEngineerId(engineerId,lastLng,lastLat);
		map.putArray("checkOrderList", checkOrderList);
		JsonUtil.writeMap(response, map);
	}
	/**
	 * 工作清单列表列表
	 */
	@RequestMapping(value = "/workorder_list.rm")
	public void workorderList(HttpServletResponse response, HttpServletRequest request,String engineerId,BigDecimal lastLng,BigDecimal lastLat) {
		CriterionMap map = new CriterionMap();
		CriteriaQuery cq = new CriteriaQuery(GroupOp.and);
		//Engineer engineer = new Engineer();
		Engineer engineerid = engineerService.findById(engineerId);
		if(ObjectUtils.isNotEmpty(engineerid.getLastLat())){
			engineerid.setLastLat(lastLat);
			engineerid.setLastLng(lastLng);
			engineerService.update(engineerid);
		}else{
			engineerid.setLastLng(new BigDecimal(116.3970060000));
			engineerid.setLastLat(new BigDecimal(39.9178540000));
			engineerService.update(engineerid);
		}
		cq.addSqlField("firm.companyName,workOrderNo,firm.Address,w.firmId,workOrderId");
		cq.order("w.createDate",Sort.desc);
		cq.eq("IsDel", 0);
		cq.addSql("DATE_FORMAT(w.createDate,'%Y-%m-%d') createDate");
		cq.eq("w.engineerId", engineerId);
		cq.createAlias("firm", "firm");
		cq.createAlias("engineer", "engineer");
		List<CriterionMap> workorderList = workorderService.pagerMap(cq, true);
		map.putArray("workorderList", workorderList);
		JsonUtil.writeMap(response, map);
	}
}
