package com.ivan.test.func;

import java.io.Serializable;

public class Parameter implements Serializable{
	private static final long serialVersionUID = 1L;
	private String msg = "";

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Parameter(String msg)
	{
		this.msg = msg;
	}
}
