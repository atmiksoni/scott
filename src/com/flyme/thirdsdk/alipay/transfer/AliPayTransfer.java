package com.flyme.thirdsdk.alipay.transfer;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.flyme.common.util.map.CriterionMap;
import com.flyme.thirdsdk.alipay.config.AlipayConfig;

/**
 * 转账到支付宝账户（用户提现）
 * 2017/09/01
 * 
 * */
public class AliPayTransfer {

	
	public static boolean withdrawCash(CriterionMap map){
		//配置参数
		String privateKey = AlipayConfig.alipay_private_key;
		String publicKey = AlipayConfig.alipay_public_key;
		String signType = AlipayConfig.sign_type;
		String appId = AlipayConfig.app_id;
		//业务参数
		String out_biz_no = map.getString("out_biz_no");//什么东西
		String payee_account = map.getString("payee_account");//支付宝账号
		String amount = "0.01";	//map.getString("amount");
		String payer_show_name = map.getString("payer_show_name");
		String payee_real_name = map.getString("payee_real_name");
		String remark = map.getString("remark");
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",appId,privateKey,"json","GBK",publicKey,signType);
		AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
		request.setBizContent("{" +
		"    \"out_biz_no\":\""+out_biz_no+"\"," +
		"    \"payee_type\":\"ALIPAY_LOGONID\"," +
		"    \"payee_account\":\""+payee_account+"\"," +
		"    \"amount\":\""+amount+"\"," +
		"    \"payer_show_name\":\""+payer_show_name+"\"," +
		"    \"payee_real_name\":\""+payee_real_name+"\"," +
		"    \"remark\":\""+remark+"\"," +
		"  }");
		AlipayFundTransToaccountTransferResponse response = null;
		try {
			response = alipayClient.execute(request);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		if(response.isSuccess()){
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}
		return response.isSuccess();
	}
	
}
