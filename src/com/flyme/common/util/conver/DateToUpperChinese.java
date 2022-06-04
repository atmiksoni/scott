package com.flyme.common.util.conver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateToUpperChinese {

    private static final String[] NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍",
            "陆", "柒", "捌", "玖" };

    /** 通过 yyyy-MM-dd 得到中文大写格式 yyyy MM dd 日期 */
    public static synchronized String toChinese(String str) {
        StringBuffer sb = new StringBuffer();
        sb.append(getSplitDateStr(str, 0)).append(" ").append(
                getSplitDateStr(str, 1)).append(" ").append(
                getSplitDateStr(str, 2));
        return sb.toString();
    }

    /** 分别得到年月日的大写 默认分割符 "-" */
    public static String getSplitDateStr(String str, int unit) {
        // unit是单位 0=年 1=月 2日
        String[] DateStr = str.split("-");
        if (unit > DateStr.length)
            unit = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < DateStr[unit].length(); i++) {

            if ((unit == 1 || unit == 2) && Integer.valueOf(DateStr[unit]) > 9) {
                sb.append(convertNum(DateStr[unit].substring(0, 1)))
                        .append("拾").append(
                                convertNum(DateStr[unit].substring(1, 2)));
                break;
            } else {
                sb.append(convertNum(DateStr[unit].substring(i, i + 1)));
            }
        }
        if (unit == 1 || unit == 2) {
            return sb.toString().replaceAll("^壹", "").replace("零", "");
        }
        return sb.toString();

    }

    /** 转换数字为大写 */
    private static String convertNum(String str) {
        return NUMBERS[Integer.valueOf(str)];
    }

    /** 判断是否是零或正整数 */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static void main(String args[]) {

        System.out.println(toChinese("2008-10-02"));

    }

}