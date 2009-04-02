package com.ivan.func.service;

public interface IServiceCenter {
	public Object getServiceByName(String serviceName);
	public Object getService(String className, String serviceName, boolean create) throws ClassNotFoundException, InstantiationException, IllegalAccessException;
}
