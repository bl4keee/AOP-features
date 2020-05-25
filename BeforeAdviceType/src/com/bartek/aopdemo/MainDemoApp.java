package com.bartek.aopdemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bartek.aopdemo.dao.AccountDAO;

public class MainDemoApp {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext(DemoConfig.class);
		
		AccountDAO theAccountDAO = context.getBean("accountDAO", AccountDAO.class);
		
		theAccountDAO.addAccount();
		
		System.out.println("\n let's call it again!\n");
		
		theAccountDAO.addAccount();
		
		context.close();
	}

}
