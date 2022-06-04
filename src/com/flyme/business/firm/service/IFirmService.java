package com.flyme.business.firm.service;
import com.flyme.core.mybatis.service.IBaseService;

import java.math.BigDecimal;

import com.flyme.business.firm.pojo.Firm;
import com.flyme.common.util.map.CriterionMap;

public interface IFirmService extends IBaseService<Firm> {
	/**
	 *  计算工人报单总金额
	 * @param firmId
	 * @param engineerId
	 * @param manpower
	 * @return
	 */
	BigDecimal getWorkerProfit(String firmId, String engineerId, BigDecimal manpower);
	/**
	 * 工人加班对客户收费计算
	 * @param normalWorktime
	 * @param workTimeStart
	 * @param workTimeEnd
	 * @param engineerId
	 * @return
	 */
	CriterionMap getOverTimePrice(Integer normalWorktime, String workTimeStart, String workTimeEnd,String firmId);
}
