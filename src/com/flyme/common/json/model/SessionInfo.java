package com.flyme.common.json.model;

import java.io.Serializable;

import com.flyme.common.util.context.ContextUtils;

/**
 * 用户Session对象
 */
public class SessionInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	public static String USER_SESSION = "user_session";//前台用户session
	public static String ADMIN_SESSION = "admin_session";//后台用户session

	/**
	 * 设置SessionInfo
	 * 
	 * @param sessionInfo
	 */
	public static void setSessionInfo(Object object,String sessionType) {
		ContextUtils.getSession().setAttribute(sessionType, object);
	}
	/**
	 * 删除SessionInfo
	 * 
	 * @param sessionInfo
	 */
	public static void removeSessionInfo(String sessionType) {
		ContextUtils.getSession().removeAttribute(sessionType);
	}

	/**
	 * 获取SessionInfo
	 */

	@SuppressWarnings("unchecked")
	public static <T> T getSessionInfo(String sessionType) {
		return (T) ContextUtils.getSession().getAttribute(sessionType);
	}
	/**
	 * 设置SessionInfo
	 * 
	 * @param sessionInfo
	 */
	public static void setAttribute(String key,Object object) {
		ContextUtils.getSession().setAttribute(key, object);
	}
}
