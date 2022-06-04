package com.flyme.common.json.pqgrid;

import java.util.ArrayList;
import java.util.List;

import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.common.util.reflect.ReflectHelper;
import com.flyme.core.springmvc.designpattern.proxy.Proxy;

public class DataGridUtil {
	public static List<CriterionMap> listtojson(CriterionMap fieldMaps, List<?> list) {

		List<CriterionMap> datas = new ArrayList<>();
		Object[] values = new Object[fieldMaps.size()];
		for (int j = 0; j < list.size(); j++) {
			CriterionMap map = new CriterionMap();
			int i = 0;
			for (CriterionMap.Entry<String, Object> entry : fieldMaps.entrySet()) {
				String fieldName = entry.getKey().toString();
				values[i] = fieldNametoValues(fieldName, list.get(j));
				if (ObjectUtils.isNotEmpty(values[i])) {
					map.put(fieldName, values[i]);
				}
				i++;
			}
			datas.add(map);
		}
		return datas;
	}

	public static <T> List<CriterionMap> listtojson(CriterionMap fieldMaps, List<T> rows, Proxy proxy) {

		List<CriterionMap> datas = new ArrayList<>();
		Object[] values = new Object[fieldMaps.size()];
		for (int j = 0; j < rows.size(); j++) {
			CriterionMap map = new CriterionMap();
			int i = 0;
			for (CriterionMap.Entry<String, Object> entry : fieldMaps.entrySet()) {
				String fieldName = entry.getKey().toString();
				String jsonName = entry.getValue().toString();
				values[i] = fieldNametoValues(fieldName, rows.get(j));
				if (ObjectUtils.isNotEmpty(values[i])) {
					map.put(fieldName, values[i]);
				}
				if (ObjectUtils.isNotEmpty(proxy)) {
					proxy.operate(map, jsonName, values[i], proxy.getParams());
				}
				i++;
			}
			datas.add(map);
		}
		return datas;
	}

	public static List<CriterionMap> listToMap(CriterionMap fieldMaps, List<?> list, Proxy proxy) {
		List<CriterionMap> datas = new ArrayList<>();
		Object[] values = new Object[fieldMaps.size()];
		for (int j = 0; j < list.size(); j++) {
			CriterionMap map = new CriterionMap();
			int i = 0;
			for (CriterionMap.Entry<String, Object> entry : fieldMaps.entrySet()) {
				String fieldName = entry.getKey().toString();
				String jsonName = entry.getValue().toString();
				int beginIndex = fieldName.indexOf(".");
				if (beginIndex != -1) {
					fieldName = fieldName.substring(beginIndex + 1);
				}
				map.put(jsonName, fieldNametoValues(fieldName, list.get(j)));
				if (ObjectUtils.isNotEmpty(proxy)) {
					proxy.operate(map, jsonName, values[i], proxy.getParams());
				}
				i++;
			}
			datas.add(map);
		}
		return datas;
	}

	/**
	 * 获取对象内对应字段的值
	 * 
	 * @param fields
	 */
	public static Object fieldNametoValues(String FiledName, Object o) {
		Object value = "";
		String fieldName = "";
		String childFieldName = null;
		ReflectHelper reflectHelper = new ReflectHelper(o);
		if (FiledName.indexOf("_") == -1) {
			fieldName = FiledName;
		} else {
			fieldName = FiledName.substring(0, FiledName.indexOf("_"));// 外键字段引用名
			childFieldName = FiledName.substring(FiledName.indexOf("_") + 1);// 外键字段名
		}
		value = reflectHelper.getMethodValue(fieldName) == null ? "" : reflectHelper.getMethodValue(fieldName);
		if (value != "" && value != null && FiledName.indexOf("_") != -1) {
			value = fieldNametoValues(childFieldName, value);
		}
		return value;
	}
}
