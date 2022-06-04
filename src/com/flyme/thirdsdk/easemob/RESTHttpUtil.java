package com.flyme.thirdsdk.easemob;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.flyme.common.util.ObjectUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RESTHttpUtil {
	
	@SuppressWarnings("finally")
	public static JsonObject post(String url,JSONObject jsonParam,String Authorization) throws IOException {
		JsonObject json = null;
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build(); 
		HttpPost post = new HttpPost(url);
		JsonParser jsonparer = new JsonParser();// 初始化解析json格式的对象
		StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		if(ObjectUtils.isNotEmpty(Authorization)){
			post.addHeader("Authorization","Bearer "+ Authorization);
		}
	//	entity.
		post.setEntity(entity);
		// 请求结束，返回结果
		try {
			HttpResponse res =closeableHttpClient.execute(post);
			// 如果服务器成功地返回响应
			String responseContent = null; // 响应内容
			HttpEntity httpEntity = res.getEntity();
			responseContent = EntityUtils.toString(httpEntity, "UTF-8");
			json = jsonparer.parse(responseContent).getAsJsonObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeableHttpClient.close();
			return json;
		}
	}
}
