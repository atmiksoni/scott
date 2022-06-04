package com.flyme.common.util.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Consts;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlRecive {
	// 接收请求发来的xml消息体
	public static String recieveData(HttpServletRequest request) {
		String inputLine = null;
		// 接收到的数据
		StringBuffer recieveData = new StringBuffer();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			while ((inputLine = in.readLine()) != null) {
				recieveData.append(inputLine);
			}
		} catch (IOException e) {
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
			}
		}

		return recieveData.toString();
	}

	// 根据参数发送第三服务接口查询，由第三方返回xml
	public String SendAndGetXml(String domain) throws Exception {
		String rqestXml = "";
		String urls = "";
		try {
			String appserverIp = "10.166.46.182";
			String appserverPort = "8080";
			// 发送xml消息体
			rqestXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><root><domain>" + domain + "</domain></root>";
			// 访问远程接接口
			urls = "http://" + appserverIp + ":" + appserverPort + "/ICBCCattestationM/validate";

		} catch (Exception e) {
			throw new Exception();
		}
		// 使用HttpURLConnection发送http请求
		byte[] xmlbyte = rqestXml.getBytes("UTF-8");
		URL url = new URL(urls);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5 * 1000);
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false);// 不使用Cache
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("Content-Length", String.valueOf

		(xmlbyte.length));
		conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
		DataOutputStream outStream = new DataOutputStream(conn.getOutputStream

		());
		outStream.write(xmlbyte);// 发送xml数据
		outStream.flush();
		outStream.close();

		// 解析返回来的xml消息体
		byte[] msgBody = null;

		if (conn.getResponseCode() != 200)
			throw new RuntimeException("请求url失败");
		InputStream is = conn.getInputStream();// 获取返回数据

		byte[] temp = new byte[1024];
		int n = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while ((n = is.read(temp)) != -1) {
			bos.write(temp, 0, n);
		}
		msgBody = bos.toByteArray();
		bos.close();
		is.close();
		String returnXml = new String(msgBody, "UTF-8").trim();

		conn.disconnect();

		// 以下下游解析XML
		System.out.println(returnXml);
		return returnXml;
	}

	// 根据参数进行第三服务接口查询，由第三方返回xml
	public String getAccount(String domain) {
		String url = "http://10.166.106.107:8080/authServer/servlet/QueryUniformTellerInfo";
		URL sendUrl = null;
		HttpURLConnection con = null;
		StringBuffer retStr = new StringBuffer();
		InputStream in = null;
		BufferedReader bufferedReader = null;
		String enc = "gbk";
		String info = domain;
		String flag = "1";
		try {
			url += "?queryEnc=" + enc + "&queryInfo=" + URLEncoder.encode(info) + "&queryFlag=" + flag;
			sendUrl = new URL(url);
			con = (HttpURLConnection) sendUrl.openConnection();
			if (con.getResponseCode() == 200) {
				in = con.getInputStream();
				bufferedReader = new BufferedReader(new InputStreamReader(in));
				String temp = bufferedReader.readLine();
				while (temp != null) {
					retStr.append(temp);
					temp = bufferedReader.readLine();
				}
				bufferedReader.close();
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 异常处理
		} finally {
			try {
				bufferedReader.close();
				in.close();
			} catch (Exception e2) {
				// 异常处理
				e2.printStackTrace();
			}
			con.disconnect();
		}
		String xmlStr = retStr.toString();
		// 以下下游解析XML
		return xmlStr;
	}

	// 组合xml返回给请求者
	public static void sendCompressDate(HttpServletResponse response, String account, String retCode) {
		BufferedWriter out = null;
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"GBK\" ?>");
		sb.append("<result>");
		sb.append("<retCode>" + retCode + "</retCode>");
		if (!"ValidateServlet".equals(account))

		{
			sb.append("<ucAccount>" + account + "</ucAccount>");
		}
		sb.append("</result>");
		try {
			System.out.println("Message,data[" + sb.toString() + "]");
			out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
			out.write(sb.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}
			} catch (IOException e) {
				out = null;
			}
		}
	}

	// 组合xml返回给请求者
	public static void sendXml(HttpServletResponse response, String account, String retCode) {
		BufferedWriter out = null;
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF8\"?><ContractRoot><SUCCESS>true</SUCCESS><NOTIFY_CODE>000000</NOTIFY_CODE><RESULTMSG>调用成功</RESULTMSG></ContractRoot>");
		try {
			out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
			out.write(sb.toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}
			} catch (IOException e) {
				out = null;
			}
		}
	}

	public static void get(HttpServletRequest request) throws IOException {
		System.out.println("------调用servlet -----");
		InputStream in = request.getInputStream();
		SAXReader saxReader = new SAXReader();
		InputStreamReader strInStream = new InputStreamReader(in, "UTF-8");
		try {
			Document document = saxReader.read(strInStream);
			Element root = document.getRootElement(); // FILELIST
			System.out.println("1、" + root.getName());
		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	public static void get2(HttpServletRequest request) throws IOException {
		int len;
		byte[] b = new byte[1024];
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		InputStream servletInputStream = request.getInputStream();
		while ((len = servletInputStream.read(b)) != -1) {
			stream.write(b, 0, len);
		}
		String postResult = stream.toString(Consts.UTF_8.name());
		System.out.println(postResult);
	}
}
