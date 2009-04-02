package com.ivan.func.execute;

import java.lang.reflect.Method;

import com.ivan.func.service.IServiceCenter;
import com.ivan.func.service.ServiceCenter;


public abstract class BaseExecuter implements Executer{
	
	protected IServiceCenter serviceCenter = new ServiceCenter();
	
	public Object execute(String serviceName, String methodName,
			Object[] prameters) {
		Object instance = null;
		Object res = null;
		instance = getServiceByName(serviceName);
		
		if(instance == null) return null;

		Method[] methods = instance.getClass().getMethods();

		for (int i = 0; i < methods.length; i++) {
			//compare method name
			if (methods[i].getName().equals(methodName)) {
				try {
					Class[] params = methods[i].getParameterTypes();
					//compare param types
					if (compareArrayTypes(params, prameters)) {
						//invoke method
						res = methods[i].invoke(instance, prameters);
						
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
		return res;
	}

	public Object execute(String className, String serviceName,
			String methodName, Object[] prameters) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Object instance = null;
		Object res = null;
		instance = getService(className,serviceName,false);
		
		if(instance == null) return null;

		Method[] methods = instance.getClass().getMethods();

		for (int i = 0; i < methods.length; i++) {
			//compare method name
			if (methods[i].getName().equals(methodName)) {
				try {
					Class[] params = methods[i].getParameterTypes();
					//compare param types
					if (compareArrayTypes(params, prameters)) {
						//invoke method
						res = methods[i].invoke(instance, prameters);
						
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
		return res;
	}
	private boolean compareArrayTypes(Class[] clazz1, Object[] clazz2) {
		if(clazz1 == null && clazz2 == null)
		{
			return true;
		}
		else if((clazz1 == null && clazz2 != null && clazz2.length == 0) || (clazz1 != null && clazz1.length == 0 && clazz2 == null))
		{
			return true;
		}
		
		if (clazz1.length != clazz2.length) {
			return false;
		} else {
			if (clazz1.length == 0 && clazz2.length == 0) {
				return true;
			}
		}
		for (int i = 0; i < clazz2.length; i++) {

			try {
				if (!checkInheritOrEquals( clazz2[i].getClass(),clazz1[i])) {
					return false;
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return true;
	}

	private boolean checkInheritOrEquals(Class _aClass, Class _bClass)
			throws Exception {
		String bClassName = _bClass.getName();
		boolean flag = false;
		if (_aClass.getName().equals(bClassName)) {
			return true;
		} else {

			if (!_aClass.isInterface() && !_bClass.isInterface()) {
				Class tempClass = _aClass;
				String tempClassName = tempClass.getName();

				while (!flag && (!tempClassName.equals("java.lang.Object"))) {
					tempClass = tempClass.getSuperclass();
					tempClassName = tempClass.getName();
					if (tempClassName.equals(bClassName)) {
						flag = true;
						break;
					}
				}
			} else if (_aClass.isInterface() && _bClass.isInterface()) {
				Class[] temp = _aClass.getInterfaces();
				for (int i = 0; i < temp.length; i++) {
					if (temp[i].getName().equals(bClassName)) {
						flag = true;
						break;
					}
				}
			} else {
				System.out.println(1);
			}
			return flag;
		}
	}
	protected Object getService(String className, String serviceName, boolean create) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		return serviceCenter.getService(className, serviceName, create);
	}
	protected Object getServiceByName(String serviceName){
		return serviceCenter.getServiceByName(serviceName);
	}
	public void setServiceCenter(IServiceCenter serviceCenter)
	{
		this.serviceCenter = serviceCenter;
	}
}
