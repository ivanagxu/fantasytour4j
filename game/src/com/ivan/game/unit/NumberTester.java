package com.ivan.game.unit;
/*
 * 检测字符串是否只有数字的类
 */
public class NumberTester {
	public NumberTester(String s)
	{
		data = s;
	}
	public NumberTester()
	{
		data = "";
	}
	public boolean test(String s)
	{
		if(s.length() == 0)
			return false;
		for(int i = 0; i < s.length(); i++)
		{
			if(s.charAt(i)< '0' || s.charAt(i) > '9')
				return false;
		}
		return true;
	}
	public boolean test()
	{
		if(data.length() == 0)
			return false;
		for(int i = 0; i < data.length(); i++)
		{
			if(data.charAt(i) < '0' || data.charAt(i) > '9')
				return false;
		}
		return true;
	}
	
	public int getInt(String s)
	{
		if(test(s))
			return Integer.parseInt(s);
		else
			return 0;
	}
	private String data;
	
}
