package com.ivan.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ivan.test.service.SimpleTestService;

public class TestAop {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		ApplicationContext actx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		SimpleTestService service = (SimpleTestService) actx.getBean("simpleService");
		try {
			System.out.println("testService starting...");
			service.start();
			service.stop();
			System.out.println("finish");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
