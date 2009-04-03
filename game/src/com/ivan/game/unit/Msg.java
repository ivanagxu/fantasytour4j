package com.ivan.game.unit;

public class Msg {
	public Msg(String s)
	{
		msg = s + " ";
		if(msg != null && msg.length() != 0)
			hasMsg = true;
	}
	public String getLine()
	{
		if(hasMsg)
		{
			if(msg.length() > 20)
			{
				String s = msg.substring(0,20);
				msg = msg.substring(20);
				return s;
			}
			else
			{
				hasMsg = false;
				return msg;
			}
		}
		else
		{
			return null;
		}
	}
	private String msg;
	private boolean hasMsg = false;
}
