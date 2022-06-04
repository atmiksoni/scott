package com.flyme.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})     
@Retention(RetentionPolicy.RUNTIME)
public @interface Pojo {
	String name();/*实体名称*/
	String findPojoName() default "";/*删除时调用的实体*/
	String findMethod() default "findById";/*查找实体的方法*/
	String desc() default "";/*实体描述*/
	boolean islog() default true;/*是否记录日志*/
}
