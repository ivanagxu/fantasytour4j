package com.ivan.test.func;



public class YahooEmailServer implements EmailServer {

	public void send(Parameter p) {
		System.out.println("Yahoo Email Server sends : " + p.getMsg());
	}
	public void yahooHello(String name)
	{
		System.out.println("Yahoo Email Server says : Hello " + name);
	}
}
