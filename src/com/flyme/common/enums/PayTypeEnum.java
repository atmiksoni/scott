package com.flyme.common.enums;

import com.flyme.core.mybatis.type.IntEnum;

/** 支付方式 */
public enum PayTypeEnum implements IntEnum<PayTypeEnum> {
	NoPay(0, "未支付"), 
	Cash(1, "现金"), 
	AliPay(2, "支付宝"), 
	WxPay(3, "微信"), 
	Balance(4, "余额"), 
	Cent(5, "积分"), 
	BankCard(6, "刷卡"), 
	PreAuth(7, "预授权"),
	WechatPay(8,"微信公众号"),
	UnionPay(9, "银联"), 
	MealCard(10,"套餐卡");
	private String value;
	private int index;

	PayTypeEnum(int index, String value) {
		this.index = index;
		this.value = value;
	}

	PayTypeEnum(int index) {
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
		for (PayTypeEnum orderEnum : PayTypeEnum.values()) {
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
