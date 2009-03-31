package com.ivan.func.param;

import java.util.Date;


public class ParameterEx extends Parameter{
	public ParameterEx(String msg)
	{
		super(msg);
	}
	
	public String getMsg()
	{
		return  super.getMsg() + " at " + new Date().toString();
	}
}
