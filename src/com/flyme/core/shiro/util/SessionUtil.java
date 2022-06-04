package com.flyme.core.shiro.util;
import com.flyme.common.util.ObjectUtils;

public class SessionUtil {
	
	/**
	 * 设置session
	 */
	public static void set(String key, Object obj) {
		BaseShiroUtils.getSession().setAttribute(key, obj);
	}
	
	/**
	 * 读取session
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String key) {
		Object object = BaseShiroUtils.getSession().getAttribute(key);
		return (T) object;
	}
	
	/**
	 * 读取session
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(String key, Object def) {
		Object object = BaseShiroUtils.getSession().getAttribute(key);
		if (ObjectUtils.isEmpty(object)) {
			object = def;
		}
		return (T) object;
	}
}
