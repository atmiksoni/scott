package com.flyme.weixin.core.cofig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *微信公众号配置
 */
@Configuration
public class WxMpConfig {
	@Value("#{wxProperties.token}")
	private String token;

	@Value("#{wxProperties.appId}")
	private String appid;

	@Value("#{wxProperties.appsecret}")
	private String appsecret;

	@Value("#{wxProperties.aeskey}")
	private String aesKey;

	public String getToken() {
		return this.token;
	}

	public String getAppid() {
		return this.appid;
	}

	public String getAppsecret() {
		return this.appsecret;
	}

	public String getAesKey() {
		return this.aesKey;
	}

}
