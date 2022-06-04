package com.flyme.base.pay.vo;
import com.flyme.core.mybatis.type.IntEnum;

/**订单状态 */
public enum OrderStatusEnum implements IntEnum<OrderStatusEnum> {
	
	CANCLE(-1, "已取消"),
	NOPAY(0, "待支付"),
	NODELIVERY(1, "待发货"),
	HASDELIVERY(2, "已发货"),
	ONREFUND(11, "退款中"),
	REFUNDFINISHED(12, "已退款"),
	FINISHED(20, "已完成"),
	EVALUATE(21, "已评价");
	
	private String value;
	private int index;
	OrderStatusEnum(int index, String value) {
		this.index = index;
		this.value = value;
	}

	OrderStatusEnum(int index) {
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
		for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
			if (orderStatusEnum.getIndex() == index) {
				v = orderStatusEnum.name();
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