package com.flyme.thirdsdk.alipay.transfer;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.flyme.common.json.model.ApiJson;
import com.flyme.thirdsdk.alipay.config.AlipayConfig;
import com.flyme.thirdsdk.alipay.prams.AlipayPrams;

public class AlipayRefund {
	 /** 
     * @param out_trade_no 订单支付时传入的商户订单号,不能和 trade_no同时为空。 
     * @param trade_no 支付宝交易号，和商户订单号不能同时为空 
     * @param refund_amount 需要退款的金额，该金额不能大于订单金额,单位为元，支持两位小数 
     * @return 将提示信息返回 
     */  
    public  synchronized static  ApiJson alipayRefundRequest(String out_trade_no, String refund_amount, String refund_reason){  
       ApiJson json = new ApiJson();
        try {  
            AlipayClient alipayClient = AlipayConfig.getInstance();
            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();  
            AlipayPrams alidata= new AlipayPrams();
            alidata.setOut_trade_no(out_trade_no);  //商家订单号
            alidata.setRefund_amount(refund_amount);  //退款金额
            alidata.setRefund_reason(refund_reason);  //退款原因
            request.setBizContent(JSONObject.toJSONString(alidata));//将数据格式化成json格式  
            AlipayTradeRefundResponse response;  
            response = alipayClient.execute(request);  
            if ("10000".equals(response.getCode())) {
    			json.setInfo("退款成功");
            }else {  
                json.setError(response.getSubMsg());//退款失败的提示信息  
            }
              
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return json;  
    }  
}
