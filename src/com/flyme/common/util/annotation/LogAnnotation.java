package com.flyme.common.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 */
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {
	/**
	 * 操作模型
	 */
	String model() default "";
	
	/**
	 * 操作方法
	 */
	String func() default "";
	
	/**
	 * 操作描述
	 */
	String desc() default "";
	
	/**
	 * 日志类型
	 */
	LogTypeEnum logType();
}