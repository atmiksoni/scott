package com.flyme.core.hcaptcha;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.flyme.common.json.util.JsonUtil;

public class ResponseUtil {
	
	private static Logger logger = Logger.getLogger(ResponseUtil.class);
	
	public static boolean sendMessageNoCache(HttpServletResponse response, String message) {
		response.setContentType("text/html;charset=GBK");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		StringBuffer sb = new StringBuffer();
		try {
			PrintWriter writer = response.getWriter();
			sb.append(message);
			writer.write(sb.toString());
			response.flushBuffer();
			return true;
		} catch (Exception e) {
			logger.error("直接推送字符串错误", e);
			return false;
		}
	}
	
	public static boolean sendJSON(HttpServletResponse response, Object obj) {
		response.setContentType("text/json;charset=GBK");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		StringBuffer sb = new StringBuffer();
		try {
			PrintWriter writer = response.getWriter();
			sb.append(JsonUtil.beanToJson(obj));
			writer.write(sb.toString());
			response.flushBuffer();
			return true;
		} catch (Exception e) {
			logger.error("直接推送JSON数据错误", e);
			return false;
		}
	}
	
	public static boolean sendJSONP(HttpServletResponse response, String callBack, Object obj) {
		response.setContentType("application/javascript;charset=GBK");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		StringBuffer sb = new StringBuffer();
		try {
			PrintWriter writer = response.getWriter();
			// sb.append(JsonUtil.toJSONP(callBack, obj));
			writer.write(sb.toString());
			response.flushBuffer();
			return true;
		} catch (Exception e) {
			logger.error("直接推送JSONP数据错误", e);
			
			return false;
		}
	}
	
	public static boolean sendImg(HttpServletResponse response, BufferedImage buffImg, String mimeType, String fileName, String extName)throws ServletException, IOException {
		OutputStream out = null;
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("attachment;filename=%s.%s", fileName, extName));
		
		out = response.getOutputStream();
		ImageIO.write(buffImg, extName, out);
		try {
			out.flush();
		} finally {
			out.close();
		}
		return true;
		
	}
	
}
