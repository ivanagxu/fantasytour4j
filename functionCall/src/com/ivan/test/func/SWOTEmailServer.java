package com.ivan.test.func;



public class SWOTEmailServer implements EmailServer {

	public void send(Parameter p) {
		System.out.println("SWOT EMail Server sends : " + p.getMsg());
	}
	public void swotHello(String name)
	{
		System.out.println("Swot Email Server says : Hello " + name);
	}
}
