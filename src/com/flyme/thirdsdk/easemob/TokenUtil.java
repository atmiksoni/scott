package com.flyme.thirdsdk.easemob;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.flyme.app.core.manager.TokenManager;
import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
import com.flyme.thirdsdk.easemob.models.EaseMobToken;
import com.google.gson.JsonObject;

@Component
public class TokenUtil {
	private static String GET_TOKEN = "http://a1.easemob.com/%s/%s/token";
	private static String easeMobToken="EASE_MOB_TOKEN";
	
	@Autowired
	private TokenManager tokenManager;
	private static TokenUtil tokenUtil;

	@PostConstruct
	public void init() {
		tokenUtil = this;
		tokenUtil.tokenManager = this.tokenManager;
	}

	public static EaseMobToken getTokenObj() throws IOException {
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("grant_type", "client_credentials");
		jsonParam.put("client_id", EaseMobConstants.ClientID);
		jsonParam.put("client_secret", EaseMobConstants.ClientSecret);
		JsonObject json = RESTHttpUtil.post(String.format(GET_TOKEN, EaseMobConstants.OrgName, EaseMobConstants.AppName), jsonParam, "");
		return JsonUtil.jsonToBean(json.toString(), EaseMobToken.class);
	}
	
	public static String getToken() throws IOException {
		String token = tokenUtil.tokenManager.getKey(easeMobToken);
		if(ObjectUtils.isNotEmpty(token)){
			return token;
		}
		EaseMobToken tokenObj = getTokenObj();
		tokenUtil.tokenManager.createRelationship(tokenObj.getAccess_token(), easeMobToken);
		return tokenObj.getAccess_token();
	}
	
	public static void main(String[] args) throws Exception {
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("grant_type", "client_credentials");
		jsonParam.put("client_id", EaseMobConstants.ClientID);
		jsonParam.put("client_secret", EaseMobConstants.ClientSecret);
		JsonObject json = RESTHttpUtil.post(String.format(GET_TOKEN, EaseMobConstants.OrgName, EaseMobConstants.AppName), jsonParam, "");
		System.out.println(json);
	}
}
