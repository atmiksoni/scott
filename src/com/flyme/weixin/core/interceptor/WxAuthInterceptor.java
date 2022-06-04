package com.flyme.weixin.core.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.flyme.base.rbac.account.pojo.Account;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.context.SysConfigUtil;
import com.flyme.common.util.conver.ConvertUtils;
import com.flyme.core.shiro.util.SessionUtil;
import com.flyme.core.shiro.web.Constants;
import com.flyme.core.springmvc.authority.WxAuth;
import com.flyme.weixin.core.service.WeixinService;

@Component
public class WxAuthInterceptor extends HandlerInterceptorAdapter {
	static Log log = LogFactory.getLog(WxAuthInterceptor.class);
	@Autowired
	private WeixinService wxService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();
		System.out.println("拦截器" + path);
		if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			return true;
		}
		WxAuth authPassport = ((HandlerMethod) handler).getMethodAnnotation(WxAuth.class);
		if (ObjectUtils.isEmpty(authPassport)) {
			return true;
		}
		Account account = SessionUtil.get(Constants.WEIXIN_ACCOUNT);
		if (ObjectUtils.isEmpty(account)) {
			Boolean isServer = SysConfigUtil.isServer();
			String param=request.getQueryString();
			String url = SysConfigUtil.getServerPath() + "/weixin/author.wx?servletPath=" + path;
			if(ObjectUtils.isNotEmpty(param)){
				url+="&param=" + ConvertUtils.string2Unicode(param);
			}
			System.out.println("WxAuthInterceptor:url--" + url);
			if (isServer) {
				url = wxService.oauth2buildAuthorizationUrl(url, "snsapi_base", "test");
			}
			response.sendRedirect(url);
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		super.postHandle(request, response, handler, modelAndView);
	}

}
