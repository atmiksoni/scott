package com.flyme.common.util.context;

import java.util.ResourceBundle;

import com.flyme.common.util.conver.ConvertUtils;

public class SysConfigUtil {
	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("sysConfig");

	/**
	 * 获取配置文件参数
	 */
	public static final String getConfigByName(String name) {
		return bundle.getString(name);
	}

	public static String getServerPath() {
		return SysConfigUtil.getConfigByName("serverName");
	}

	public static Boolean isServer() {
		return ConvertUtils.getBoolean(getConfigByName("isServer"));
	}

	public static String getOpenId() {
		return ConvertUtils.getString(getConfigByName("openId"));
	}
	public static Boolean getSmsStatus() {
		return ConvertUtils.getBoolean(getConfigByName("SmsStatus"));
	}
}
