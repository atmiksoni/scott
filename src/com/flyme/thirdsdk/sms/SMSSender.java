package com.flyme.thirdsdk.sms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.date.DateUtil;
import com.flyme.common.util.md5.MD5Util;



/**
 * 短信发送接口
 */
public class SMSSender {
	private static Logger logger = LoggerFactory.getLogger(SMSSender.class);

	// 发送传递的参数
	// http://www.ztsms.cn:8800/sendXSms.do?username=用户名&password=密码&mobile=手机号码&content=内容&dstime=&productid=产品ID&xh=留空
	private static String username = "you66yzm"; // 您的用户名;
	private static String password = "QEnCWy"; // 密码;
	private static Integer productid = 676767; // 产品ID号;
	private static String xh="1";
	private static String sendurl = "http://www.yzmsms.cn/"; // 助通提供的发送地址;

	/**
	 * 创建一个新的实例 SMSSender. 助通科技提供的用户名与地址
	 */
	/****
	 * username 用户名（必填） password 密码（必填） mobile 手机号，多个手机号为用半角 ,
	 * 分开，如13899999999,13688888888(最多100个，必填) content 发送内容（必填） dstime
	 * 定时时间，为空时表示立即发送（选填） productid 产品id(必填) xh 扩展号,留空
	 ***/

	public static String sendSms(String mobile, String content) {
		String tkey=DateUtil.getLangDate();
		String sendUrl = null;
		try {// 否则发到手机乱码
			sendUrl = sendurl + "sendSmsYZM.do?username=" + username + "&password=" + MD5Util.MD5(MD5Util.MD5(password)+tkey) +"&tkey="+tkey+"&mobile=" + mobile + "&content=" + URLEncoder.encode(content, "UTF-8") + "&xh=" + xh;
		} catch (UnsupportedEncodingException uee) {
			logger.error(uee.toString());
		}
		logger.info("短信内容为------------->:" + content);
		return getUrl(sendUrl);
	}

	/**
	 * @Title: getUrl
	 * @Description: 获取地址
	 * @param urlString
	 * @return
	 */

	public static String getUrl(String urlString) {
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			conn.setReadTimeout(15000);
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			for (String line = null; (line = reader.readLine()) != null;) {
				sb.append(line + "\n");
			}
			reader.close();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		String result = "";
		try {
			result = URLDecoder.decode(sb.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return result;
	}

	/***
	 * 测试地址
	 ***/
	public static void main(String[] args) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String message = "您的验证码是:728191。请不要把验证码泄露给他人【优网乐】";
		String phone = "18838219339";
		sendSms(phone, message);

	}

	/**
	 * 查询短信剩余数量 200代表短信的数量 -1用户名或者密码不正确 -2没有url提交权限 -3 产品id不正确
	 */
	public static String getBalance() {
		String result = "";
		try {// 否则发到手机乱码
			String sendUrl = "http://www.ztsms.cn:8800/balance.do?username=" + username + "&password=" + password + "&productid=" + productid;
			result = getUrl(sendUrl);
		} catch (Exception uee) {
			logger.error(uee.toString());
		}

		int tmp = ObjectUtils.getInteger(result.trim());
		switch (tmp) {
		case -1:
			result = "用户名或者密码不正确";
		case -2:
			result = "没有url提交权限";
		case -3:
			result = " 产品id不正确";
		default:
			logger.info("短信总余额为------------->:" + result);
		}
		return result.trim();
	}
}
