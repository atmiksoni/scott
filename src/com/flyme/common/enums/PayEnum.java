package com.flyme.common.enums;

import com.flyme.core.mybatis.type.IntEnum;

/** 支付状态 */
public enum PayEnum implements IntEnum<PayEnum> {
	NOPAY(0, "未支付"), PAY(1, "已支付"), PACKAGEPAY(2, "套餐卡支付");
	private String value;
	private int index;

	PayEnum(int index, String value) {
		this.index = index;
		this.value = value;
	}

	PayEnum(int index) {
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
		for (PayEnum orderEnum : PayEnum.values()) {
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
