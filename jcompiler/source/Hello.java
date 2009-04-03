package com.ivan.hello.test;

import java.util.Date;

public class Hello {
	public static void main(String[] args) {
		System.out.println("Hello,now is " + new Date());
	}
	public String sayHello(String target)
	{
		return "Hello " + target;
	}
}
