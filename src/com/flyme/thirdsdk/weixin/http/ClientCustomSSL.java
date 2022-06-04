/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package com.flyme.thirdsdk.weixin.http;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.flyme.thirdsdk.weixin.model.PayRefund;
import com.flyme.thirdsdk.weixin.util.WxPayUtil;


/**
 * 微信退款证书
 */
public class ClientCustomSSL {

	public static String doRefund(String url, String data) throws Exception {
		PayRefund payRefund=null;
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File("E:/apiclient_cert.p12"));// P12文件目录
		try {
			keyStore.load(instream, WxPayUtil.mch_id.toCharArray());//默认是你的MCHID
		} finally {
			instream.close();
		}
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WxPayUtil.mch_id.toCharArray())// 默认是你的MCHID
				.build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			HttpPost httpost = new HttpPost(url); // 设置响应头信息
			httpost.addHeader("Connection", "keep-alive");
			httpost.addHeader("Accept", "*/*");
			httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			httpost.addHeader("Host", "api.mch.weixin.qq.com");
			httpost.addHeader("X-Requested-With", "XMLHttpRequest");
			httpost.addHeader("Cache-Control", "max-age=0");
			httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
			httpost.setEntity(new StringEntity(data, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httpost);
			try {
				HttpEntity entity = response.getEntity();
				SAXReader saxReader = new SAXReader();  
				Document document = saxReader.read(entity.getContent());  
                Element rootElt = document.getRootElement();  
                String returnCode = rootElt.elementText("return_code"); 
                payRefund=new PayRefund();
                if(returnCode.equals("SUCCESS")){  
                	String result_code = rootElt.elementText("result_code"); 
                	if(result_code.equals("SUCCESS"))
                	{
                		payRefund.setRefund_id(rootElt.elementText("refund_id"));
                		payRefund.setOut_refund_no(rootElt.elementText("out_refund_no"));
                	}
                	else {
						
					}
                }
				String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
				EntityUtils.consume(entity);
				return jsonStr;
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}

}
