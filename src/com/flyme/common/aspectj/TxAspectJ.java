package com.flyme.common.aspectj;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.flyme.common.annotation.Tx;

/**
 * 事务控制
 */
@Aspect
@Component
public class TxAspectJ {
	@Autowired
	DataSourceTransactionManager transactionManager;
	TransactionStatus ts = null;
	Logger log = LoggerFactory.getLogger(TxAspectJ.class);
	
	/**
	 * 声明一个切入点（包括切入点表达式和切入点签名）
	 */
	@Pointcut("execution(* com..*.*(..))")
	public void txMethod() {
	}
	
	/**
	 * 开启事务
	 */
	@Before("txMethod() && @annotation(tx)")
	public void beforeAdvide(JoinPoint jp, Tx tx) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		def.setIsolationLevel(tx.isolationLevel());
		ts = transactionManager.getTransaction(def);
		log.info("-----------------事务开启----------------");
	}
	
	/**
	 * 事务回滚
	 */
	@AfterThrowing(pointcut = "txMethod() && @annotation(tx)", throwing = "throwing")
	public void rollback(JoinPoint point, RuntimeException throwing, Tx tx) {
		log.info("---------------出现异常事务回滚,异常信息:" + throwing.getMessage() + "------------------");
		transactionManager.rollback(ts);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("exceptionMessage", throwing.getMessage());
		
	}
	
	@AfterReturning("txMethod() && @annotation(tx)")
	public void commit(JoinPoint point, Tx tx) {
		transactionManager.commit(ts);
		log.info("---------------事务已提交------------------");
	}
}