package com.flyme.common.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flyme.base.system.log.service.ILogService;
import com.flyme.common.json.model.ApiJson;
import com.flyme.common.util.ObjectUtils;
import com.flyme.common.util.annotation.LogAnnotation;

/**
 * 日志管理
 */
@Aspect
@Component
public class LogAspectJ {
	Logger log = LoggerFactory.getLogger(LogAspectJ.class);
	private LogModel logModel;
	@Autowired
	private ILogService logService;

	/**
	 * 声明一个切入点（包括切入点表达式和切入点签名）
	 */
	@Pointcut("execution(* net..*.*(..))")
	public void anyMethod() {
	}

	/**
	 * 拦截日志
	 */
	@Before("anyMethod() && @annotation(mylog)")
	public void beforeLog(JoinPoint jp, LogAnnotation mylog) {
	}

	/**
	 * 声明一个后置通知
	 */
	@After("anyMethod() && @annotation(mylog)")
	public void afterAdvie(JoinPoint jp, LogAnnotation mylog) {
	}

	@Around("anyMethod() && @annotation(mylog)")
	public Object around(ProceedingJoinPoint point, LogAnnotation mylog) {
		Object result = null;
		try {
			result = point.proceed();
			if (result instanceof ApiJson) {
				ApiJson j = (ApiJson) result;
				if (j.success()) {
					this.logModel = new LogModel(point, mylog, j);
					if (ObjectUtils.isNotEmpty(logModel.getLogDesc())) {
						logService.insertLog(logModel);
					}
				}
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}
}