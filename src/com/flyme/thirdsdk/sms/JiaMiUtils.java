package com.flyme.thirdsdk.sms;

public class JiaMiUtils {
	public static String appkey="c306e6eb-fdba-11e7-9bb0-00163e0004bf";
	public static String sercret="jZ0F9RTa5Y4NDZ95C4n38SuddBgtSw05";
    public static String iv;
    public static long time;
    public static String DESIV;

    public static String getkey(String time) {
        String a = "";
        StringBuffer buffer2 = new StringBuffer(MD5Utils.md5Password(time, 1));
        String hh = buffer2.toString().substring(0, 8);
        a = hh.toUpperCase() + appkey;
        StringBuffer buffer = new StringBuffer(MD5Utils.md5Password(a, 1));
        String b = buffer.toString();
        a = b.substring(12, 20).toLowerCase();
        return a;
    }

    public static String getiv(String time) {
        String a = "";
        StringBuffer buffer3 = new StringBuffer(MD5Utils.md5Password(time, 1));//d080db3b03e22080ce132023fc0f306c
        String dd = buffer3.substring(12, 20);
        a = dd.toLowerCase() + sercret;
        StringBuffer buffer4 = new StringBuffer(MD5Utils.md5Password(a, 1));
        a = buffer4.substring(24, 32).toUpperCase();
        return a;
    }
}
