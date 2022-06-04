package com.flyme.weixin.core.cofig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;

/**
 * 微信公众号支付相关配置.
 */
@Configuration
public class WechatPayConfig {
	@Value("#{wxProperties.appId}")
	private String appId;

	@Value("#{wxProperties.mchId}")
	private String mchId;

	@Value("#{wxProperties.mchKey}")
	private String mchKey;

	@Value("#{wxProperties.subAppId}")
	private String subAppId;

	@Value("#{wxProperties.subMchId}")
	private String subMchId;

	@Value("#{wxProperties.keyPath}")
	private String keyPath;

	@Bean
	public WxPayConfig payConfig() {
		WxPayConfig payConfig = new WxPayConfig();
		payConfig.setAppId(this.appId);
		payConfig.setMchId(this.mchId);
		payConfig.setMchKey(this.mchKey);
		payConfig.setSubAppId(this.subAppId);
		payConfig.setSubMchId(this.subMchId);
		payConfig.setKeyPath(this.keyPath);
		return payConfig;
	}

	@Bean
	public WxPayService wxPayService() {
		WxPayService wxPayService = new WxPayServiceImpl();
		wxPayService.setConfig(payConfig());
		return wxPayService;
	}
}