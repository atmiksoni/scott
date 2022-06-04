package com.flyme.common.enums;

import com.flyme.core.mybatis.type.IntEnum;

/** 退款状态 */
public enum RefundEnum implements IntEnum<RefundEnum>{
	NO(0,"未退款"), YES(1,"已退款");
	private String value;
	private int index;

	RefundEnum(int index, String value) {
		this.index = index;
		this.value = value;
	}

	RefundEnum(int index) {
		this.index = index;
		this.value = getText(index);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public static String getText(int index) {
		String v = "";
		for (RefundEnum orderEnum : RefundEnum.values()) {
			if (orderEnum.getIndex() == index) {
				v = orderEnum.name();
				break;
			}
		}
		return v;
	}

	@Override
	public int getIntValue() {
		return this.index;
	}

}
