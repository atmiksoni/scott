package com.flyme.thirdsdk.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.ValidSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;

public class JpushSMSUtils {
	protected static final Logger LOG = LoggerFactory.getLogger(JpushSMSUtils.class);

	private static final String appkey = "db0d477e7b17968e4a04627a";
	private static final String masterSecret = "b2dc3386b436e094ca1ac498";

	public static void main(String[] args) {
		 testSendSMSCode("13673372570");
		// testSendValidSMSCode();
		// testSendVoiceSMSCode();
		/*send("18739941307","55555");*/
	}

	// https://github.com/jpush/jsms-api-java-client/blob/master/example/main/java/cn/jsms/api/JSMSExample.java

	public static void testSendSMSCode(String moblie) {
		SMSClient client = new SMSClient(masterSecret, appkey);
		SMSPayload payload = SMSPayload.newBuilder().setMobildNumber(moblie).setTempId(1).build();
		try {
			SendSMSResult res = client.sendSMSCode(payload);
			LOG.info(res.toString());
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Message: " + e.getMessage());
		}
	}

	public static void testSendValidSMSCode() {
		SMSClient client = new SMSClient(masterSecret, appkey);
		try {
			ValidSMSResult res = client.sendValidSMSCode("23956732-d63f-438b-b940-e1578cc0199f", "745141");
			LOG.info(res.toString());
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Message: " + e.getMessage());
		}
	}

	/**
	 * The default value of ttl is 60 seconds.
	 */
	public static void testSendVoiceSMSCode() {
		SMSClient client = new SMSClient(masterSecret, appkey);
		SMSPayload payload = SMSPayload.newBuilder().setMobildNumber("13800138000").setTTL(90).build();
		try {
			SendSMSResult res = client.sendVoiceSMSCode(payload);
			LOG.info(res.toString());
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Message: " + e.getMessage());
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		}
	}

	public static void send(String mobile, String code) {
		SMSClient client = new SMSClient(masterSecret, appkey);
		SMSPayload payload = SMSPayload.newBuilder().setTempId(13440).setMobildNumber(mobile).addTempPara("code", code).build();
		try {
			SendSMSResult res = client.sendTemplateSMS(payload);
			LOG.info(res.toString());
		} catch (APIRequestException e) {
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Message: " + e.getMessage());
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		}
	}

}
