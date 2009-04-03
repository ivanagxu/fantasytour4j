package com.ivan.game.managers;

import com.ivan.game.unit.Hero;

public class PlayerManager {
	public PlayerManager()
	{
		
	}
	public Hero newPlayer()
	{
		return new Hero("");
	}
	public void setMsgMgr(MessageManager m)
	{
		msgmgr = m;
	}
	private MessageManager msgmgr;
}
