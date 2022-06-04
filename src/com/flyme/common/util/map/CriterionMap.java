package com.flyme.common.util.map;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flyme.common.util.ObjectUtils;
import com.flyme.core.mybatis.filters.SearchUtil;

/**
 * 查询参数MAP
 */
@JsonIgnoreProperties(value = { "key" })
public class CriterionMap extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;
	public String key = "map";

	public CriterionMap() {
		this.key = getKey();
	}

	public CriterionMap(Boolean tag) {
		if (tag) {
			this.put("msgcode", 100);
			this.put("success", true);
			this.add("msg", "");
			this.key = getKey();
		}
	}

	public CriterionMap(Boolean tag, String... strings) {
		for (String str : strings) {
			super.put(str, "");
		}
		if (tag) {
			this.put("msgcode", 100);
			this.put("success", true);
			this.add("msg", "");
			this.key = getKey();
		}
	}

	public CriterionMap(String... strings) {
		for (String str : strings) {
			super.put(str, "");
		}
	}

	public CriterionMap(String field) {
		if (ObjectUtils.isNotEmpty(field)) {
			String[] fields = field.split(",");
			for (String string : fields) {
				string = ObjectUtils.substring(string, ".");
				super.put(string, "");
			}
		}
	}

	public Object put(String key, Object value) {
		if (ObjectUtils.isNotEmpty(value)) {
			super.put(SearchUtil.getField(key), value);
		} else {
			super.put(SearchUtil.getField(key), "");
		}
		return value;
	}

	public Object putObj(String key, Object value) {
		if (ObjectUtils.isNotEmpty(value)) {
			super.put(SearchUtil.getField(key), value);
		} else {
			super.put(SearchUtil.getField(key), new Object());
		}
		return value;
	}

	public Object putArray(String key, Collection<?> value) {
		if (ObjectUtils.isNotEmpty(value)) {
			super.put(SearchUtil.getField(key), value);
		} else {
			super.put(SearchUtil.getField(key), ObjectUtils.getArrayList());
		}
		return value;
	}

	public CriterionMap add(String key, Object value) {
		super.put(SearchUtil.getField(key), value);
		return this;
	}

	@SuppressWarnings("unchecked")
	public <T> T setParam(String paraName) {
		T t = null;
		Object obj = ObjectUtils.getParameter(paraName);
		if (ObjectUtils.isNotEmpty(obj)) {
			this.put(paraName, obj);
			t = (T) obj;
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public <T> T setParam(String paramName, String key) {
		T t = null;
		Object obj = ObjectUtils.getParameter(paramName);
		if (ObjectUtils.isNotEmpty(obj)) {
			this.put(key, obj);
			t = (T) obj;
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		T t = null;
		Object obj = super.get(key);
		if (ObjectUtils.isNotEmpty(obj)) {
			t = (T) obj;
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key, T def) {
		T t = null;
		Object obj = super.get(key);
		if (ObjectUtils.isNotEmpty(obj)) {
			t = (T) obj;
		} else {
			t = def;
		}
		return t;
	}

	public String getString(String key) {
		Object object = super.get(SearchUtil.getField(key));
		if (ObjectUtils.isNotEmpty(object)) {
			return object.toString();
		} else {
			return null;
		}
	}

	// 不为空
	public Object notNull(String field) {
		return super.put(SearchUtil.getField(field), "notNull");
	}

	// group
	public Object group(String sql) {
		return super.put("group", "group by " + sql);
	}

	public void clear() {
		super.clear();
	}

	public String getKey() {
		StringBuffer url = new StringBuffer();
		if (this.keySet().size() > 0) {
			for (Map.Entry<String, Object> entry : this.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				url.append(key + "=" + encodeUrl(value));
				url.append("&");
			}
		}
		this.key = url.toString();
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String encodeUrl(Object value) {
		String result = value.toString();
		try {
			byte temp[] = result.getBytes("UTF-8");
			result = new String(temp, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/* 合并MAP */
	public void putMap(CriterionMap map) {
		if (ObjectUtils.isNotEmpty(map)) {
			this.putAll(map);
		}
	}

}
