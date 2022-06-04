package com.flyme.common.util.prefer;

import java.util.prefs.Preferences;

/**
 * 注册表读写
 */
public class PreferencesUtil {
	static Preferences pre = Preferences.userRoot().node("/bfpms");

	public static void put(String key, String value) {
		pre.put(key, value);

	}

	public static String get(String key) {
		return pre.get(key, "");
	}

	public static void remore(String key) {
		pre.remove(key);
	}
}
