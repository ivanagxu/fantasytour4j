package com.ivan.func.execute;


public class SimpleExecuter extends BaseExecuter{

	public Object execute(String serviceName, String methodName,
			Object[] prameters) {
		return super.execute(serviceName, methodName, prameters);
	}

	public Object execute(String className, String serviceName,
			String methodName, Object[] prameters) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		return super.execute(className, serviceName, methodName, prameters);
	}

}
