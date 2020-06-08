package com.bartek.aopdemo.aspect;

import java.util.List;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.bartek.aopdemo.Account;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {
	
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	@Around("execution(* com.bartek.aopdemo.service.*.getFortune(..))")
	public Object aroundGetFortune(
			ProceedingJoinPoint theProceedingJoinPoint) throws Throwable {
		
		String method = theProceedingJoinPoint.getSignature().toShortString();
		myLogger.info("=====>>> Executing @Around on method: " + method);
		
		long begin = System.currentTimeMillis();
		
		Object result = theProceedingJoinPoint.proceed();
		
		long end = System.currentTimeMillis();
		
		long duration = end - begin;
		myLogger.info("=====>>> Duration: " + duration / 1000.0 + "seconds"); // dividing was done to convert from miliseconds to seconds
		
		return result;
	}
	
	@After("execution(* com.bartek.aopdemo.dao.AccountDAO.findAccounts(..))")
	public void afterFinallyFindAccountsAdvice(JoinPoint theJoinPoint) {
		
		String method = theJoinPoint.getSignature().toShortString();
		myLogger.info("=====>>> Executing @After (finally) on method: " + method);
	}
	
	@AfterThrowing(
			pointcut="execution(* com.bartek.aopdemo.dao.AccountDAO.findAccounts(..))",
			throwing="theExc")
	public void afterThrowingFindAccountsAdvice(
			JoinPoint theJoinPoint, Throwable theExc) {
		
		String method = theJoinPoint.getSignature().toShortString();
		myLogger.info("=====>>> Executing @AfterThrowing on method: " + method);
		
		myLogger.info("=====>>> The exception is: " + theExc);
	}

	@AfterReturning(
			pointcut="execution(* com.bartek.aopdemo.dao.AccountDAO.findAccounts(..))",
			returning="result")
	public void afterReturningFindAccountsAdvice(
					JoinPoint theJoinPoint, List<Account> result) {
	 
		String method = theJoinPoint.getSignature().toShortString();
		myLogger.info("\n=====>>> Executing @AfterReturning on method: " + method);
		myLogger.info("\n=====>>> result is: " + result);
		
		convertAccountNamesToUpperCase(result);
		myLogger.info("\n=====>>> result is: " + result);	
	}

	private void convertAccountNamesToUpperCase(List<Account> result) {
		for (Account tempAccount : result) {
			String theUpperName = tempAccount.getName().toUpperCase();
			tempAccount.setName(theUpperName);
		}
	}
	
	@Before("com.bartek.aopdemo.aspect.LuvAopExpressions.forDaoPackageNoGetterSetter()") 
	public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {
		
		myLogger.info("\n=====>>> Executing @Before advice on method");
		
		MethodSignature methodSig = (MethodSignature) theJoinPoint.getSignature();
		
		myLogger.info("Method: " + methodSig);
		
		Object[] args = theJoinPoint.getArgs();
		
		for (Object tempArg: args) {
			myLogger.info(tempArg.toString());
			
			if (tempArg instanceof Account) {
				Account theAccount = (Account) tempArg;
				myLogger.info("account name: " + theAccount.getName());
				myLogger.info("account level: " + theAccount.getLevel());
			}
		}
	}
}
