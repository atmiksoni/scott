package com.flyme.thirdsdk.easemob;

import com.alibaba.fastjson.JSONObject;
import com.flyme.common.util.map.CriterionMap;

public class EaseMobConstants {
	public static final String AppKey ="1180171020178353#huosiji";
	public static final String AppName = "huosiji";
	public static final String OrgName = "1180171020178353";
	public static final String ClientID = "YXA6N_bZYNzCEeeDd0c-nTAMsA";
	public static final String ClientSecret = "YXA61FH9VKYN5iTcaUk_6IjWfFl3W2Q";
	
	public static String getUrl(String url) {
		return String.format(url, EaseMobConstants.OrgName,EaseMobConstants.AppName);
	}
	
	public static JSONObject getJsonParam(CriterionMap map) {
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("grant_type", "client_credentials");
		jsonParam.put("client_id", EaseMobConstants.ClientID);
		jsonParam.put("client_secret", EaseMobConstants.ClientSecret);
		return jsonParam;
	}
}
