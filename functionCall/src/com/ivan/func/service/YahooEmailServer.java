package com.ivan.func.service;

import com.ivan.func.param.Parameter;


public class YahooEmailServer implements EmailServer {

	public void send(Parameter p) {
		System.out.println("Yahoo Email Server sends : " + p.getMsg());
	}
	public void yahooHello(String name)
	{
		System.out.println("Yahoo Email Server says : Hello " + name);
	}
}
