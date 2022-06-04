package com.flyme.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})     
@Retention(RetentionPolicy.RUNTIME)
public @interface Fk {
	String name();/*外键取值字段*/
	String fkname();/*外键对象名称*/
	boolean islog() default false;/*是否记录日志*/
}
