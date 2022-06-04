package com.flyme.business.engineer.service;
import com.flyme.core.mybatis.service.IBaseService;
import com.flyme.business.engineer.pojo.Engineer;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.util.map.CriterionMap;

public interface IEngineerService extends IBaseService<Engineer> {
		int  insertEngineer();
		/**
		 * 计算工程师的工资
		 * @param type  为加班的类型 0 正常工资 1 1.5倍工资 2两倍工资
		 * @param startTime
		 * @param endTime
		 * @param engineerId
		 * @return
		 */
		CriterionMap getOverTimePrice(Integer type, String startTime, String endTime, String engineerId);
}
