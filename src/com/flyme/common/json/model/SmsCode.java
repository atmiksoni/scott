package com.flyme.common.json.model;

import java.io.Serializable;


/**
 * 短信验证码
 */
public class SmsCode extends ResponseBaseRet implements Serializable{
	private static final long serialVersionUID = 1L;
	private String code;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * 获取成功
	 */
	public static String status_1="1";
	/**
	 * 非法号码
	 */
	public static String status_6="6";
	/**
	 * 已注册
	 */
	public static String status_51="51";
	/**
	 * 未注册
	 */
	public static String status_52="52";
	/**
	 * 服务器异常
	 */
	public static String status_50="50";
	
}
