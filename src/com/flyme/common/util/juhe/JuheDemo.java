package com.flyme.common.util.juhe;

import java.util.HashMap;
import java.util.Map;

import com.flyme.common.util.http.HttpUtils;
import com.flyme.common.util.md5.MD5Util;

import net.sf.json.JSONObject;
 
/**
*流量直充调用示例代码 － 聚合数据
*在线接口文档：http://www.juhe.cn/docs/105
**/
 
public class JuheDemo {
   
    public static final String openId = "JHe937dde1cb0c0af757fb17d1ed1765fd";
    //配置您申请的KEY
    public static final String APPKEY ="aa534bae7133252d4fe351e1cdbea78a";
 
    //1.全部流量套餐列表
    public static void getRequest1(){
        String result =null;
        String url ="http://v.juhe.cn/flow/list";//请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();//请求参数
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
 
        try {
            result =HttpUtils.net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //2.检测号码支持的流量套餐
    public static String getRequest2(String phone){
        String result =null;
        String url ="http://v.juhe.cn/flow/telcheck";//请求接口地址
        String jsonString =null;
        Map<String, Object> params = new HashMap<String, Object>();//请求参数
            params.put("phone",phone);//要查询的手机号码
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
        try {
            result =HttpUtils.net(url, params, "GET");
            jsonString = JSONObject.fromObject(result).toString();
//            if(object.getInt("error_code")==0){
//                System.out.println(object.get("result"));
//            }else{
//                System.out.println(object.get("error_code")+":"+object.get("reason"));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }
 
    //3.提交流量充值
    public static String getRequest3(String phone,String pid,String orderid){
        String result =null;
        String url ="http://v.juhe.cn/flow/recharge";//请求接口地址
    	String sign = MD5Util.MD5(openId+APPKEY+phone+pid+orderid);
    	String jsonString =null;
        Map<String, Object> params = new HashMap<String, Object>();//请求参数
            params.put("phone",phone);//需要充值流量的手机号码
            params.put("pid",pid);//流量套餐ID
            params.put("orderid",orderid);//自定义订单号，8-32字母数字组合
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
            params.put("sign",sign);//校验值，md5(<b>OpenID</b>+key+phone+pid+orderid)，结果转为小写
 
        try {
            result =HttpUtils.net(url, params, "GET");
             jsonString = JSONObject.fromObject(result).toString();
//            if(object.getInt("error_code")==0){
//                System.out.println(object.get("result"));
//            }else{
//                System.out.println(object.get("error_code")+":"+object.get("reason"));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }
 
    //4.订单状态查询
    public static void getRequest4(){
        String result =null;
        String url ="http://v.juhe.cn/flow/batchquery";//请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();//请求参数
            params.put("orderid","");//用户订单号，多个以英文逗号隔开，最大支持50组
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
 
        try {
            result =HttpUtils.net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //5.充值订单列表
    public static void getRequest5(){
        String result =null;
        String url ="http://v.juhe.cn/flow/orderlist";//请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();//请求参数
            params.put("pagesize","");//每页返回条数，最大200，默认50
            params.put("page","");//页数，默认1
            params.put("phone","");//指定要查询的手机号码
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
 
        try {
            result =HttpUtils.net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    //6.运营商状态查询
    public static void getRequest6(){
        String result =null;
        String url ="http://v.juhe.cn/flow/operatorstate";//请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();//请求参数
            params.put("key",APPKEY);//应用APPKEY(应用详细页查询)
 
        try {
            result =HttpUtils.net(url, params, "GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
 
 
    public static void main(String[] args) {
    	String moblie="15993651314";
    	String pid="3";
    	String orderid="201601111421";
    	//String jsonString=getRequest2(moblie);
    	String jsonString=getRequest3(moblie,pid,orderid);
    	System.out.println(jsonString);
    }
 

}