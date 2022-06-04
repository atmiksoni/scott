package com.flyme.thirdsdk.sms;


/**
 * 用户类型
 */
public enum FlowsConstants {
	CREATE(0, "充值中"), SENDSUCCESS(1, "充值成功"), SENDFAILED(9, "失败"), 
	ERROR_CODE_0(0, "成功"),
	ERROR_CODE_208501(210501, "错误的手机号码"), 
	ERROR_CODE_208502(210502, "错误的面值"), 
	ERROR_CODE_208503(210503, "检索不到符合该手机号码的流量套餐"), 
	ERROR_CODE_208504(210504, "套餐ID不符合当前手机号"),
	ERROR_CODE_208505(210505, "不合规范的订单号（8-32位）"),
	ERROR_CODE_208506(210506, "校验值sign错误"),
	ERROR_CODE_208507(210507, "此号码正在充值中"),
	ERROR_CODE_208508(210508, "重复的订单号"),
	ERROR_CODE_208509(210509, "订单生成失败"),
	ERROR_CODE_208510(210510, "受理充值失败");
	
	private int index;
	private String name;
	
	private FlowsConstants(int index, String name) {
		this.name = name;
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public static String getName(int index) {
		for (FlowsConstants r : FlowsConstants.values()) {
			if (r.getIndex() == index) {
				return r.name;
			}
		}
		return null;
	}
	
}
