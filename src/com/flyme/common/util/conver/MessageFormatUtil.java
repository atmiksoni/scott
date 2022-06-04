package com.flyme.common.util.conver;

import java.text.MessageFormat;

import com.flyme.common.util.map.CriterionMap;

public class MessageFormatUtil {
	private static final String START_FLAG = "#[";
	private static final String END_FLAG = "]";

	public static String format(String str, Object... object) {
		return MessageFormat.format(str, object);
	}

	/**
	 * 格式化月份"%02d"
	 */
	public static String formatMonthNow(Integer month) {
		return String.format("%03d", month);
	}

	public static void main(String[] args) {
		System.out.println(formatMonthNow(1));
	}

	public static String convert(String s, CriterionMap map) {
		StringBuilder ret = new StringBuilder(s.length());
		int cursor = 0;
		for (int start, end; (start = s.indexOf(START_FLAG, cursor)) != -1
				&& (end = s.indexOf(END_FLAG, start)) != -1;) {
			Object obj = map.get(s.substring(start + START_FLAG.length(), end));
			ret.append(s.substring(cursor, start)).append(obj);
			cursor = end + END_FLAG.length();
		}
		ret.append(s.substring(cursor, s.length()));
		return ret.toString();
	}

}
