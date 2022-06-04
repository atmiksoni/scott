package com.flyme.common.util.context;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils extends Properties {
	private static final long serialVersionUID = 1L;
	private static PropertiesUtils instance;

	public static PropertiesUtils getInstance() {
		if (instance != null) {
			return instance;
		}

		makeInstance();
		return instance;
	}

	private static synchronized void makeInstance() {
		if (instance == null) {
			instance = new PropertiesUtils();
		}
	}

	private PropertiesUtils() {
		InputStream is = getClass().getResourceAsStream("/jdbc.properties");
		try {
			load(is);
		} catch (Exception e) {
		}
	}
}