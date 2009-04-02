package com.ivan.func.service;

import java.util.HashMap;


public class ServiceCenter {
	private HashMap<String,Object> services = new HashMap<String,Object>();
	
	public Object getServiceByName(String serviceName)
	{
		Object service = null;
		service = services.get(serviceName);
		return service;
	}
	
	public Object getService(String className, String serviceName, boolean create) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		Object service = null;
		synchronized (services) {
			service = services.get(serviceName);
			if (service == null) {
				Class.forName(className);
				service = Class.forName(className).newInstance();
				services.put(serviceName, service);
			}
		}
		if(create)
			return service = Class.forName(className).newInstance();
		else
			return service;
	}
}
