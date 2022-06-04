package com.flyme.business.purchaseno.service;
import com.flyme.business.purchaseno.pojo.PurchaseNo;
import com.flyme.core.mybatis.service.IBaseService;

public interface IPurchaseNoService extends IBaseService<PurchaseNo> {
	/**
	 * 获取最新采购订单号
	 * @return
	 */
	String getNewPurchaseNo();
}
