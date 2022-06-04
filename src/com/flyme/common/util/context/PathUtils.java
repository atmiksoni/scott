package com.flyme.common.util.context;

import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.conver.ConvertUtils;
import com.flyme.common.util.file.FileUtils;
import com.thoughtworks.xstream.io.path.Path;

/**
 * @description：(各种路径获取) @author：flyme @data：2013-8-26 下午02:08:02 @version：v 1.0
 */
public class PathUtils {

	/**
	 * 获取项目绝对路径 例：C:\Tomcat6\webapps\jpsy\
	 */
	public static String getRealPath() {
		return ContextUtils.getSession().getServletContext().getRealPath("/");
	}

	public static String getRealPath(String path) {
		String string = "";
		Boolean cusTomUpload = ConvertUtils.getBoolean(SysConfigUtil.getConfigByName("CusTomUpload"));
		if (ObjectUtils.isNotEmpty(path)) {
			if (cusTomUpload) {
				FileUtils.makDirs(SysConfigUtil.getConfigByName("resourcePath"));
			} else {
				FileUtils.makDirs(ContextUtils.getSession().getServletContext().getRealPath("/") + path);
				string = ContextUtils.getSession().getServletContext().getRealPath(path) + "/";
			}
		}
		return string;
	}

	/**
	 * 获取请求全路径 例：/jpsy/admin/check.do
	 */
	public static String getRequestURI() {
		return ContextUtils.getRequest().getRequestURI();

	}

	/**
	 * 获取工程名 例：/jpsy
	 */
	public static String getContextPath() {
		return ContextUtils.getRequest().getContextPath();
	}

	/**
	 * 获取当前页面所在目录下全名称 例：/jpsy
	 */
	public static String getServletPath() {
		return ContextUtils.getRequest().getServletPath();

	}

	/**
	 * 获取user.dir
	 */
	public static String getProperty() {
		return System.getProperty("user.dir");

	}

	public static String getWebRootAbsolutePath() {
		String path = null;
		String folderPath = Path.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		if (folderPath.indexOf("WEB-INF") > 0) {
			path = folderPath.substring(0, folderPath.indexOf("WEB-INF"));
		}
		return path;
	}

	public static void main(String[] args) {

	}
}
