package com.flyme.common.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.TransactionDefinition;

/**
 * 事务(可注解到任意层面级方法内)
 */
@Target({ElementType.METHOD})     
@Retention(RetentionPolicy.RUNTIME)     
public @interface Tx {
	/**
	 * 是否启用事务，默认启用
	 * @return
	 */
	boolean isOpen() default true;
	int isolationLevel() default TransactionDefinition.ISOLATION_DEFAULT;
}