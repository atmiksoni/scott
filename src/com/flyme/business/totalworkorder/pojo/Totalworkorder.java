package com.flyme.business.totalworkorder.pojo;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.annotation.Pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.flyme.core.mybatis.abstractentity.AbstractEntity;
/**
 * 总工作工单
 * @author zyf
 * @date 2019-2-1
 */
@Pojo(name="总工作工单")
@JsonInclude(Include.NON_NULL)
public class Totalworkorder extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private String totalWorkOrderId=ObjectUtils.getUUID();
	/** 总工单编号 */ 
	private String totalWorkOrderNo;
	/** 客户Id */ 
	private String firmId;
	/** 是否删除 */ 
	private Integer IsDel;

	public Totalworkorder(){
	}
	public Totalworkorder(String totalWorkOrderId){
	    this.totalWorkOrderId=totalWorkOrderId;
	}
	
	public String getTotalWorkOrderId() {
		return totalWorkOrderId;
	}
	public Totalworkorder setTotalWorkOrderId(String totalWorkOrderId) {
		this.totalWorkOrderId = totalWorkOrderId;
		return this;
	}
	
	public String getTotalWorkOrderNo() {
		return totalWorkOrderNo;
	}
	public Totalworkorder setTotalWorkOrderNo(String totalWorkOrderNo) {
		this.totalWorkOrderNo = totalWorkOrderNo;
		return this;
	}
	
	public String getFirmId() {
		return firmId;
	}
	public Totalworkorder setFirmId(String firmId) {
		this.firmId = firmId;
		return this;
	}
	
	public Integer getIsDel() {
		return IsDel;
	}
	public Totalworkorder setIsDel(Integer IsDel) {
		this.IsDel = IsDel;
		return this;
	}
}
