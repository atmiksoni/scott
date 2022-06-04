package com.flyme.business.workdetails.pojo;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.annotation.Pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.flyme.core.mybatis.abstractentity.AbstractEntity;
import java.math.BigDecimal;
/**
 * 
 * @author zyf
 * @date 2019-1-29
 */
@Pojo(name="")
@JsonInclude(Include.NON_NULL)
public class Workdetails extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private String workDetailsId=ObjectUtils.getUUID();
	/** 工程师姓名 */ 
	private String engineerId;
	/** 工资 */ 
	private BigDecimal charge;
	/** 报给客户工资 */ 
	private BigDecimal offerCharge;
	/** 所属单号 */ 
	private String orderno;

	public Workdetails(){
	}
	public Workdetails(String workDetailsId){
	    this.workDetailsId=workDetailsId;
	}
	
	public String getWorkDetailsId() {
		return workDetailsId;
	}
	public Workdetails setWorkDetailsId(String workDetailsId) {
		this.workDetailsId = workDetailsId;
		return this;
	}
	
	public String getEngineerId() {
		return engineerId;
	}
	public Workdetails setEngineerId(String engineerId) {
		this.engineerId = engineerId;
		return this;
	}
	
	public BigDecimal getCharge() {
		return charge;
	}
	public Workdetails setCharge(BigDecimal charge) {
		this.charge = charge;
		return this;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public BigDecimal getOfferCharge() {
		return offerCharge;
	}
	public void setOfferCharge(BigDecimal offerCharge) {
		this.offerCharge = offerCharge;
	}
	

}
