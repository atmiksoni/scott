package com.flyme.business.repaircompany.pojo;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.annotation.Pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.flyme.core.mybatis.abstractentity.AbstractEntity;
/**
 * 维修公司信息
 * @author zyf
 * @date 2019-1-21
 */
@Pojo(name="维修公司信息")
@JsonInclude(Include.NON_NULL)
public class Repaircompany extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private String repairCompanyId=ObjectUtils.getUUID();
	/** 用户Id */ 
	private String accountInfoId;
	/** 公司名称 */ 
	private String companyName;
	/** 所属地址 */ 
	private String address;
	/** 公司电话 */ 
	private String mobile;
	/** 可添加的工程师数量 */ 
	private String engineerCount;
	/** 联系人 */ 
	private String linkman;
	/** 联系电话 */ 
	private String linkphone;
	/** 联系邮箱 */ 
	private String compEmail;
	/** 联系邮箱 */ 
	private String logo;
	/** 账户到期时间 */ 
	private String expireDate;
	/** 银行卡号 */ 
	private String bankCard;

	public Repaircompany(){
	}
	public Repaircompany(String repairCompanyId){
	    this.repairCompanyId=repairCompanyId;
	}
	
	public String getRepairCompanyId() {
		return repairCompanyId;
	}
	public Repaircompany setRepairCompanyId(String repairCompanyId) {
		this.repairCompanyId = repairCompanyId;
		return this;
	}
	
	public String getAccountInfoId() {
		return accountInfoId;
	}
	public Repaircompany setAccountInfoId(String accountInfoId) {
		this.accountInfoId = accountInfoId;
		return this;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public Repaircompany setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}
	
	public String getAddress() {
		return address;
	}
	public Repaircompany setAddress(String address) {
		this.address = address;
		return this;
	}
	
	public String getMobile() {
		return mobile;
	}
	public Repaircompany setMobile(String mobile) {
		this.mobile = mobile;
		return this;
	}
	
	public String getEngineerCount() {
		return engineerCount;
	}
	public Repaircompany setEngineerCount(String engineerCount) {
		this.engineerCount = engineerCount;
		return this;
	}
	
	public String getLinkman() {
		return linkman;
	}
	public Repaircompany setLinkman(String linkman) {
		this.linkman = linkman;
		return this;
	}
	
	public String getLinkphone() {
		return linkphone;
	}
	public Repaircompany setLinkphone(String linkphone) {
		this.linkphone = linkphone;
		return this;
	}
	
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getCompEmail() {
		return compEmail;
	}
	public Repaircompany setCompEmail(String compEmail) {
		this.compEmail = compEmail;
		return this;
	}
	
	public String getExpireDate() {
		return expireDate;
	}
	public Repaircompany setExpireDate(String expireDate) {
		this.expireDate = expireDate;
		return this;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	
}
