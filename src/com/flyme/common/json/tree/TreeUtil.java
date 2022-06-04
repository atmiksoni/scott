package com.flyme.common.json.tree;

import java.util.ArrayList;
import java.util.List;

import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.conver.ConvertUtils;
import com.flyme.common.util.reflect.ReflectHelper;

/**
 * @description:(树形结构数据构建工具类)
 * @author：flyme
 * @data：2013-7-8 上午09:27:18
 * @version：1.0
 */
public class TreeUtil {
	public static final String ID = "id";
	public static final String PARENTID = "parentId";
	public static final String NAME = "name";
	public static final String CODE = "code";
	public static final String CHILD = "child";
	public static final String POJONAME = "pojoName";
	public static final String STATE = "state";

	/**
	 * 构建ComboTree
	 * 
	 * @param list树模型集合
	 * @param modelParm树模型对象
	 * @param ownSet已拥有节点
	 */
	public static List<TreeModel> getTreeList(List<?> list, TreeSetting treeSetting, List<?> ownSet) {
		List<TreeModel> trees = new ArrayList<TreeModel>();
		for (Object obj : list) {
			trees.add(comboTree(obj, treeSetting, ownSet));
		}
		return trees;
	}

	/**
	 * 构建ComboTree
	 * 
	 * @param list树模型集合
	 * @param modelParm树模型对象
	 */
	public static List<TreeModel> getTreeList(List<?> list, TreeSetting treeSetting) {
		List<TreeModel> trees = new ArrayList<TreeModel>();
		for (Object obj : list) {
			trees.add(comboTree(obj, treeSetting, null));
		}
		return trees;
	}

	/**
	 * 递归树模型
	 */
	private static TreeModel comboTree(Object obj, TreeSetting treeSetting, List<?> ownList) {
		TreeModel tree = new TreeModel();
		ReflectHelper reflectHelper = new ReflectHelper(obj);
		String id = ConvertUtils.getString(reflectHelper.getMethodValue(ID));
		String pId = ConvertUtils.getString(reflectHelper.getMethodValue(PARENTID, ""));
		String code = ConvertUtils.getString(reflectHelper.getMethodValue(CODE, ""));
		String pojoName = ConvertUtils.getString(reflectHelper.getMethodValue(POJONAME, ""));
		String name = ConvertUtils.getString(reflectHelper.getMethodValue(NAME, ""));
		tree.setId(id);
		tree.setParentId(pId);
		tree.setCode(code);
		tree.setPojoName(pojoName);
		tree.setName(name);
		tree.setState(treeSetting.getState());
		if (ObjectUtils.isNotEmpty(treeSetting.getUrl())) {
			String url = treeSetting.getUrl();
			if (url.indexOf("?") > -1) {
				tree.setUrl(treeSetting.getUrl() + "&nodeId=" + id);
			} else {
				tree.setUrl(treeSetting.getUrl() + "?nodeId=" + id);
			}

		}
		if (ObjectUtils.isNotEmpty(treeSetting.getTarget())) {
			tree.setTarget(treeSetting.getTarget());
		}
		List<TreeModel> children = new ArrayList<TreeModel>();
		if (treeSetting.getHasChild()) {
			List<?> childList = null;
			if (reflectHelper.getMethodValue(CHILD) != null) {
				childList = (ArrayList<?>) reflectHelper.getMethodValue(CHILD);
				if (!ObjectUtils.isEmpty(childList)) {
					tree.setIsParent(true);
					tree.setOpen(false);
					for (Object object : childList) {
						TreeModel treeModel = comboTree(object, treeSetting, ownList);
						children.add(treeModel);
					}
					tree.setChildren(children);// 子节点
				} else {
					tree.setIsParent(false);
				}
			}
		}
		if (!ObjectUtils.isEmpty(ownList)) {
			for (Object object : ownList) {
				ReflectHelper oReflectHelper = new ReflectHelper(object);
				String oId = ConvertUtils.getString(oReflectHelper.getMethodValue(ID));
				if (id.equals(oId)) {
					tree.setChecked(true);
				}
			}
		}
		return tree;
	}
}
