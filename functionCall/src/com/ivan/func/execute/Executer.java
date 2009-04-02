package com.ivan.func.execute;

import com.ivan.func.service.IServiceCenter;

public interface Executer {
	public Object execute(String serviceName, String methodName, Object[] prameters);
	public Object execute(String className,String serviceName, String methodName, Object[] prameters)throws ClassNotFoundException, InstantiationException, IllegalAccessException;
	public void setServiceCenter(IServiceCenter serviceCenter);
}
