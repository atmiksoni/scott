package com.flyme.common.enums;

/** 退款状态 */
public enum PapersEnum {
	身份证或驾驶证(1), 台湾居民来往大陆通行证(2),港澳居民来往大陆通行证(3),外籍护照(4);
	private int value;

	PapersEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static String getValue(int value) {
		String v = "";
		for (PapersEnum orderEnum : PapersEnum.values()) {
			if (orderEnum.getValue() == value) {
				v = orderEnum.name();
				break;
			}
		}
		return v;
	}

}
