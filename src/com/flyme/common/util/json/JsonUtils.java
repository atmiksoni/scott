package com.flyme.common.util.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

/**
 * @description：TODO(JSON字符串工具类) @author：flyme @data：2013-8-23
 *                               上午11:41:49 @version：v 1.0
 */
public class JsonUtils {

	/***
	 * 将对象序列化为JSON文本
	 * 
	 * @param object
	 * @return
	 */
	public static String objectToJson(Object object) {
		JSONArray jsonArray = toJSONArray(object);
		return jsonArray.toString();
	}

	/**
	 * 将对象序列化为JSON文本
	 * 
	 * @param <T>
	 *            t
	 * @return
	 */
	public static <T> String tToJson(T t) {
		JSONObject jsonObject = toJSONObject(t);
		return jsonObject.toString();
	}

	@SuppressWarnings("unused")
	public static Object xmlToBean(String xx, Class<?> class1) {
		JSON json = null;
		InputStream is;
		try {
			String xml = xx;
			XMLSerializer xmlSerializer = new XMLSerializer();
			json = xmlSerializer.read(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toBean(json.toString(), class1);

	}

	/***
	 * 将对象转换为Collection对象
	 * 
	 * @param object
	 * @return
	 */
	public static Collection<?> toCollection(Object object) {
		JSONArray jsonArray = toJSONArray(object);
		return JSONArray.toCollection(jsonArray);
	}

	/***
	 * 将对象转换为JSON对象数组
	 * 
	 * @param object
	 * @return
	 */
	public static JSONArray toJSONArray(Object object) {
		return JSONArray.fromObject(object);
	}

	/***
	 * 将对象转换为JSON对象
	 * 
	 * @param object
	 * @return
	 */
	public static JSONObject toJSONObject(Object object) {
		return JSONObject.fromObject(object);
	}

	/***
	 * 将对象转换为JSON对象
	 * 
	 * @param object
	 * @return
	 */
	public static JSONObject getJsonObject() {
		return new JSONObject();
	}

	/***
	 * 输出JSONObject
	 * 
	 * @param object
	 * @return
	 */
	public static void print(JSONObject root, HttpServletResponse response) {
		try {
			PrintWriter out = response.getWriter();
			out.print(root);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * 将对象转换为传入类型的List
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static <T> List<T> toList(Object object, Class<T> objectClass) {
		return JSONArray.toList(toJSONArray(object), objectClass);
	}

	/***
	 * 将JSON对象数组转换为传入类型的List
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static <T> List<T> toList(JSONArray jsonArray, Class<T> objectClass) {
		return JSONArray.toList(jsonArray, objectClass);
	}

	/***
	 * 将对象转换为List对象
	 */
	public static List<Object> toArrayList(Object object) {
		List<Object> arrayList = new ArrayList<Object>();
		Iterator<?> it = toJSONArray(object).iterator();
		while (it.hasNext()) {
			JSONObject jsonObject = (JSONObject) it.next();
			Iterator<?> keys = jsonObject.keys();
			while (keys.hasNext()) {
				Object key = keys.next();
				Object value = jsonObject.get(key);
				arrayList.add(value);
			}
		}
		return arrayList;
	}

	/***
	 * 将对象转换为HashMap
	 */
	public static HashMap<String, Object> toHashMap(Object object) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		JSONObject jsonObject = toJSONObject(object);
		Iterator<?> it = jsonObject.keys();
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			Object value = jsonObject.get(key);
			data.put(key, value);
		}

		return data;
	}

	/***
	 * 将对象转换为List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> toList(Object object) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray jsonArray = toJSONArray(object);
		for (Object obj : jsonArray) {
			JSONObject jsonObject = (JSONObject) obj;
			Map<String, Object> map = new HashMap<String, Object>();
			Iterator<?> it = jsonObject.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object value = jsonObject.get(key);
				map.put((String) key, value);
			}
			list.add(map);
		}
		return list;
	}

	/***
	 * 将将对象转换为传入类型的对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(String jsonString, Class<T> beanClass, Map<Object, Object> map) {
		JSONObject jsonObject = toJSONObject(jsonString);
		return (T) JSONObject.toBean(jsonObject, beanClass, map);
	}

	/***
	 * 将对象转换为传入类型的对象组
	 */

	@SuppressWarnings("unchecked")
	public static <T> List<T> toList(String jsonString, Class<T> beanClass, Map<String, Object> map) {
		List<T> list = new ArrayList<T>();
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Object object;
		for (Object object2 : jsonArray) {
			object = (T) JSONObject.toBean((JSONObject) object2, beanClass, map);
			list.add((T) object);
		}
		return list;
	}

	/***
	 * 将将对象转换为传入类型的对象
	 * 
	 * @param <T>
	 * @param object
	 * @param beanClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toBean(Object object, Class<T> beanClass) {
		JSONObject jsonObject = toJSONObject(object);
		return (T) JSONObject.toBean(jsonObject, beanClass);
	}

	/**
	 * 本方法封装了往前台设置的header,contentType等信息
	 */
	public static void writeToWeb(String message, String type, HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/" + type + "; charset=utf-8");
		try {
			response.getWriter().write(message);
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
