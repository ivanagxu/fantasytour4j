package com.ivan.test.func;

import java.util.Date;


public class ParameterEx extends Parameter{
	private static final long serialVersionUID = 1L;

	public ParameterEx(String msg)
	{
		super(msg);
	}
	
	public String getMsg()
	{
		return  super.getMsg() + " at " + new Date().toString();
	}
}
