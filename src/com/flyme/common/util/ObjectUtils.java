package com.flyme.common.util;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.ui.Model;

import com.flyme.base.rbac.resource.pojo.Resource;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.context.ContextUtils;
import com.flyme.common.util.conver.ConvertUtils;
import com.flyme.common.util.date.DateUtil;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.core.jdbc.config.SetupConfig;

/**
 * 对象校验工具类
 */
public class ObjectUtils {

	/**
	 * 检验对象是否为空
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null || obj.equals("undefined")) {
			return true;
		} else if (obj instanceof String && (obj.equals(""))) {
			return true;
		} else if (obj instanceof Collection && ((Collection<?>) obj).isEmpty() && ((Collection<?>) obj).size() == 0) {
			return true;
		} else if (obj instanceof Map && ((Map<?, ?>) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
			return true;
		}
		return false;

	}

	public static int getSize(Object obj) {
		int size = 0;
		if (obj instanceof Collection) {
			size = ((Collection<?>) obj).size();
		} else if (obj instanceof Map) {
			size = ((Map<?, ?>) obj).size();
		} else if (obj instanceof Object[]) {
			size = ((Object[]) obj).length;
		}
		return size;
	}

	/**
	 * 检验对象是否为空
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 * 检验对象是否为undefined 或者null
	 */
	public static boolean isNullOrUndefined(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String && (obj.equals("undefined"))) {
			return true;
		} else {
			return false;
		}
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (ObjectUtils.isNotEmpty(str)) {
			if (str != null) {
				Pattern p = Pattern.compile("\t|\r|\n");
				Matcher m = p.matcher(str);
				dest = m.replaceAll("");
			}
		}
		return dest;
	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static Integer getIntParameter(String param) {
		String str = ContextUtils.getRequest().getParameter(param);
		return ConvertUtils.getInteger(str);
	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static Integer getIntParameter(HttpServletRequest request, String param) {
		String str = request.getParameter(param);
		return ConvertUtils.getInteger(str);
	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static Integer getIntParameter(HttpServletRequest request, String param, Integer def) {
		String str = request.getParameter(param);
		if (ObjectUtils.isEmpty(str)) {
			return def;
		} else {
			return ConvertUtils.getInteger(str);
		}
	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static Integer getIntParameter(String param, int def) {
		String str = ContextUtils.getRequest().getParameter(param);
		if (ObjectUtils.isEmpty(str)) {
			return def;
		} else {
			return ConvertUtils.getInteger(str);
		}

	}

	public static Boolean isChinese(Object object) {
		Boolean tag = true;
		if (ObjectUtils.isNotEmpty(object)) {
			char[] ch = object.toString().toCharArray();
			for (char c : ch) {
				if (!check(c)) {
					tag = false;
				}
			}
		} else {
			tag = false;
		}
		return tag;
	}

	public static boolean check(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static boolean isPhoneNumber(Object phoneNumber) {

		boolean isValid = false;
		if (ObjectUtils.isNotEmpty(phoneNumber)) {
			String expression = "((^(1)[3-9][0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
			CharSequence inputStr = phoneNumber.toString();

			Pattern pattern = Pattern.compile(expression);

			Matcher matcher = pattern.matcher(inputStr);

			if (matcher.matches()) {
				isValid = true;
			}
		}
		return isValid;
	}

	public static boolean isEmail(String email) {
		Boolean tag = false;
		if (isNotEmpty(email)) {
			String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
			Pattern p = Pattern.compile(str);
			Matcher m = p.matcher(email);

			tag = m.matches();
		}
		return tag;
	}

	public static boolean isLetter(Object s) {
		Boolean tag = false;
		if (ObjectUtils.isNotEmpty(s)) {
			String matcher = "^[a-zA-Z]*";
			Pattern p = Pattern.compile(matcher);
			Matcher m = p.matcher(s.toString());
			tag = m.matches();
		}
		return tag;

	}

	public static boolean isMobile(String s) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(s);
		return m.matches();
	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static BigDecimal getDecimalParameter(String param) {
		BigDecimal bigDecimal = new BigDecimal("0.00");
		String str = ContextUtils.getRequest().getParameter(param);
		if (ObjectUtils.isNotEmpty(str)) {
			bigDecimal = new BigDecimal(str);
		}
		return bigDecimal;
	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static Double getDoubleParameter(String param) {
		String str = ContextUtils.getRequest().getParameter(param);
		return ConvertUtils.getDouble(str);
	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static String getParameter(String param, String defval) {
		String str = ContextUtils.getRequest().getParameter(param);
		if (ObjectUtils.isEmpty(str)) {
			return defval;
		}
		return str.toString();
	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static Short getParameter(String param, Short defval) {
		String str = ContextUtils.getRequest().getParameter(param);
		if (ObjectUtils.isEmpty(str)) {
			return defval;
		}
		return ConvertUtils.getShort(str);
	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static String getParameter(String param) {
		String str = ContextUtils.getRequest().getParameter(param);
		if (ObjectUtils.isEmpty(str)) {
			return "";
		}
		return str.toString().trim();
	}

	/** 解码 */
	public static String URLDecoder(String str) {
		try {
			if (ObjectUtils.isNotEmpty(str)) {
				str = URLDecoder.decode(str, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/** 编码 */
	public static String URLEncode(String str) {
		try {
			if (ObjectUtils.isNotEmpty(str)) {
				str = URLEncoder.encode(str, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	@SuppressWarnings("deprecation")
	public static String getURLDecoderParam(String str) {
		str = getUTF8Parameter(str);
		if (ObjectUtils.isNotEmpty(str)) {
			str = URLDecoder.decode(str);
		}
		return str;
	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static String getUTF8Parameter(String param) {
		String string = "";
		String str = ContextUtils.getRequest().getParameter(param);
		if (ObjectUtils.isNotEmpty(str)) {
			try {
				string = new String(str.getBytes("ISO8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return string;
	}

	public static CriterionMap getInputStreamMap() {
		// 获取post参数
		StringBuffer sb = new StringBuffer();
		InputStream is;
		CriterionMap map = new CriterionMap();
		try {
			is = ContextUtils.getRequest().getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) {
				sb.append(s);
				System.out.println(s);
			}
			if (ObjectUtils.isNotEmpty(sb)) {
				map = getUrlParams(sb.toString());

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return map;
	}

	public static CriterionMap getInputStreamMap2() {
		// 获取post参数
		StringBuffer sb = new StringBuffer();
		InputStream is;
		CriterionMap map = new CriterionMap();
		try {
			is = ContextUtils.getRequest().getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			if (ObjectUtils.isNotEmpty(sb)) {
				map = JsonUtil.jsonToMap(sb.toString(), false);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return map;
	}

	public static CriterionMap getUrlParams(String param) {
		CriterionMap map = new CriterionMap();
		if (ObjectUtils.isEmpty(param)) {
			return map;
		}
		String[] params = param.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=");
			if (p.length == 2) {
				map.put(p[0], p[1]);
			}
		}
		return map;
	}

	/**
	 * 返回字符串如为空返回默认值
	 * 
	 * @param param
	 * @return
	 */
	public static String getHeaders(String param) {
		String str = ContextUtils.getRequest().getHeader(param);
		if (ObjectUtils.isEmpty(str)) {
			return "";
		}
		return str.toString();
	}

	/**
	 * 根据值判断输入类型
	 */
	public static String getValueType(HttpServletRequest request, String def) {
		String name = ObjectUtils.getParameter(request, "name");
		Object object = ObjectUtils.getParameter(request, "value");
		String type = def;
		if (name.equals("all")) {
			if (isChinese(object)) {
				type = "ch";
			}
			if (isPhoneNumber(object)) {
				type = "num";
			}
			if (isLetter(object)) {
				type = "en";
			}
		} else {
			return name;
		}
		return type;

	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static String[] getParameters(String param) {
		String[] str = ContextUtils.getRequest().getParameterValues(param);
		return str;
	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static Object getAttribute(String param) {
		HttpSession session = ContextUtils.getRequest().getSession();
		Object obj = session.getAttribute(param);
		return obj;
	}

	public static BigDecimal toFixed(BigDecimal v, int scale) {
		BigDecimal bigDecimal = new BigDecimal("0.0");
		if (ObjectUtils.isNotEmpty(v)) {
			bigDecimal = v.setScale(scale, BigDecimal.ROUND_HALF_UP);
		}
		return bigDecimal;
	}

	/**
	 * 一直入
	 */
	public static BigDecimal roundUp(BigDecimal v, int scale) {
		return v.setScale(scale, BigDecimal.ROUND_UP);
	}

	/**
	 * b1/b2
	 */
	public static BigDecimal divide(BigDecimal b1, BigDecimal b2, int scale) {
		MathContext mc = new MathContext(20, RoundingMode.HALF_DOWN);
		return b1.divide(b2, mc).setScale(scale, BigDecimal.ROUND_UP);
	}

	/**
	 * 取余
	 */
	public static BigDecimal getBalance(BigDecimal v) {
		return roundUp(v, 0).subtract(toFixed(v, 2));
	}

	/**
	 * 取余
	 */
	public static BigDecimal getBalance(BigDecimal v, int scale) {
		return roundUp(toFixed(v, scale), 0).subtract(toFixed(v, scale));
	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static String getParameter(HttpServletRequest request, String param) {
		String str = request.getParameter(param);
		if (ObjectUtils.isEmpty(str)) {
			return "";
		}
		return str.toString();
	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static String getParameter(HttpServletRequest request, String param, String def) {
		String str = request.getParameter(param);
		if (ObjectUtils.isEmpty(str)) {
			return def;
		}
		return str.toString();
	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static String getString(Object s, String defval) {
		if (isEmpty(s)) {
			return (defval);
		}
		return (s.toString().trim());
	}

	/**
	 * 分割字符串
	 */
	public static String[] split(String s, String regex) {
		if (isEmpty(s)) {
			return null;
		} else {
			return s.split(regex);
		}

	}

	/**
	 * 参数分割字符串
	 */
	public static String[] getSplitParams(String s, String regex) {
		String str = ContextUtils.getRequest().getParameter(s);
		if (isEmpty(str)) {
			return null;
		} else {
			return str.split(regex);
		}

	}

	public static boolean isInherit(Class<?> cls, Class<?> parentClass) {
		return parentClass.isAssignableFrom(cls);
	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static String getString(int n, String defval) {
		Integer integer = new Integer(n);
		return integer.toString();
	}

	/**
	 * 返回Integer如为空返回默认值
	 */
	public static Integer getInteger(Integer s, Integer defval) {
		if (isEmpty(s)) {
			return (defval);
		}
		return (s);
	}

	/**
	 * 返回Integer如为空返回默认值
	 */
	public static Integer getInteger(String s) {
		if (!isEmpty(s)) {
			return Integer.parseInt(s);
		}
		return 0;
	}

	/**
	 * 返回Integer如为空返回默认值
	 */
	public static Integer getInteger(Object s) {
		int i = 0;
		if (!isEmpty(s)) {
			i = Integer.parseInt(s.toString());
		}
		return i;
	}

	/**
	 * 返回Integer如为空返回默认值
	 */
	public static Integer getInteger(String s, Integer defval) {
		if (isEmpty(s)) {
			return (defval);
		} else {
			return Integer.parseInt(s);
		}

	}

	/**
	 * v1>v2
	 */
	public static Boolean gt(BigDecimal v1, BigDecimal v2) {
		Boolean tag = false;
		if (v1.compareTo(v2) == 1) {
			tag = true;
		}
		return tag;
	}

	/**
	 * v1>=v2
	 */
	public static Boolean ge(BigDecimal v1, BigDecimal v2) {
		Boolean tag = false;
		if (v1.compareTo(v2) == 1) {
			tag = true;
		}
		if (v1.compareTo(v2) == 0) {
			tag = true;
		}
		return tag;
	}

	/**
	 * v1<v2
	 */
	public static Boolean lt(BigDecimal v1, BigDecimal v2) {
		Boolean tag = false;
		if (v1.compareTo(v2) == -1) {
			tag = true;
		}
		return tag;
	}

	/**
	 * v1=v2
	 */
	public static Boolean eq(BigDecimal v1, BigDecimal v2) {
		Boolean tag = false;
		if (v1.compareTo(v2) == 0) {
			tag = true;
		}
		return tag;
	}

	/**
	 * 返回Short如为空返回默认值
	 */
	public static Short getShout(Short s, Short defval) {
		if (isEmpty(s)) {
			return (defval);
		}
		return (s);
	}

	/**
	 * 判断BigDecimal是否大于0
	 */
	public static Boolean gtzero(BigDecimal v) {
		Boolean tag = false;
		if (ObjectUtils.isNotEmpty(v)) {
			if (v.doubleValue() > 0) {
				tag = true;
			}
		}
		return tag;
	}

	/**
	 * 判断BigDecimal是否大于等于0
	 */
	public static Boolean gezero(BigDecimal v) {
		Boolean tag = false;
		if (ObjectUtils.isNotEmpty(v)) {
			if (v.compareTo(new BigDecimal("0.0")) == 0 || v.compareTo(new BigDecimal("0.0")) == 1) {
				tag = true;
			}
		}
		return tag;
	}

	/**
	 * 判断BigDecimal是等于0
	 */
	public static Boolean eqzero(BigDecimal v) {
		Boolean tag = false;
		if (ObjectUtils.isNotEmpty(v)) {
			if (v.compareTo(new BigDecimal("0.0")) == -1) {
				tag = true;
			}
		}
		return tag;
	}

	/**
	 * 判断BigDecimal不等于0
	 */
	public static Boolean nezero(BigDecimal v) {
		Boolean tag = true;
		if (ObjectUtils.isNotEmpty(v)) {
			if (v.compareTo(new BigDecimal("0.0")) == 0) {
				tag = false;
			}
		}
		return tag;
	}

	/**
	 * 判断BigDecimal是否xiao于0
	 */
	public static Boolean ltzero(BigDecimal v) {
		Boolean tag = false;
		if (ObjectUtils.isNotEmpty(v)) {
			if (v.doubleValue() < 0) {
				tag = true;
			}
		}
		return tag;
	}

	/**
	 * 判断BigDecimal是否小于等于0
	 */
	public static Boolean lezero(BigDecimal v) {
		Boolean tag = false;
		if (ObjectUtils.isNotEmpty(v)) {
			if (v.compareTo(new BigDecimal("0.0")) == 0) {
				tag = true;
			}
			if (v.compareTo(new BigDecimal("0.0")) == -1) {
				tag = true;
			}
		}
		return tag;
	}

	/**
	 * 返回Double如为空返回默认值
	 */
	public static Double getDouble(Double s, Double defval) {
		if (isEmpty(s)) {
			return (defval);
		}
		return (s);
	}

	/**
	 * 返回Double如为空返回默认值
	 */
	public static Double getDouble(Double s, String defval) {
		if (isEmpty(s)) {
			return new Double(defval);
		}
		return s;
	}

	/**
	 * 返回Double如为空返回默认值
	 */
	public static BigDecimal getBigDecimal(BigDecimal s, Double defval) {
		if (isEmpty(s)) {
			return new BigDecimal(defval);
		}
		return (s);
	}

	/**
	 * 返回String如为空返回默认值
	 */
	public static Double getDouble(String s, String defval) {
		Double d = new Double(s);
		if (isNotEmpty(s)) {
			d = new Double(s);
		}
		return d;
	}

	/**
	 * Objcet转BigDecimal
	 */
	public static BigDecimal getBigDecimal(Object s, String defval) {
		BigDecimal bigDecimal = new BigDecimal(defval);
		if (isNotEmpty(s)) {
			bigDecimal = new BigDecimal(s.toString());
		}
		return bigDecimal;
	}

	/**
	 * 返回BigDecimal如为空返回默认值
	 */
	public static BigDecimal getBigDecimal(BigDecimal s, String defval) {
		if (isEmpty(s)) {
			return new BigDecimal(defval);
		}
		return (s);
	}

	/**
	 * 比较两个Integer的大小，返回较大的Integer
	 */
	public static Integer getIntegerAbs(Integer s1, Integer s2) {
		if (Math.abs(s1) > Math.abs(s2)) {
			return Math.abs(s1);
		} else {
			return Math.abs(s2);
		}
	}

	/**
	 * 返回某Integer的负值
	 */
	public static Integer getIntegerNegative(Integer s1) {
		return -s1;
	}

	public static Object getObject(Class<?> cls) {
		Object object = null;
		try {
			object = Class.forName(cls.getName()).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * 检验字符串是否为空
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.length() <= 0 || str == "" || str.equals("null") || str.equals("undefined")) {
			return true;
		}
		return false;
	}

	/**
	 * 检验字符串是否为空
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 设置生日
	 */
	public static void setBirthDay(String birthDay, Model model) {
		if (ObjectUtils.isNotEmpty(birthDay)) {
			String[] str = birthDay.split("-");
			model.addAttribute("year", str[0]);
			model.addAttribute("month", str[1]);
			model.addAttribute("day", str[2]);
		}
	}

	/**
	 * 获取生日
	 */
	public static String getBirthDay() {
		String year = ObjectUtils.getParameter("year");
		String month = ObjectUtils.getParameter("month");
		String day = ObjectUtils.getParameter("day");
		return year + "-" + month + "-" + day;

	}

	/**
	 * 所有对象都不能为空
	 */
	public static Boolean allNotEmpty(Object... objs) {
		Boolean tag = true;
		for (Object obj : objs) {
			tag = isNotEmpty(obj);
			if (!tag) {
				break;
			}
		}
		return tag;
	}

	/**
	 * 所有对象都为空
	 */
	public static Boolean allEmpty(Object... objs) {
		Boolean tag = true;
		for (Object obj : objs) {
			tag = isEmpty(obj);
			if (!tag) {
				break;
			}
		}
		return tag;
	}

	/**
	 * 判断某个字符串是否存在于数组中
	 */
	public static boolean contains(String[] stringArray, String source) {
		List<String> tempList = Arrays.asList(stringArray);
		if (tempList.contains(source)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断某个字符串是否存在于数组中
	 */
	public static boolean contains(List<String> stringArray, String source) {
		if (stringArray.contains(source)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * java风格编程：驼峰式命名<br/>
	 */
	public static String javaStyle(String columnName) {
		String patternStr = "(_[a-z])";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(columnName);
		StringBuffer buf = new StringBuffer();
		while (matcher.find()) {
			String replaceStr = matcher.group();
			matcher.appendReplacement(buf, replaceStr.toUpperCase());
		}
		matcher.appendTail(buf);
		return buf.toString().replaceAll("_", "");
	}

	/**
	 * 处理：去掉前缀，驼峰式写法
	 */
	public static String javaStyleOfTableName(String tableName) {
		String prefixs = SetupConfig.getInstance().getIgnorePrefix();
		String[] ps = prefixs.split(",");
		for (int i = 0; i < ps.length; i++) {
			if (tableName.startsWith(ps[i])) {
				tableName = tableName.replaceAll(ps[i], "");
			}
		}
		return ObjectUtils.javaStyle(tableName);
	}

	/**
	 * 首字母转小写
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	public static String upperCase(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	public static String lowerCase(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'A' && ch[0] <= 'Z') {
			ch[0] = (char) (ch[0] + 32);
		}
		return new String(ch);
	}

	/**
	 * 首字母转大写
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * 生成UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();

	}

	/**
	 * 获取Map
	 */
	public static Map<String, Object> getHashMap() {
		return new HashMap<String, Object>();
	}

	/**
	 * 判断是否为Ajax请求
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null && requestType.equals("XMLHttpRequest")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为Ajax请求
	 */
	public static boolean isAjaxRequest(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestType = httpRequest.getHeader("X-Requested-With");
		if (requestType != null && requestType.equals("XMLHttpRequest")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否非静态资源
	 */
	public static boolean isValidRequestType(HttpServletRequest request) {
		String fileType[] = { ".html", ".html", ".do", ".json" };
		String uri = request.getRequestURI();
		for (String type : fileType) {
			if (uri.indexOf(type) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取List
	 */
	public static <T> List<T> getArrayList() {
		return new ArrayList<T>();
	}

	public static String getSalt(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 拼接流水号
	 */
	public static String getSerialNo(String prefix, int serialNo) {
		String sn = String.format("%s%s%04d", prefix, DateUtil.getSimDate(), serialNo);
		return sn;
	}

	/** 对象属性拷贝 */
	public static void copy(Object source, Object target, String... fields) {
		BeanUtils.copyProperties(source, target, getNullPropertyNames(source, fields));
	}

	public static String[] getNullPropertyNames(Object source, String[] fields) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();
		Set<String> emptyNames = new HashSet<String>();
		if (ObjectUtils.isNotEmpty(fields)) {
			for (String str : fields) {
				emptyNames.add(str);
			}
		}
		for (java.beans.PropertyDescriptor pd : pds) {
			String name = pd.getName();
			if (name.equals("callback") || name.equals("callbacks") || name.equals("class")) {
				break;
			}
			Object srcValue = src.getPropertyValue(pd.getName());
			if (ObjectUtils.isEmpty(srcValue)) {
				emptyNames.add(pd.getName());
			}

		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	/**
	 * 获取验证吗
	 */
	public static String getIdentifyingCode() {
		return new Integer((int) ((Math.random() * 9 + 1) * 1000)).toString();
	}

	public static int getStatus(String t) {
		int result = 0;
		if (t.equals("是")) {
			result = 1;
		}
		return result;
	}

	/**
	 * 合并资源
	 */
	public static void merge(List<Resource> t1, List<Resource> t2) {
		if (ObjectUtils.isNotEmpty(t1) && ObjectUtils.isNotEmpty(t2)) {
			t1.removeAll(t2);
			t1.addAll(t2);
		}
		if (ObjectUtils.isEmpty(t1) && ObjectUtils.isNotEmpty(t2)) {
			t1.addAll(t2);
		}
	}

	/**
	 * t1去除拥有的t2
	 */
	public static void toRepeat(List<Resource> t1, List<Resource> t2) {
		if (ObjectUtils.isNotEmpty(t1) && ObjectUtils.isNotEmpty(t2)) {
			t1.removeAll(t2);
		}
	}

	public static String getWxRandom() {
		String currTime = DateUtil.getLangDate();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		return strReq;
	}

	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}

	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode(String string) {

		StringBuffer unicode = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {

			// 取出每一个字符
			char c = string.charAt(i);

			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		}

		return unicode.toString();
	}

	/**
	 * unicode 转字符串
	 */
	public static String unicode2String(String unicode) {

		StringBuffer string = new StringBuffer();

		String[] hex = unicode.split("\\\\u");

		for (int i = 1; i < hex.length; i++) {

			// 转换出每一个代码点
			int data = Integer.parseInt(hex[i], 16);

			// 追加成string
			string.append((char) data);
		}

		return string.toString();
	}

	public static Map<String, Object> getRequest(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<?> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();

			String[] paramValues = request.getParameterValues(paramName);
			if (paramValues.length == 1) {
				String paramValue = paramValues[0];
				if (paramValue.length() != 0) {
					System.out.println("参数：" + paramName + "=" + paramValue);
					map.put(paramName, paramValue);
				}
			}
		}
		return map;
	}

	public static String getInputStream(HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		InputStream is;
		String xml = "";
		try {
			request.setCharacterEncoding("GBK");
			is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) {
				sb.append(new String(s.toString().getBytes("GBK"), "UTF-8"));
			}
			xml = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return xml;
	}

	/* 从指定字符串截取 */
	public static String substring(String str, String chat) {
		int index = str.indexOf(chat);
		if (index != -1) {
			str = str.substring(index + 1);
		}
		return str;
	}

	/** 格式化人民币 */
	public static String formatCurrency(BigDecimal d) {
		if (ObjectUtils.isNotEmpty(d)) {
			return d.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
		} else {
			return "";
		}
	}

	/**
	 * 返回字符串如为空返回默认值
	 */
	public static BigDecimal getDecimalParameter(HttpServletRequest request, String param) {
		BigDecimal bigDecimal = new BigDecimal("0.00");
		String str = request.getParameter(param);
		if (ObjectUtils.isNotEmpty(str)) {
			bigDecimal = new BigDecimal(str);
		}
		return bigDecimal;
	}

	public final static String getIpAddress(HttpServletRequest request) {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}

	public static CriterionMap conversionMap(CriterionMap map) {
		//System.out.println(map);
		CriterionMap result = new CriterionMap();
		for (String obj : map.keySet()) {
			System.out.println(obj);
			Object object =map.get(obj); 
			String value=conversion(object);
			result.put(obj, value);
		}
		//System.out.println(result);
		return result;
	}

	public static String conversion(Object o) {
		String defaultVal = "-";
		if (o == null) {
			return defaultVal;
		}
		if (o instanceof BigDecimal) {
			if (Double.parseDouble(o.toString()) == 0) {
				return defaultVal;
			}
		}
		if (o instanceof Integer) {
			if (Double.parseDouble(o.toString()) == 0) {
				return defaultVal;
			}
		}
		if (o instanceof String) {
			if (ObjectUtils.isEmpty(o) || o.equals("0.00") || o.equals("0.000") || o.equals("0")) {
				return defaultVal;
			}
		}
		return o.toString();
	}

	public static void main(String[] args) {
		/*
		 * String aString = URLDecoder.decode(
		 * "%E5%9B%9E%E6%9D%A5%E4%BA%86%F0%9F%98%8F%F0%9F%98%81%E5%BC%80%E5%B7%A5"
		 * ); System.out.print(aString +
		 * unicode2String("\\ud83d\\ude00\\ud83d\\ude00\\u597d\\u7684")); String
		 * a = "测试"; String b = URLEncode(a); String c = URLDecoder(b);
		 * System.out.println(b); System.out.println(c);
		 * 
		 * System.out.println(lowerCase("ABC"));
		 * System.out.println(upperCase("abc"));
		 */
	}
}
