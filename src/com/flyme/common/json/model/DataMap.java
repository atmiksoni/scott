package com.flyme.common.json.model;

import java.io.Serializable;

public class DataMap implements Serializable {

	private static final long serialVersionUID = 1L;
	private String key;
	private Object value;

	public DataMap(String key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
