package com.flyme.app.bus;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.aspectj.bridge.IMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.flyme.app.core.authorization.Authorization;
import com.flyme.app.core.authorization.CurrentUser;
import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.system.msgrecive.service.IMsgReciveService;
import com.flyme.business.checkitem.vo.ImageType.ImageTypeEnum;
import com.flyme.business.engineer.pojo.Engineer;
import com.flyme.business.engineer.service.IEngineerService;
import com.flyme.business.engineer.service.impl.EngineerService;
import com.flyme.business.equipmentcheckresult.pojo.Equipmentcheckresult;
import com.flyme.business.equipmentcheckresult.service.IEquipmentcheckresultService;
import com.flyme.business.equipmenttypetwo.service.IEquipmenttypetwoService;
import com.flyme.business.firm.pojo.Firm;
import com.flyme.business.firm.service.IFirmService;
import com.flyme.business.obligation.service.IObligationService;
import com.flyme.business.parts.service.IPartsService;
import com.flyme.business.profit.service.IProfitService;
import com.flyme.business.purchaseorder.service.IPurchaseOrderService;
import com.flyme.business.repaircompany.pojo.Repaircompany;
import com.flyme.business.repaircompany.service.IRepaircompanyService;
import com.flyme.business.workorder.pojo.Workorder;
import com.flyme.business.workorder.service.IWorkorderService;
import com.flyme.business.worktime.pojo.Worktime;
import com.flyme.business.worktime.service.IWorktimeService;
import com.flyme.business.worktime.service.impl.WorktimeService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.json.model.Upload;
import com.flyme.common.json.pqgrid.PqGrid;
import com.flyme.common.json.upload.UploadFile;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.context.PathUtils;
import com.flyme.common.util.conver.ConvertUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.mybatis.filters.CriteriaQuery;
import com.flyme.core.mybatis.filters.GroupOp;
import com.flyme.core.springmvc.controller.MbsBaseController;
import com.sun.javafx.collections.MappingChange.Map;

import sun.tools.jar.resources.jar;

/**
 * 公共列表模块
 */
@Controller
@RequestMapping("/api")
public class App_CommonalityList extends MbsBaseController{

