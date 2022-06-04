package com.flyme.thirdsdk.easemob;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;

public class UserUtil {
	private static String CREATE_USER = "http://a1.easemob.com/%s/%s/users";
	private static String GET_USER = "http://a1.easemob.com/%s/%s/users/%s";
	private static String UPDATE_USER = "http://a1.easemob.com/%s/%s/users/%s";

	/***
	 * 注册用户
	 * @param username 用户名
	 * @param password 密码
	 * @param nickName 昵称
	 * @return
	 * @throws IOException
	 */
	public static JsonObject createUser(String username, String password, String nickName) throws IOException {
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("username", username);
		jsonParam.put("password", password);
		jsonParam.put("nickname", nickName);
		JsonObject json = RESTHttpUtil.post(String.format(CREATE_USER, EaseMobConstants.OrgName, EaseMobConstants.AppName), jsonParam,"");
		return json;
	}
	
	/***
	 * 获取用户信息
	 * @param username
	 * @param password
	 * @param nickName
	 * @return
	 * @throws IOException
	 */
	public static JsonObject getUser(String username) throws IOException {
		String token = TokenUtil.getToken();
		JSONObject jsonParam = new JSONObject();
		JsonObject json = RESTHttpUtil.post(String.format(GET_USER, EaseMobConstants.OrgName, EaseMobConstants.AppName,username), jsonParam,token);
		return json;
	}
	
	/***
	 *  修改昵称
	 * @param userName
	 * @param nickname
	 * @return
	 * @throws IOException
	 */
	public static JsonObject updateUser(String userName,String nickname) throws IOException {
		String token = TokenUtil.getToken();
		JSONObject jsonParam = new JSONObject();
		jsonParam.put("nickname", nickname);
		JsonObject json = RESTHttpUtil.post(String.format(UPDATE_USER, EaseMobConstants.OrgName, EaseMobConstants.AppName,userName), jsonParam,token);
		return json;
	}
	
	public static void main(String[] args) throws IOException {
		//JsonObject json = createUser("3", "3", "3");
		//JsonObject token = TokenUtil.getToken();
		JsonObject json = getUser("50C4E041FA844051B13BF1F74434A3E6");
		//JsonObject json = updateUser("50C4E041FA844051B13BF1F74434A3E6", "福昌");
		System.out.println(json);
	}
}
