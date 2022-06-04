package com.flyme.business.checkorder.service;
import com.flyme.core.mybatis.service.IBaseService;
import java.math.BigDecimal;
import java.util.List;
import com.flyme.business.checkorder.pojo.Checkorder;
import com.flyme.business.equipment.pojo.Equipment;
import com.flyme.business.equipmenttypeconect.pojo.Equipmenttypeconect;
import com.flyme.business.equipmenttypetwo.pojo.Equipmenttypetwo;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.util.map.CriterionMap;
public interface ICheckorderService extends IBaseService<Checkorder> {
	/**
	 * 检查工单添加
	 * @param accountInfoId 
	 */
	ApiJson addCheckOrder(Checkorder checkorder,Equipment equipment,String equipmenttypeconect,String parts,String equipmentcheckresult, String accountInfoId);
	/**
	 * 修改检查工单
	 * @param accountInfoId 
	 */
	ApiJson editCheckOrder(String checkOrderId,Checkorder checkOrder,Equipment equipment,String equipmenttypeconect,String parts,String equipmentcheckresult, String accountInfoId);
	/**
	 * 删除检查工单
	 */
	ApiJson delCheckOrder(String checkOrderId);
	/**
	 * 通过工程师Id 获取对应的检查工单列表
	 * @param engineerId
	 * @return
	 */
	List<CriterionMap> findListByEngineerId(String engineerId,BigDecimal lastLng,BigDecimal lastLat);
	/**
	 * 工作工单详情
	 * @param checkOrderId
	 */
	ApiJson CheckOrderDetails(String checkOrderId);
	/**
	 * 生成pdf
	 */
	void createPdf(String checkOrderId,Integer type);
}