	@Autowired
	private IMsgReciveService msgReciveService;
	@Autowired
	private IEngineerService engineerService;
	@Autowired
	private IFirmService firmService;
	@Autowired
	private IEquipmentcheckresultService  equipmentcheckresultService;
	@Autowired
	private IWorkorderService workorderService;
	@Autowired
	private IEquipmenttypetwoService equipmenttypetwoService;
	@Autowired
	private IWorktimeService worktimeService;
	@Autowired
	private IPartsService partsService;
	@Autowired
	private IObligationService ObligationService;
	@Autowired
	private IRepaircompanyService repaircompanyService;
	@Autowired
	private IPurchaseOrderService purchaseorderService;
	@Autowired
	private IProfitService profitService;
	/**
	 * 工程师列表
	 * 
	 * 
	 */
	@RequestMapping(value = "/engineerDetailed_list.rm")
	public void engineerDetailedList(String accountId,HttpServletResponse response,HttpServletRequest request,String name) {
		CriterionMap map = new CriterionMap();
		CriteriaQuery cq = new CriteriaQuery(GroupOp.and);
		cq.addSqlField("accountInfo.userName,departmentName,head,engineerId");
		cq.cn("engineerName", name);
		cq.createAlias("accountInfo", "accountInfo");
		Engineer engineer=engineerService.findById(accountId);
		cq.eq("repairCompanyId", engineer.getRepairCompanyId());
		List<CriterionMap> engineerList = engineerService.pagerMap(cq, false);
		map.add("engineerList", engineerList);
		JsonUtil.writeObj(response, engineerList);
	}
	/**
	 * 设计职责
	 * 
	 * 
	 */
	@RequestMapping(value = "/designResponsibility_list.rm")
	public void designResponsibility(HttpServletResponse response,HttpServletRequest request,Integer Type) {
		ApiJson j = new ApiJson();
		CriterionMap map = new CriterionMap();
		CriteriaQuery cq = new CriteriaQuery(GroupOp.and);
		CriteriaQuery cq2 = new CriteriaQuery(GroupOp.and);
		cq.addSqlField("obligationName");
		cq.eq("type", 1);
		cq.order("indexs","asc");
		List<CriterionMap> first = ObligationService.pagerMap(cq, true);
		cq2.addSqlField("obligationName");
		cq2.eq("type", 2);
		cq2.order("indexs","asc");
		List<CriterionMap> second = ObligationService.pagerMap(cq2, true);
		map.add("first", first);
		map.add("second", second);
		j.setObject(map);
		JsonUtil.writeObj(response, j);
	}
	/**
	 *  公司列表
	 * 
	 * 
	 */
	@RequestMapping(value = "/firmDetailed_list.rm")
	public void firmDetailedList(String accountId,HttpServletResponse response,HttpServletRequest request,String name) {
		CriterionMap map = new CriterionMap();
		CriteriaQuery cq = new CriteriaQuery(GroupOp.and);
		cq.addSqlField("companyName,Head,firmId,mobile,Address,linkman,Linkphone,compEmail,postcodes");
		cq.cn("companyName", name);
		cq.createAlias("accountInfo", "accountInfo");
		Engineer engineer=engineerService.findById(accountId);
		//查询该用户所属的公司 获取该维修公司的 客户公司列表
		cq.eq("repairCompanyId", engineer.getRepairCompanyId());
		List<CriterionMap> firmList = firmService.pagerMap(cq, false);
		map.add("firmList", firmList);
		JsonUtil.writeObj(response, firmList);
	}
	/**
	 * 检查信息图片上传
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * 
	 * 
	 */
	@RequestMapping(value = "/pictureUpload_list.rm")
	public void pictureUpload(HttpServletResponse response,HttpServletRequest request,String equipmentCheckResultId,Integer typeId) throws FileNotFoundException, IOException {
		ApiJson j = new ApiJson();
		Upload upload = new Upload();
		CriterionMap map = new CriterionMap();
		UploadFile uploadFile = new UploadFile(request);
		ImageTypeEnum.getText(typeId);
		String filePath = "imgs" + ImageTypeEnum.getText(typeId);
		uploadFile.setCusPath(filePath);
		upload = mbsCommonService.upload(uploadFile);
		List<String> images = upload.getFilsPaths();
		 map.add("images", ConvertUtils.listToString(images));
		 j.setObject(map);
		 JsonUtil.writeApiJson(response, j);
	}
	/**
	 * 添加公司信息
	 *                        
	 */
	@RequestMapping(value = "/corporationInfo_list.rm")
	public void corporationInfo(HttpServletResponse response,HttpServletRequest request,Firm firm) {
		ApiJson json = firmService.add(firm);
		JsonUtil.writeApiJson(response, json);
	}
	/**
	 * 公司LOGO上传
	 * 
	 */
	@RequestMapping(value = "/corporationLOGO_list.rm")
	public void corporationLOGO(HttpServletResponse response,HttpServletRequest request,String firmId) {
		ApiJson j = new ApiJson();
		CriterionMap map = new CriterionMap();
		Upload upload = new Upload();
		String filePath = "Head/" + firmId;
		UploadFile uploadFile = new UploadFile(request);
		uploadFile.setCusPath(filePath);
		upload = mbsCommonService.upload(uploadFile);
		String imgsPath = upload.getFilePath();
		Firm firm = new Firm();
		firm.setHead(imgsPath);
		 firmService.add(firm);
		 map.add("imgsPath", imgsPath);
		 j.setObject(map);
		 JsonUtil.writeApiJson(response, j);
	}
	/**
	 * 现场图片
	 * 
	 */
	@RequestMapping(value = "/localeImage_list.rm")
	public void localeImage(HttpServletResponse response,HttpServletRequest request,String workOrderId) {
		ApiJson j = new ApiJson();
		CriterionMap map = new CriterionMap();
		Upload upload = new Upload();
		String filePath = "imgs/" + workOrderId;
		UploadFile uploadFile = new UploadFile(request);
		uploadFile.setCusPath(filePath);
		upload = mbsCommonService.upload(uploadFile);
		String imgsPath = upload.getFilePath();
		Workorder workorder  = new Workorder();
		workorder.setImgs(imgsPath);
		 workorderService.add(workorder);
		 map.add("imgsPath", imgsPath);
		 j.setObject(map);
		 JsonUtil.writeApiJson(response, j);
	}
	/**
	 * 类型关联接口
	 * 
	 */
	@RequestMapping(value = "/typeRelevance_list.rm")
	public void typeRelevance(HttpServletResponse response,HttpServletRequest request,String equipmentTypeId) {
		ApiJson j = new ApiJson();
		CriterionMap map = new CriterionMap();
		CriteriaQuery cq = new CriteriaQuery(GroupOp.and);
		cq.addSqlField("equipmentTypeTwoName,equipmentTypeTwoId");
		cq.eq("equipmentTypeId", equipmentTypeId);
		List<CriterionMap> equipmenttypetwoList = equipmenttypetwoService.pagerMap(cq, true);
		 map.add("equipmenttypetwoList", equipmenttypetwoList);
		 j.setObject(map);
		 JsonUtil.writeApiJson(response, j);
	}
	/**
	 * 工作时间删除
	 * 
	 */
	@RequestMapping(value = "/workingHoursDelete_list.rm")
	public void workingHoursDelete(HttpServletResponse response,HttpServletRequest request,String workTimeId) {
		ApiJson j = new ApiJson();
			int i =  worktimeService.deleteById(workTimeId);
			if(i > 0){
				j.setAppInfo("删除成功");
			}
		 JsonUtil.writeApiJson(response, j);
	}
	/**
	 * 配件信息删除
	 * 
	 */
	@RequestMapping(value = "/partsDelete_list.rm")
	public void partsDelete(HttpServletResponse response,HttpServletRequest request,String partsId) {
		ApiJson j = new ApiJson();
		int i =   partsService.deleteById(partsId);
		//删除配件对应的采购订单表数据
		//并删除配件对应的采购订单表
		CriterionMap map =new CriterionMap();
		map.put("partsId", partsId);
		purchaseorderService.deleteByMap(map);
		if(i > 0){
			j.setAppInfo("删除成功");
		}
		 JsonUtil.writeApiJson(response, j);
	}
}
