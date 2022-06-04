package com.flyme.common.util.juhe;

import com.flyme.common.util.http.HttpUtils;
import com.flyme.common.util.md5.MD5Util;

import net.sf.json.JSONObject;

public class ShoujiHuaFei {
	// HttpClient请求的相关设置，可以不用配置，用默认的参数，这里设置连接和超时时长(毫秒)
	public static final String key = "2c4e13400ac36324a7620b378747e5b2";// 申请的接口Appkey
	public static final String openId = "JHe937dde1cb0c0af757fb17d1ed1765fd";// 在个人中心查询
	public static final String telCheckUrl = "http://op.juhe.cn/ofpay/mobile/telcheck?cardnum=*&phoneno=!&key=" + key;
	public static final String telQueryUrl = "http://op.juhe.cn/ofpay/mobile/telquery?cardnum=*&phoneno=!&key=" + key;
	public static final String onlineUrl = "http://op.juhe.cn/ofpay/mobile/onlineorder?key=" + key + "&phoneno=!&cardnum=*&orderid=@&sign=$";
	public static final String yueUrl = "http://op.juhe.cn/ofpay/mobile/yue?key=" + key + "&timestamp=%&sign=$";
	public static final String orderstaUrl = "http://op.juhe.cn/ofpay/mobile/ordersta?key=" + key + "&orderid=!";
	public static final String orderListUrl = "http://op.juhe.cn/ofpay/mobile/orderlist?key=" + key;
	
	/***
	 * 1.检测手机号码是否能充值
	 * 
	 * @param phone
	 *            手机号码
	 * @param cardnum
	 *            充值金额,目前可选：5、10、20、30、50、100、300
	 * @return 返回错码，0为允许充值的手机号码及金额，其他为不可以或其他错误
	 * @throws Exception
	 */
	public static int telCheck(String phone, int cardnum) throws Exception {
		int error_code = 0;
		String result = HttpUtils.get(telCheckUrl.replace("*", cardnum + "").replace("!", phone), 0);
		error_code = JSONObject.fromObject(result).getInt("error_code");
		return error_code;
	}
	
	/***
	 * 2.根据手机号和面值查询商品信息
	 * 
	 * @param phone
	 *            手机号码
	 * @param cardnum
	 *            充值金额,目前可选：5、10、20、30、50、100、300
	 * @return String类型结果
	 * @throws Exception
	 */
	public static String telQuery(String phone, int cardnum) throws Exception {
		String result = HttpUtils.get(telQueryUrl.replace("*", cardnum + "").replace("!", phone), 0);
		return result;
	}
	
	/***
	 * 3.依据用户提供的请求为指定手机直接充值
	 * 
	 * @param phone
	 *            手机号码
	 * @param cardnum
	 *            充值金额,目前可选：5、10、20、30、50、100、300
	 * @param orderid
	 *            商家订单号，8-32位字母数字组合，自定义
	 * @return 返回String结果
	 * @throws Exception
	 */
	public static String onlineOrder(String phone, int cardnum, String orderid) throws Exception {
		String result = null;
		String sign = MD5Util.MD5(openId + key + phone + cardnum + orderid);
		result = HttpUtils.get(onlineUrl.replace("*", cardnum + "").replace("!", phone).replace("@", orderid).replace("$", sign), 0);
		return result;
	}
	
	/***
	 * 4.查询账户余额
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String yuE() throws Exception {
		String timestamp = System.currentTimeMillis()/1000+"";
		String sign = MD5Util.MD5(openId+key+timestamp);
		String result = HttpUtils.get(yueUrl.replace("%",timestamp).replace("$",sign),0);
		return result;
	}
	
	/***
	 * 5.订单状态查询 * @param orderid 商家订单号* @return 订单结果* @throws Exception
	 */
	public static String orderSta(String orderid) throws Exception {
		return HttpUtils.get(orderstaUrl.replace("1", orderid), 0);
	}
	  
	/*** 6.历史订单列表* @return* @throws Exception */
	public static String orderList() throws Exception {
		return HttpUtils.get(orderListUrl, 0);
	}
	
	/***
	 * 测试地址
	 ***/
	public static void main(String[] args) {
		
		//String sign = MD5Util.MD5("JHe937dde1cb0c0af757fb17d1ed1765fd2c4e13400ac36324a7620b378747e5b217737680507103333333444466");
		String phone = "18790255910";
		int money = 20;
		String id = "20161111133138";
		try {
			String reString = onlineOrder(phone, money,id);
			System.out.println(reString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
