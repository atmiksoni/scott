package com.flyme.common.enums;

import com.flyme.core.mybatis.type.IntEnum;

/** 支付选项 */
public enum PayOptionEnum implements IntEnum<PayOptionEnum> {
	depoPay(0, "支付定金"), fullPay(2, "全额支付"),cashOnPay(3,"到店支付");
	private String value;
	private int index;

	PayOptionEnum(int index, String value) {
		this.index = index;
		this.value = value;
	}

	PayOptionEnum(int index) {
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
		for (PayOptionEnum orderEnum : PayOptionEnum.values()) {
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
