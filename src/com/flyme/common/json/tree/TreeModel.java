package com.flyme.common.json.tree;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @description:(Ztree数据模型)
 * @author：flyme
 * @data：2013-7-8 上午09:27:18 @version：1.0
 */
@JsonInclude(Include.NON_NULL)
public class TreeModel {
	private String id; // 编号
	private String parentId; // 父节点
	private String code;// 节点编码
	private Boolean isParent = false; // 是否父节点
	private String name; // 节点名称
	private String state = "open";
	private Boolean open = false; // 是否打开
	private Boolean doCheck = true;// 是否 禁止选中
	private Boolean checked = false;// 默认是否选中
	private boolean nocheck = false;
	private String url;// 节点编辑的URL
	private String target;// 设置点击节点后在何处打开 url。[treeNode.url 存在时有效]
	private List<TreeModel> children;// 子节点
	private String pojoName;
	private Map<String, Object> otherParam;

	public Map<String, Object> getOtherParam() {
		return otherParam;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public String getUrl() {
		return url;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public void setOtherParam(Map<String, Object> otherParam) {
		this.otherParam = otherParam;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getDoCheck() {
		return doCheck;
	}

	public void setDoCheck(Boolean doCheck) {
		this.doCheck = doCheck;
	}

	public List<TreeModel> getChildren() {
		return children;
	}

	public void setChildren(List<TreeModel> children) {
		this.children = children;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPojoName() {
		return pojoName;
	}

	public void setPojoName(String pojoName) {
		this.pojoName = pojoName;
	}
}
