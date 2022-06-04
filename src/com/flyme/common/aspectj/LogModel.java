package com.flyme.common.aspectj;

import org.aspectj.lang.JoinPoint;

import com.flyme.common.json.model.ApiJson;
import com.flyme.common.util.annotation.LogAnnotation;

/**
 * 切面辅助模型类
 */
public class LogModel {
	private Object[] args;/* 参数数组 */
	private String targetClassName;/* 代理类名称 */
	private String methodName;/* 调用方法名 */
	private String logType;
	private String logDesc;
	private String remark;

	public LogModel() {

	}

	public LogModel(JoinPoint joinPoint, LogAnnotation log, ApiJson j) {
		init(joinPoint, log, j);
	}

	/**
	 * 初始化
	 */
	private void init(JoinPoint jp, LogAnnotation log, ApiJson j) {
		this.args = jp.getArgs();
		this.methodName = jp.getSignature().getName();
		this.logDesc = j.getInfo();
		this.logType = log.logType().toString();
		this.remark = j.getRemark();
	}

	public String getTargetClassName() {
		return targetClassName;
	}

	public void setTargetClassName(String targetClassName) {
		this.targetClassName = targetClassName;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getLogType() {
		return logType;
	}

	public String getLogDesc() {
		return logDesc;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
