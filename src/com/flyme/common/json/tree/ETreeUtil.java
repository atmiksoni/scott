package com.flyme.common.json.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.flyme.common.util.conver.ConvertUtils;
import com.flyme.common.util.reflect.ReflectHelper;

/**
 * @description:(树形结构数据构建工具类)
 * @author：flyme
 * @data：2013-7-8 上午09:27:18
 * @version：1.0
 */
public class ETreeUtil {
	public static final String ID = "resourceId";
	public static final String TEXT = "resName";
	public static final String PARENTID = "parentId";

	// 构建ComboTree
	public static List<ETreeModel> getResultList(List<?> list, List<?> hasList) {
		// 返回对象
		List<ETreeModel> parentTreeList = new ArrayList<ETreeModel>();
		for (Object object : list) {
			ETreeModel comboTree = comboTree(object, hasList);
			if (comboTree.getParentId().equals("")) {
				ETreeModel ccomboTree = comboTree;
				ccomboTree.setChildren(new ArrayList<ETreeModel>());
				parentTreeList.add(ccomboTree);
			}
		}
		List<ETreeModel> last = new ArrayList<ETreeModel>();
		for (Object object : list) {
			ETreeModel comboTree = comboTree(object, hasList);
			if (!comboTree.getParentId().equals("")) {
				last.add(comboTree);
			}
		}
		buildTree(parentTreeList, last);
		return parentTreeList;
	}

	/**
	 * 构建tree单个对象
	 * 
	 * @param object
	 * @return
	 */
	public static ETreeModel comboTree(Object object, List<?> hasList) {
		ETreeModel tree = new ETreeModel();
		ReflectHelper reflectHelper = new ReflectHelper(object);
		String id = ConvertUtils.getString(reflectHelper.getMethodValue(ID));
		String text = ConvertUtils.getString(reflectHelper.getMethodValue(TEXT, ""));
		String parentId = ConvertUtils.getString(reflectHelper.getMethodValue(PARENTID, ""));
		tree.setId(id);
		tree.setText(text);
		tree.setParentId(parentId);
		for (Object has : hasList) {
			ReflectHelper hasHelper = new ReflectHelper(has);
			String resourceId = ConvertUtils.getString(hasHelper.getMethodValue("resourceId", ""));
			if (!resourceId.equals("") && id.equals(resourceId)) {
				tree.setChecked(true);
			}
		}
		return tree;
	}

	/**
	 * 递归 构建 tree
	 * 
	 * @param parents
	 * @param others
	 */
	public static void buildTree(List<ETreeModel> parents, List<ETreeModel> others) {
		List<ETreeModel> record = new ArrayList<ETreeModel>();
		for (Iterator<ETreeModel> it = parents.iterator(); it.hasNext();) {
			ETreeModel vi = it.next();
			if (vi.getId() != null) {
				for (Iterator<ETreeModel> otherIt = others.iterator(); otherIt.hasNext();) {
					ETreeModel inVi = otherIt.next();
					if (vi.getId().equals(inVi.getParentId())) {
						if (null == vi.getChildren()) {
							vi.setChildren(new ArrayList<ETreeModel>());
						}
						vi.getChildren().add(inVi);
						record.add(inVi);
						otherIt.remove();
					}
				}
			}
		}
		if (record == null || record.size() == 0) {
			return;
		} else {
			buildTree(record, others);
		}
	}
}
