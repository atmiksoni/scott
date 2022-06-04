package com.flyme.core.shiro.util;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Component;

import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.base.rbac.accountinfo.pojo.AccountInfo;
import com.flyme.common.util.ObjectUtils;
import com.flyme.core.shiro.web.Constants;

@Component
public class BaseShiroUtils {

	public BaseShiroUtils() {

	}

	/**
	 * 获取当前登录用户
	 */
	public static Account getAccount() {
		return (Account) getSession().getAttribute(Constants.SESSION_ACCOUNT);
	}

	/**
	 * 获取微信端当前登录用户
	 */
	public static Account getWxAccount() {
		return (Account) getSession().getAttribute(Constants.WEIXIN_ACCOUNT);
	}

	/**
	 * 获取微信端当前登录用户
	 */
	public static AccountInfo getWxAccountInfo() {

		return (AccountInfo) getSession().getAttribute(Constants.WEIXIN_USERINFO);
	}

	/**
	 * 获取当前登录用户
	 */
	public static AccountInfo getAccountInfo() {
		return (AccountInfo) getSession().getAttribute(Constants.SESSION_USERINFO);
	}

	/**
	 * 获取当前登录账户Id
	 */
	public static String getAccountId() {
		String accountId = null;
		Account account = ((Account) getSession().getAttribute(Constants.SESSION_ACCOUNT));
		if (ObjectUtils.isNotEmpty(account)) {
			accountId = account.getAccountId();
		}
		return accountId;
	}

	/**
	 * 获取当前登录用户
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	/**
	 * 判断当前用户是否拥有某个角色
	 */
	public static Boolean hasRole(String roleCode) {
		return SecurityUtils.getSubject().hasRole(roleCode);
	}

	/**
	 * 判断当前用户是否拥有某个角色
	 */
	public static Boolean hasRole(String... roleCodes) {
		Boolean tag = false;
		if (ObjectUtils.isNotEmpty(roleCodes)) {
			for (String roleCode : roleCodes) {
				Boolean hasrole = hasRole(roleCode);
				if (hasrole) {
					tag = hasrole;
					break;
				}
			}
		}
		return tag;
	}

	@SuppressWarnings("unchecked")
	public static List<String> getRoleCodes() {
		Session session = getSession();
		return (List<String>) session.getAttribute(Constants.SESSION_USERROLECODES);
	}

	@SuppressWarnings("unchecked")
	public static List<String> getRoleIds() {
		Session session = getSession();
		return (List<String>) session.getAttribute(Constants.SESSION_USERROLEIDS);
	}

}