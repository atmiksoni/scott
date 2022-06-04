package com.flyme.common.util.reflect;

import java.lang.reflect.Field;

/**
 * 反射工具
 * @author zyf 
 */
public class ReflectUtils {
	/**
	 * 获取obj对象fieldName的Field
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName) {
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
		return null;
	}

	/**
	 * 获取obj对象fieldName的属性值
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Object getValueByFieldName(Object obj, String fieldName) {
		Field field = getFieldByFieldName(obj, fieldName);
		Object value = null;
		if (field != null) {
			try {
				if (field.isAccessible()) {  
	                value = field.get(obj);  
	            } else {  
	                field.setAccessible(true);  
	                value = field.get(obj);  
	                field.setAccessible(false);  
	            }  
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	/**
	 * 设置obj对象fieldName的属性值
	 * 
	 * @param obj
	 * @param fieldName
	 * @param value
	 */
	public static void setValueByFieldName(Object obj, String fieldName,
			Object value) {
		Field field;
		try {
			field = obj.getClass().getDeclaredField(fieldName);
			 if (field.isAccessible()) {  
		            field.set(obj, value);  
		        } else {  
		            field.setAccessible(true);  
		            field.set(obj, value);  
		            field.setAccessible(false);  
		        }  
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			e1.printStackTrace();
		}
	}
}