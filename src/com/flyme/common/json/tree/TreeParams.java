package com.flyme.common.json.tree;

/**
 * ztree传递到后台参数
 */
public class TreeParams {
	private String id;// 节点Id
	private String pojoName = "DEFAULT";// 实体名称
	private String name;// 节点名称
	private String code;// 节点代码
	private String parentId;// 父节点ID
	private Boolean virtual = true;// 是否显示非业主
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPojoName() {
		return pojoName;
	}
	
	public void setPojoName(String pojoName) {
		this.pojoName = pojoName;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getParentId() {
		return parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public Boolean getVirtual() {
		return virtual;
	}
	
	public void setVirtual(Boolean virtual) {
		this.virtual = virtual;
	}
	
}