package com.flyme.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.flyme.common.json.util.JsonUtil;
import com.flyme.common.util.ObjectUtils;
@Component
public class MySimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			if (ObjectUtils.isAjaxRequest(request)) {
				ErrorInfo errorInfo= new ErrorInfo(ex);
				JsonUtil.writeObj(response, errorInfo);
				return null;
			} else {// JSON格式返回
				// 如果不是异步请求
				Integer statusCode = determineStatusCode(request, viewName);
				if (statusCode != null) {
					applyStatusCodeIfPossible(request, response, statusCode);
				}
				ex.printStackTrace();
				return new ModelAndView("../common/500");

			}
		} else {
			ErrorInfo errorInfo= new ErrorInfo(ex);
			JsonUtil.writeObj(response, errorInfo);
			return null;
		}
	}
}
