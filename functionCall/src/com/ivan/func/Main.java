package com.ivan.func;

import java.lang.reflect.Method;

import com.ivan.func.param.Parameter;
import com.ivan.func.param.ParameterEx;
import com.ivan.func.service.ServiceCenter;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		main.start();
		System.out.println("update by ubuntu");
	}

	public void start() {
		/*
		 * configuration
		 * first to call yahoo mail sender
		 * second to call swot mail sender
		 */
		String[] serverName = new String[] { 
				"yahooEmailServer",
				"yahooEmailServer",
				"swotEmailServer",
				"swotEmailServer"
				};
		
		String[] methods = new String[]{
				"yahooHello",
				"send",
				"swotHello",
				"send"
				};
		/*
		 * add parameters for method invoke
		 */
		Object[] params = new Object[] {
				new String("swot"),
				new Parameter("yahoo mail"),
				new String("ivan"),
				new ParameterEx("swot mail") };
		/*
		 * find serverObject then find and invoke send method in serverObject
		 */
		for (int i = 0; i < serverName.length; i++) {
			Object res = execute(serverName[i], methods[i], new Object[] { params[i] });
			System.out.println("result : " + res);
		}
	}

	public Object execute(String className, String methodName, Object[] prameters) {
		Object instance = null;
		Object res = null;
		instance = getService(className,true);
		
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

	private Object getService(String name, boolean create) {
		return ServiceCenter.getInstance().getService(name,create);
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
}
