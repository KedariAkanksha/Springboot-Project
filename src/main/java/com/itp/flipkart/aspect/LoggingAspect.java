package com.itp.flipkart.aspect;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	// 1. AROUND ADVICE: Measures exactly how long a database or controller process takes
	@Around("execution(* com.itp.flipkart.controller.*.*(..)) || execution(* com.itp.flipkart.service.*.*(..))")
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
		long start = System.currentTimeMillis();
		try {
			// Lets your target method (like getAllProducts) execute
			Object result = joinPoint.proceed();
			
			long duration = System.currentTimeMillis() - start;
			logger.info("[PERFORMANCE] {} executed in {} ms", 
					joinPoint.getSignature().toShortString(), duration);
			return result;
		} catch (Exception ex) {
			logger.error("[ERROR-AROUND] Exception captured in {} : {}", 
					joinPoint.getSignature().toShortString(), ex.getMessage());
			throw ex; // Pass it along so Spring's Exception Handler can catch it
		}
	}

	// 2. BEFORE ADVICE: Tracks method entry and parameters
	@Before("execution(* com.itp.flipkart.controller.*.*(..)) || execution(* com.itp.flipkart.service.*.*(..))")
	public void loggingBeforeMethods(JoinPoint joinPoint) {
		String className = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = joinPoint.getSignature().getName();
		
		logger.info("[ENTRY] Started: {}.{}()", className, methodName);
		logger.info("[INPUT] Arguments: {}", Arrays.toString(joinPoint.getArgs()));
	}
	
	// 3. AFTER-RETURNING ADVICE: Confirms successful execution path completion
	@AfterReturning("execution(* com.itp.flipkart.controller.*.*(..)) || execution(* com.itp.flipkart.service.*.*(..))")
	public void loggingAfterMethods(JoinPoint joinPoint) {
		String className = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = joinPoint.getSignature().getName();
		
		logger.info("[SUCCESS] Completed: {}.{}()", className, methodName);
	}

	// 4. AFTER-THROWING ADVICE: Explicit alert tracking if your code hits a runtime failure
	@AfterThrowing(
		pointcut = "execution(* com.itp.flipkart.controller.*.*(..)) || execution(* com.itp.flipkart.service.*.*(..))",
		throwing = "ex"
	)
	public void loggingAfterExceptions(JoinPoint joinPoint, Exception ex) {
		String className = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = joinPoint.getSignature().getName();
		
		logger.error("[EXCEPTION-ALERT] Error in {}.{}() -> Message: {}", 
				className, methodName, ex.getMessage());
	}
}