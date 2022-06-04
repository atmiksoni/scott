package com.flyme.common.util.conver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialClob;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.flyme.common.constants.Global;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.map.CriterionMap;

public class ConvertUtils {
	private final static Logger log = Logger.getLogger(ConvertUtils.class);

	/**
	 * 字符串转数字
	 */
	public static Integer getInteger(Object object) {
		Integer i = -1;
		if (ObjectUtils.isEmpty(object)) {
			return i;
		}
		try {
			i = (Integer.parseInt(getString(object)));
		} catch (NumberFormatException e) {
			log.debug("字符串转数字异常");
			return i;
		}
		return i;
	}

	// 将数字转化为大写
	public static String numToUpper(int num) {
		// String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
		String u[] = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		char[] str = String.valueOf(num).toCharArray();
		String rstr = "";
		for (int i = 0; i < str.length; i++) {
			rstr = rstr + u[Integer.parseInt(str[i] + "")];
		}
		return rstr;
	}

	// 将数字转化为大写
	public static String numToWeek(int num) {
		// String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
		String u[] = { "一", "二", "三", "四", "五", "六", "日" };
		char[] str = String.valueOf(num - 1).toCharArray();
		String rstr = "";
		for (int i = 0; i < str.length; i++) {
			rstr = rstr + u[Integer.parseInt(str[i] + "")];
		}
		return rstr;
	}

	/**
	 * 字符串转数字
	 */
	public static Short getShort(String str) {
		Short i = null;
		if (ObjectUtils.isEmpty(str)) {
			return i;
		}
		try {
			i = (Short.parseShort(str));
		} catch (NumberFormatException e) {
			log.debug("字符串转数字异常");
			return i;
		}
		return i;
	}

	/**
	 * 字符串转Long
	 */
	public static Long getLong(String str) {
		Long l = null;
		if (ObjectUtils.isEmpty(str)) {
			return l;
		}
		try {
			l = (Long.parseLong(str));
		} catch (NumberFormatException e) {
			log.debug("字符串转数字异常");
			return l;
		}
		return l;
	}

	/**
	 * 字符串转Double
	 */
	public static Double getDouble(String s) {
		Double d = new Double("0");
		if (ObjectUtils.isNotEmpty(s)) {
			d = new Double(s);
		}
		return d;
	}

	public static String getString(Object object) {
		if (ObjectUtils.isEmpty(object)) {
			return "";
		}
		return (object.toString().trim());
	}

	/**
	 * 字符串转Double
	 */
	public static Double getDouble(Object object) {
		Double d = null;
		if (ObjectUtils.isEmpty(object)) {
			return d;
		}
		try {
			d = (Double.parseDouble(getString(object)));
		} catch (NumberFormatException e) {
			return d;
		}
		return d;
	}

	/**
	 * CLOB类型转换成String类型
	 */

