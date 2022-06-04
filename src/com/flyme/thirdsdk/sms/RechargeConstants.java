package com.flyme.thirdsdk.sms;


/**
 * 用户类型
 */
public enum RechargeConstants {
	CREATE(0, "充值中"), SENDSUCCESS(1, "充值成功"), SENDFAILED(9, "失败"), 
	ERROR_CODE_0(0, "成功"),
	ERROR_CODE_208501(208501, "不允许充值的手机号码及金额"), 
	ERROR_CODE_208502(208502, "请求手机号和面值查询商品信息失败，请重试"), 
	ERROR_CODE_208503(208503, "运营商地区维护，暂不能充值"), 
	ERROR_CODE_208504(208504, "请求手机号和面值查询商品信息错误"),
	ERROR_CODE_208505(208505, "无效手机号码"),
	ERROR_CODE_208506(208506, "错误的充值金额"),
	ERROR_CODE_208507(208507, "充值失败"),
	ERROR_CODE_208508(208508, "请求充值失败，请重试"),
	ERROR_CODE_208509(208509, "错误的订单号"),
	ERROR_CODE_208510(208510, "请求订单状态失败，请重试"),
	ERROR_CODE_208513(208513, "查询订单失败"),
	ERROR_CODE_208514(208514, "不合规范的订单号（8-32位）"),
	ERROR_CODE_208515(208515, "校验值sign错误"),
	ERROR_CODE_208516(208516, "重复的订单号"),
	ERROR_CODE_208517(208517, "当前账户可用余额不足"); 
	
	private int index;
	private String name;
	
	private RechargeConstants(int index, String name) {
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
		for (RechargeConstants r : RechargeConstants.values()) {
			if (r.getIndex() == index) {
				return r.name;
			}
		}
		return null;
	}
	
}
