package com.ivan.test.func;

import com.ivan.func.execute.SimpleExecuter;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		main.start();
		System.out.println("update by Ivan");
	}

	public void start() {
		/*
		 * configuration
		 * first to call yahoo mail sender
		 * second to call swot mail sender
		 */
		String[] classes = new String[]{
				"com.ivan.test.func.YahooEmailServer",
				"com.ivan.test.func.YahooEmailServer",
				"com.ivan.test.func.SWOTEmailServer",
				"com.ivan.test.func.SWOTEmailServer"
		};
		
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
		
		SimpleExecuter simpleExecuter = new SimpleExecuter();
		for (int i = 0; i < serverName.length; i++) {
			Object res = null;
			try {
				res = simpleExecuter.execute(classes[i],serverName[i], methods[i], new Object[] { params[i] });
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if(res != null)
				System.out.println("result : " + res);
		}
	}
}
