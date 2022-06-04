package com.flyme.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MyField {
	String name();/* 字段名称 */

	String desc() default "";/* 字段描述 */

	boolean islog() default false; /*修改时是否记录修改值*/
	boolean indesc() default false;/*是否显示在日志描述内*/

	int length() default (int) 255;/* 字段长度 */
}