	public String ClobToString(Clob clob) throws SQLException, IOException {

		String reString = "";
		Reader is = clob.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
			sb.append(s);
			s = br.readLine();
		}
		reString = sb.toString();
		return reString;
	}

	/**
	 * 将String转成Clob
	 * 
	 * @param str字段
	 */
	public static Clob stringToClob(String str) {
		if (null == str)
			return null;
		else {
			try {
				Clob c = new SerialClob(str.toCharArray());
				return c;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 将String转成BigDecimal
	 * 
	 * @param str字段
	 */
	public static BigDecimal getBigDecimal(String v, int scale) {
		BigDecimal value;
		if (ObjectUtils.isNotEmpty(v)) {
			value = new BigDecimal(v).setScale(scale, BigDecimal.ROUND_HALF_UP);
		} else {
			value = new BigDecimal(0);
		}
		return value;
	}

	/**
	 * 将String转成BigDecimal
	 * 
	 * @param str字段
	 */
	public static BigDecimal getBigDecimal(BigDecimal v, int scale) {
		BigDecimal value;
		if (ObjectUtils.isNotEmpty(v)) {
			value = v.setScale(scale, BigDecimal.ROUND_HALF_UP);
		} else {
			value = new BigDecimal(0);
		}
		return value;
	}

	/**
	 * 将String转成BigDecimal
	 * 
	 * @param str字段
	 */
	public static BigDecimal getBigDecimal(Double v, int scale) {
		BigDecimal value;
		if (ObjectUtils.isNotEmpty(v)) {
			value = new BigDecimal(v.toString()).setScale(scale, BigDecimal.ROUND_HALF_UP);
		} else {
			value = new BigDecimal(0);
		}
		return value;
	}

	/**
	 * 将String转成BigDecimal
	 * 
	 * @param str字段
	 */
	public static BigDecimal getBigDecimal(Double v) {
		BigDecimal value;
		if (ObjectUtils.isNotEmpty(v)) {
			value = new BigDecimal(v.toString());
		} else {
			value = new BigDecimal(0);
		}
		return value;
	}

	/**
	 * 将String转成BigDecimal
	 * 
	 * @param str字段
	 */
	public static BigDecimal getBigDecimal(String v, BigDecimal def, int scale) {
		BigDecimal value;
		if (ObjectUtils.isNotEmpty(v)) {
			value = new BigDecimal(v).setScale(scale);
		} else {
			value = def;
		}
		return value;
	}

	/**
	 * b1/b2
	 */
	public static BigDecimal divide(BigDecimal b1, BigDecimal b2) {
		MathContext mc = new MathContext(20, RoundingMode.HALF_DOWN);
		return b1.divide(b2, mc).setScale(2, BigDecimal.ROUND_UP);
	}

	/**
	 * 将String转成BigDecimal
	 * 
	 * @param str字段
	 */
	public static BigDecimal getBigDecimal(String v) {
		BigDecimal value;
		if (ObjectUtils.isNotEmpty(v)) {
			value = new BigDecimal(v);
		} else {
			value = new BigDecimal(0);
		}
		return value;
	}

	/**
	 * 解析页面经过encodeURI编码的字符串
	 */
	public static String getURLDecoder(String str) {
		String s = "";
		try {
			s = URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 合并list
	 */
	public static <T> List<T> sumList(List<T> t1, List<T> t2) {
		List<T> list = ObjectUtils.getArrayList();
		if (ObjectUtils.isNotEmpty(t1)) {
			list.addAll(t1);
		}
		if (ObjectUtils.isNotEmpty(t2)) {
			list.addAll(t2);
		}
		return list;
	}

	/**
	 * 字符串转Long
	 */
	public static Integer getInteger(Long l) {
		Integer i = null;
		if (ObjectUtils.isEmpty(l)) {
			return i;
		}
		try {
			i = l.intValue();
		} catch (NumberFormatException e) {
			log.debug("字符串转数字异常");
			return i;
		}
		return i;
	}

	/**
	 * List转换为数组
	 */
	public static <T> Object[] listToArray(List<T> list) {
		Object[] strs = list.toArray(new Object[list.size()]);
		return strs;
	}

	/**
	 * 以逗号分隔的转换成list
	 */
	public static List<String> arrayToList(String[] list, Integer index) {
		List<String> obj = new ArrayList<String>();
		if (ObjectUtils.isNotEmpty(list)) {
			Integer i = 0;
			for (String string : list) {
				if (!i.equals(index)) {
					obj.add(string);
				}
				i++;
			}
		}
		return obj;
	}

	/**
	 * 以逗号分隔的转换成list
	 */
	public static List<String> stringTolist(String str) {
		List<String> list = new ArrayList<String>();
		if (ObjectUtils.isNotEmpty(str)) {
			String[] temp = str.split(",");
			list = Arrays.asList(temp);
		}
		return list;
	}

	/**
	 * 数组转换为字符串
	 */
	public static String ArrayToString(Object[] strs) {
		StringBuffer sb = new StringBuffer();
		if (!ObjectUtils.isEmpty(strs)) {
			int i = 0;
			for (Object string : strs) {
				sb.append(string.toString());
				i++;
				if (i < strs.length) {
					sb.append(",");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 数组转换为字符串
	 */
	public static String MapToString(CriterionMap map) {

		String str = "";
		if (ObjectUtils.isNotEmpty(map)) {
			Collection<Object> list = map.values();
			Object[] strs = list.toArray(new Object[list.size()]);
			str = ArrayToString(strs);

		}
		return str;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> MapToList(Map<String, Object> maps) {

		List<T> list = new ArrayList<T>();
		for (Map.Entry<String, Object> entry : maps.entrySet()) {
			list.add((T) entry.getValue());
		}
		return list;
	}

	public static String spilt(String str) {
		StringBuffer sb = new StringBuffer();
		String[] temp = str.split(",");
		for (int i = 0; i < temp.length; i++) {
			if (!"".equals(temp[i]) && temp[i] != null)
				sb.append("'" + temp[i] + "',");
		}
		String result = sb.toString();
		String tp = result.substring(result.length() - 1, result.length());
		if (",".equals(tp))
			return result.substring(0, result.length() - 1);
		else
			return result;
	}

	public static String spilt(List<String> str) {
		StringBuffer sb = new StringBuffer();
		String result = "";
		if (ObjectUtils.isNotEmpty(str)) {
			for (String string : str) {
				if (!"".equals(string) && string != null)
					sb.append("'" + string + "',");
			}
			result = sb.toString();
			String tp = result.substring(result.length() - 1, result.length());
			if (",".equals(tp)) {
				result = result.substring(0, result.length() - 1);
			}
		}
		return result;
	}

	public static String listToString(List<String> stringList) {
		if (ObjectUtils.isNotEmpty(stringList)) {
			return StringUtils.join(stringList, ",");
		} else {
			return null;
		}

	}

	public List<Object> repeat(List<Object> t1, List<Object> t2) {
		t1.removeAll(t2);
		return t1;
	}

	public static String toPercent(int a, int b) {
		String percent = "0.00%";
		if (b > 0) {
			BigDecimal aa = new BigDecimal(a);
			BigDecimal bb = new BigDecimal(b);
			Double v = aa.doubleValue() / bb.doubleValue();
			NumberFormat fmt = NumberFormat.getPercentInstance();
			fmt.setMaximumFractionDigits(2);// 最多两位百分小数，如25.23%
			percent = fmt.format(v);
		}
		return percent;
	}

	public static String toPercent(BigDecimal a, BigDecimal b) {
		String percent = "0.00%";
		if (ObjectUtils.gtzero(b) && ObjectUtils.gezero(a)) {
			double v = divide(a, b).doubleValue();
			NumberFormat fmt = NumberFormat.getPercentInstance();
			fmt.setMaximumFractionDigits(2);// 最多两位百分小数，如25.23%
			percent = fmt.format(v);
		}
		return percent;
	}

	/**
	 * 将元为单位的转换为分 （乘100）
	 * 
	 */
	public static int changeY2F(BigDecimal amount) {
		return amount.multiply(new BigDecimal(100)).intValue();
	}

	/**
	 * 转boolean
	 */
	public static Boolean getBoolean(Object t) {
		Boolean result = false;
		if (Global.ENABLE.equals(t)) {
			result = true;
		}
		if ("true".equals(t)) {
			result = true;
		}
		if (t.equals("1")) {
			result = true;
		}
		if (t.equals("是")) {
			result = true;
		}
		return result;
	}

	/**
	 * base64转byte
	 */
	public static byte[] Base64ToByte(String base64) {
		byte[] photo = Base64.getDecoder().decode(base64);
		return photo;
	}

	/**
	 * base64转byte
	 */
	public static String ByteToBase64(byte[] bte) {
		String base64 = "";
		if (ObjectUtils.isNotEmpty(bte)) {
			base64 = Base64.getEncoder().encodeToString(bte);
		}
		return base64;
	}

	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode(String string) {
		String result = "";
		if(ObjectUtils.isNotEmpty(string)){
			StringBuffer unicode = new StringBuffer();
			for (int i = 0; i < string.length(); i++) {
				// 取出每一个字符
				char c = string.charAt(i);
				// 转换为unicode
				unicode.append("\\u" + Integer.toHexString(c));
			}
			result = unicode.toString();
		}
		return result;
	}

	/** 
	 * unicode 转字符串 
	 */  
	public static String unicode2String(String unicode) {  
	    String result = "";
		if(ObjectUtils.isNotEmpty(unicode)){
			StringBuffer string = new StringBuffer();  
		    String[] hex = unicode.split("\\\\u");  
		    for (int i = 1; i < hex.length; i++) {  
		        // 转换出每一个代码点  
		        int data = Integer.parseInt(hex[i], 16);  
		        // 追加成string  
		        string.append((char) data);  
		    }  
		    result = string.toString();
		}
	    return result;  
	}  
	
	
	public static void main(String[] arg) {
		BigDecimal a = new BigDecimal("5.269");
		System.out.println(changeY2F(a));
	}

}
