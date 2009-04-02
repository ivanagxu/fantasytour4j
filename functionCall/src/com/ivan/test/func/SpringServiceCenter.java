package com.ivan.test.func;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ivan.func.service.IServiceCenter;

public class SpringServiceCenter implements IServiceCenter {
	private BeanFactory beanFactory = null;
	private static class Holder {
		private static final SpringServiceCenter instance = new SpringServiceCenter();
	}
	private SpringServiceCenter() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[]{
						"applicationContext.xml"
				}
		);
		beanFactory = (BeanFactory)context;
	}
	public static SpringServiceCenter getInstance() {
		return Holder.instance;
	}

	public Object getService(String className, String serviceName,
			boolean create) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		return beanFactory.getBean(serviceName);
	}

	public Object getServiceByName(String serviceName) {
		return beanFactory.getBean(serviceName);
	}

}
