package com.flyme.thirdsdk.zhima;

import java.util.List;

import com.antgroup.zmxy.openplatform.api.DefaultZhimaClient;
import com.antgroup.zmxy.openplatform.api.ZhimaApiException;
import com.antgroup.zmxy.openplatform.api.domain.IvsDetail;
import com.antgroup.zmxy.openplatform.api.request.ZhimaCreditIvsDetailGetRequest;
import com.antgroup.zmxy.openplatform.api.response.ZhimaCreditIvsDetailGetResponse;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.date.DateUtil;

public class ZhimaUtil {
	// 芝麻开放平台地址
	private static final String gatewayUrl = "https://zmopenapi.zmxy.com.cn/openapi.do";
	// 商户应用 Id
	private static final String appId = "300001931";
	// 商户 RSA 私钥
	private static final String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMLG/Lf9qqXEPsEE5JWaWRHN3X5nhJcpckPXfjsKn2WzJa8gKvWPuepbbF03MVBBWFVtpKeox7ELbOlM554LNZmw50dp8z6erct4kYyJY9/fgASOqBuZbY5VeMurfrLdq+a9xqE2lXwKqn3nokeLkbvWlJtoRgfBbt1hKrwOEHSZAgMBAAECgYEAmfQgoul87aONGpHcf6dNSbZXoWb/NWAMMjVtUAuYbBFK8v5RFmGxSN1UbOWIM/BCetu8Ls1IcdcfEdS4qoIfrWWmYd5bA3ieCQdy01NMug312aKKZn+O9w5LEECwATddt4FxuFGJy+Gb1z8PWrE12Y9AjkzTjDH+maNdIXbzhYECQQD2Pxe7WwnQDm48RDv9rDETZBZXm23WvkHSCWxIMMOuCQw8ZDUaN+xqDiyANMBWpr0F2hu4q1ENxi+jkd4InC1xAkEAyn4A+gAA9SSP/MYBCxobECGQ/08Dz4ROM8oTP3a+6TA8Dzpz4h5dodj0+DNRU4u0f0ixIPCTYPNl/3hVwqdFqQJAf5ADnTz2Al/XX0UBxtGSwrbQlCWiauI4erQC8grA88EgI5DNqX1a7T3Wc7vf/4N03Dk2rx2gaYBHYXbJDLDLIQJAVIdxJOuneq4+pUiK2vWU4pEAlHOJpbEXbCspCc8qNvH5wGmoQnZmo8+Lw6i4hGXHfXL87kE/ZLN2msTDGDT3sQJAbZ7QMlZITNQTx1QnbSJGJgLt+wGkmkGu8RZcLf7MTMIh/e/ddsEguPMA22QrVj7VnMsqQIzpt31AnJyObQPQTw==";
	// 芝麻 RSA 公钥
	private static final String zhimaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCRAj5uIIZOydrQQMu9ozCJkV1fzul7LUcynJ8PwLBmUo8rrNIn9cDYWI3/B/uzpTmU9I6h/6ek4XMvPe7ir3O3yqPC0lrKhOYu0HpapPLMX5n+1zPyP8nbNmT1v16Rl6S56/MfvMQNmeUm94AyTb9FqcXR/+Y9Vp9tgvH89jHMMQIDAQAB";

	private static ZhimaCreditIvsDetailGetRequest getRequest() {
		ZhimaCreditIvsDetailGetRequest req = new ZhimaCreditIvsDetailGetRequest();
		req.setPlatform("zmop");
		req.setProductCode("20180201w1010103000216226891");// 必要参数
		req.setTransactionId(DateUtil.getLangDate());// 必要参数
		return req;
	}

	private static DefaultZhimaClient getClient() {
		DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
		return client;
	}

	/** 校验身份证 */
	public static Boolean checkCardNo(String userName, String cardNo) {
		Boolean success = false;
		ZhimaCreditIvsDetailGetRequest req = getRequest();
		req.setCertNo(cardNo);//
		req.setCertType("100");//
		req.setName(userName);//
		DefaultZhimaClient client = getClient();
		try {
			ZhimaCreditIvsDetailGetResponse response = client.execute(req);
			List<IvsDetail> ivsDetails = response.getIvsDetail();
			Boolean checkName = true;
			Boolean checkNo = true;
			if (ObjectUtils.isNotEmpty(ivsDetails)) {
				for (IvsDetail ivsDetail : ivsDetails) {

					String code = ivsDetail.getCode();
					System.out.println(code);
					if (ObjectUtils.contains(ZhiMaCode.NAME_ERROR, code)) {
						checkName = false;
					}
					if (ObjectUtils.contains(ZhiMaCode.CERTNO_ERROR, code)) {
						checkNo = false;
					}
				}
			}
			if (checkName && checkNo) {
				success = true;
			}
		} catch (ZhimaApiException e) {
			e.printStackTrace();
		}
		return success;
	}

	/** 校验银行卡 */
	public static Boolean checkBankNo(String userName, String cardNo, String bankNo) {
		Boolean success = false;
		ZhimaCreditIvsDetailGetRequest req = new ZhimaCreditIvsDetailGetRequest();
		req.setPlatform("zmop");
		req.setProductCode("w1010100000000000103");// 必要参数
		req.setTransactionId(DateUtil.getLangDate());// 必要参数
		req.setCertNo(cardNo);//
		req.setBankCard(bankNo);//
		req.setCertType("100");//
		req.setName(userName);//
		DefaultZhimaClient client = new DefaultZhimaClient(gatewayUrl, appId, privateKey, zhimaPublicKey);
		try {
			ZhimaCreditIvsDetailGetResponse response = client.execute(req);
			List<IvsDetail> ivsDetails = response.getIvsDetail();
			Boolean checkName = true;
			Boolean checkNo = true;
			Boolean checkBankNo = true;
			if (ObjectUtils.isNotEmpty(ivsDetails)) {
				for (IvsDetail ivsDetail : ivsDetails) {
					String code = ivsDetail.getCode();
					System.out.println(code);
					if (ObjectUtils.contains(ZhiMaCode.NAME_ERROR, code)) {
						checkName = false;
					}
					if (ObjectUtils.contains(ZhiMaCode.CERTNO_ERROR, code)) {
						checkNo = false;
					}
					if (ObjectUtils.contains(ZhiMaCode.BANDCARD_ERROR, code)) {
						checkBankNo = false;
					}
				}
			}
			if (checkName && checkNo && checkBankNo) {
				success = true;
			}
		} catch (ZhimaApiException e) {
			e.printStackTrace();
		}
		return success;
	}

	public static void main(String[] args) {
		System.out.println(checkCardNo("亢兰", "410327199309103542"));
		System.out.println(checkBankNo("田欢", "410782198910160931", "6236681420503913027"));
	}
}