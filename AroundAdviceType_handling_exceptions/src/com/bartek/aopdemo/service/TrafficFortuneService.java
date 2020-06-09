package com.bartek.aopdemo.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class TrafficFortuneService {

	public String getFortune() {
		
	try {
		TimeUnit.SECONDS.sleep(5); // code will sleep for 5 seconds
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	
	return "Expect heavy traffic this morning";
}

	public String getFortune(boolean tripWire) {
		
		if (tripWire) {
			throw new RuntimeException("Major accident! Highway is closed!");
		}
		return getFortune();
	}
	
}
