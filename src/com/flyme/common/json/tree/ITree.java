package com.flyme.common.json.tree;

import com.fasterxml.jackson.annotation.JsonView;
import com.flyme.common.json.view.TreeView;
import com.flyme.common.util.ObjectUtils;

/**
 * 实现TREE树形结构的接口(如果一个类要实现树形结构必须实现该接口)
 */
public interface ITree {
	public static final String PARENTID = "1";// 默认父ID

	public abstract String getId();

	public abstract String getParentId(); // 父ID

	public abstract String getName();// 节点名称
	//public abstract String Englishname();// 节点名称

	public abstract String getCode();// 节点编码

	public abstract String getPojoName();// 节点实体名称
	@JsonView(TreeView.class)
	default String getState() {
		return ObjectUtils.isNotEmpty(getParentId()) ? "closed" : "open";
	}
	default int getChildSize() {
		return 1;
	}

}
