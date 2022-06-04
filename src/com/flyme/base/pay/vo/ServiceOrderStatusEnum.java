package com.flyme.base.pay.vo;
import com.flyme.core.mybatis.type.IntEnum;

/**订单状态 */
public enum ServiceOrderStatusEnum implements IntEnum<ServiceOrderStatusEnum> {
	
	DELETE(99, "已删除"),
	CANCLE(-1, "已取消"),
	CONFIRM(0, "待确认"),
	NOPAY(1, "待支付"),
	WAITING(2,"待服务"),
	ONGOING(3, "进行中"),
	ONREFUND(11, "退款中"),
	REFUNDFINISHED(12, "已退款"),
	SERVICEFINISHED(19, "服务方确认完成"),
	FINISHED(20, "已完成"),
	EVALUATE(21, "已评价");
	
	private String value;
	private int index;
	ServiceOrderStatusEnum(int index, String value) {
		this.index = index;
		this.value = value;
	}

	ServiceOrderStatusEnum(int index) {
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
		for (ServiceOrderStatusEnum orderStatusEnum : ServiceOrderStatusEnum.values()) {
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