package com.ivan.test.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.ivan.test.logger.Logger;

/**
 * @author Ivan
 */
@Aspect
public class LogAspect {
	/**
	 * logger
	 * 
	 */
	private Logger logger = new Logger();

	@Pointcut("execution(public * *(..))")
	public void method1() {
	}

	@Pointcut("within(com.ivan.test.service.SimpleTestService)")
	public void method2() {
	}

	/**
	 * 
	 * @param jp
	 * @param ex
	 */
	@AfterThrowing(pointcut = "method1()", throwing = "ex")
	public void doExceptionActions(JoinPoint jp, Throwable ex) {
		//System.out.println(1);
		String aa = jp.getSignature().getName();
		logger.entry_error(" run " + aa + " Throw...." + ex);
	}

	/**
	 * 
	 * @param p
	 * @return
	 * @throws Throwable
	 */
	@Around("method2()")
	public Object doTimerActions(ProceedingJoinPoint p) throws Throwable {
		//System.out.println(2);
		long procTime = System.currentTimeMillis();
		logger.entry_info(" run Starting "
				+ p.getSignature().getName() + " method");
		try {
			Object result = p.proceed();
			return result;
		}
		finally {
			procTime = System.currentTimeMillis() - procTime;
			logger.entry_info(" run "
					+ p.getSignature().getName() + " method end");
			logger.entry_info("run " + p.getSignature().getName()
					+ " and run method in " + procTime + "ms");
		}
	}
}