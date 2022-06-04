package com.flyme.business.equipmentstatus.pojo;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.annotation.Pojo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.flyme.core.mybatis.abstractentity.AbstractEntity;
/**
 * 设备状态
 * @author zyf
 * @date 2018-10-31
 */
@Pojo(name="设备状态")
@JsonInclude(Include.NON_NULL)
public class Equipmentstatus extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private String equipmentStatusId=ObjectUtils.getUUID();
	/** 状态编号 */ 
	private String equipmentStatusCode;
	/** 状态名称 */ 
	private String equipmentStatusName;
	/** 状态英文名称 */ 
	private String equipmentStatusEnglishName;
	/** 排序 */ 
	private Integer Indexs;

	public Equipmentstatus(){
	}
	public Equipmentstatus(String equipmentStatusId){
	    this.equipmentStatusId=equipmentStatusId;
	}
	
	public String getEquipmentStatusId() {
		return equipmentStatusId;
	}
	public Equipmentstatus setEquipmentStatusId(String equipmentStatusId) {
		this.equipmentStatusId = equipmentStatusId;
		return this;
	}
	
	public String getEquipmentStatusCode() {
		return equipmentStatusCode;
	}
	public Equipmentstatus setEquipmentStatusCode(String equipmentStatusCode) {
		this.equipmentStatusCode = equipmentStatusCode;
		return this;
	}
	
	public String getEquipmentStatusName() {
		return equipmentStatusName;
	}
	public Equipmentstatus setEquipmentStatusName(String equipmentStatusName) {
		this.equipmentStatusName = equipmentStatusName;
		return this;
	}
	
	public String getEquipmentStatusEnglishName() {
		return equipmentStatusEnglishName;
	}
	public void setEquipmentStatusEnglishName(String equipmentStatusEnglishName) {
		this.equipmentStatusEnglishName = equipmentStatusEnglishName;
	}
	public Integer getIndexs() {
		return Indexs;
	}
	public Equipmentstatus setIndexs(Integer Indexs) {
		this.Indexs = Indexs;
		return this;
	}
}
