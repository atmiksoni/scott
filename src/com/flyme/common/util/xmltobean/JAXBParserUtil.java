package com.flyme.common.util.xmltobean;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;


/**
 * JAXB解析常用类
 * 
 * @ClassName JAXBParserUtil
 * @Description TODO(描述类作用)
 * @author Administrator
 * @data 2016年4月8日 下午3:31:43
 *
 */
public class JAXBParserUtil {
	/**
	 * 把对象序列化为XML
	 * 
	 * @Title serializaToXml
	 * @param obj
	 *            实例化对象
	 * @param clazz
	 *            对象Class
	 * @return
	 * @throws JAXBException
	 *             String
	 */
	public static String serializaToXml(Object obj, Class<?>[] clazz) throws JAXBException {
		return serializaToXml(obj, "UTF-8", clazz);
	}

	/**
	 * 把对象序列化为XML
	 * 
	 * @Title serializaToXml
	 * @param obj
	 *            实例化对象
	 * @param encode
	 *            XML字符串编码
	 * @param clazz
	 *            对象Class
	 * @return
	 * @throws JAXBException
	 *             String
	 */
	public static String serializaToXml(Object obj, String encode, Class<?>[] clazz) throws JAXBException {
		Set classes = new HashSet();
		if ((clazz != null) && (clazz.length > 0)) {

			for (int i = 0; i < clazz.length; i++) {
				classes.add(clazz[i]);
			}
		} else {
			extractClasses(obj, classes);
		}
		JAXBContext context = JAXBContext.newInstance((Class[]) classes.toArray(new Class[classes.size()]));
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty("jaxb.encoding", encode);
		marshaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
		marshaller.setProperty("jaxb.fragment", Boolean.valueOf(false));
		StringWriter sw = new StringWriter();
		marshaller.marshal(obj, sw);
		return sw.toString();
	}

	/**
	 * 反序列化XML到对象
	 * 
	 * @Title unserializeFromXml
	 * @param reader
	 *            文件读取类
	 * @param clazz
	 *            实例化对象类
	 * @return 实例化对象类
	 * @throws JAXBException
	 */
	public static Object unserializeFromXml(Reader reader, Class<?>[] clazz) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Object obj = unmarshaller.unmarshal(reader);
		return obj;
	}

	/**
	 * 反序列化XML到对象
	 * 
	 * @Title unserializeFromXml
	 * @param xml
	 *            xml文件
	 * @param clazz
	 *            实例化对象
	 * @return 实例化对象
	 * @throws JAXBException
	 */
	public static Object unserializeFromXml(String xml, Class<?>[] clazz) throws JAXBException {

		StringReader sr = new StringReader(xml);
		Object obj = unserializeFromXml(sr, clazz);
		return obj;
	}

	private static void extractClasses(Object obj, Set<Class<?>> classes) {
		Class clazz = obj.getClass();
		if (clazz.isInterface()) {
			return;
		}
		XmlAccessorType annotation = (XmlAccessorType) clazz.getAnnotation(XmlAccessorType.class);
		if (annotation != null) {

			XmlAccessType value = annotation.value();
			if (XmlAccessType.PUBLIC_MEMBER.equals(value) || XmlAccessType.PROPERTY.equals(value)) {

				Method[] declareMethods = clazz.getDeclaredMethods();
				for (Method method : declareMethods) {
					if (Modifier.isPublic(method.getModifiers())) {

						Class[] parameterType = method.getParameterTypes();
						if (parameterType.length != 0)
							continue;
						try {
							Object methodValue = method.invoke(obj, new Object[0]);
							if (methodValue != null) {
								extractCollectionClasses(methodValue, classes);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} else if (XmlAccessType.FIELD.equals(value)) {
				Field[] declaredFields = clazz.getDeclaredFields();
				for (Field field : declaredFields) {
					field.setAccessible(true);
					try {
						Object propertyValue = field.get(obj);
						if (propertyValue != null) {
							extractCollectionClasses(propertyValue, classes);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		extractCollectionClasses(obj, classes);
	}

	private static void extractCollectionClasses(Object obj, Set<Class<?>> classes) {

		try {
			if (obj instanceof Collection) {

				for (Iterator localIterator = ((Collection) obj).iterator(); localIterator.hasNext();) {
					Object item = localIterator.next();
					extractClasses(obj, classes);
				}
			} else if (obj instanceof Map) {

				Iterator keys = ((Map) obj).keySet().iterator();
				while (keys.hasNext()) {
					Object key = keys.next();
					Object value = ((Map) obj).get(key);
					extractClasses(key, classes);
					extractClasses(value, classes);
				}
			} else {

				Class class1 = obj.getClass();
				if (class1.getName().contains("$$EnhancerByCGLIB$$")) {
					classes.add(class1.getSuperclass());
				} else if (!class1.isInterface())
					classes.add(class1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static class MyNamespacePrefixMapper extends NamespacePrefixMapper {
		@Override
		public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
			if (requirePrefix) {
				if ("http://test.me/persons".equals(namespaceUri)) {
					return "me";
				}
				return suggestion;
			} else {
				return "";
			}
		}
	}

}
