package com.flyme.common.json.model;

import java.util.List;

/**
 * @description：TODO(权限分配模型)
 * @author：flyme
 * @data：2013-8-23 上午09:34:06
 * @version：v 1.0
 */
public class Permission {
	private String menuId;
	private List<Operation> operations;

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
