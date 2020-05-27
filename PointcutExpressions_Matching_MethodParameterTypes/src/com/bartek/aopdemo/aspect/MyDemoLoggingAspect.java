package com.bartek.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyDemoLoggingAspect {

	@Before("execution(* com.bartek.aopdemo.dao.*.*(..))") 
	
	/*
	 any return type *
	 specific package com.bartek.aopdemo.dao
	 any class *
	 any method *
	 any number of parameters (..)
	 */
	
	public void beforeAddAccountAdvice() {
		
		System.out.println("\n=====>>> Executing @Before advice on method");
	}
}
