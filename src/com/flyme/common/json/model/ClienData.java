package com.flyme.common.json.model;

import java.io.Serializable;
import java.util.List;

import com.flyme.base.rbac.resource.vo.ResourceModel;

public class ClienData implements Serializable {

	/**
	 * 客户端数据
	 */

	private static final long serialVersionUID = 1L;
	private List<ResourceModel> resources;// 权限集合
	private Boolean flag=false;

	public List<ResourceModel> getResources() {
		return resources;
	}

	public void setResources(List<ResourceModel> resources) {
		this.resources = resources;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	

}
