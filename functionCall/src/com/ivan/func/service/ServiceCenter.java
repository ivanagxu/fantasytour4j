package com.ivan.func.service;

import java.util.HashMap;


public class ServiceCenter {
	private static ServiceCenter instance = null;
	private HashMap services = new HashMap();
	private ServiceCenter()
	{
		
	}
	public Object getService(String serviceName, boolean create)
	{
		if (serviceName.equals("yahooEmailServer"))
		{
			Object service = null;
			synchronized (services) {
				service = services.get("yahooEmailServer");
				if (service == null) {
					service = new YahooEmailServer();
					services.put("yahooEmailServer", service);
				}
			}
			if(create)
				return new YahooEmailServer();
			else
				return service;
		}
		else if(serviceName.equals("swotEmailServer"))
		{
			Object service = null;
			synchronized (services) {
				service = services.get("swotEmailServer");
				if (service == null) {
					service = new SWOTEmailServer();
					services.put("swotEmailServer", service);
				}
			}
			if(create)
				return new SWOTEmailServer();
			else
				return service;
		}
		else
		{
			return null;
		}
		
	}
	
	public static ServiceCenter getInstance()
	{
		synchronized (ServiceCenter.class) {
			if (instance == null) {
				instance = new ServiceCenter();
			}
		}
		return instance;
	}
}
