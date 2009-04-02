package com.ivan.test.func;

import junit.framework.TestCase;

import com.ivan.func.execute.SimpleExecuter;

public class IvanServiceTest extends TestCase{
	public IvanServiceTest(String fName)
	{
		super(fName);
	}
	public void testIvanService()
	{
		SimpleExecuter simpleExecuter = new SimpleExecuter();
		try {
			String result = (String)simpleExecuter.execute("com.ivan.test.func.IvanService", "ivanService", "sayHello", null);
			assertEquals(result,"Hello Ivan");
			result = (String)simpleExecuter.execute("ivanService", "sayHello", null);
			assertEquals(result,"Hello Ivan");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
