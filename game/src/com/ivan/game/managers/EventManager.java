package com.ivan.game.managers;

import java.io.File;
import java.util.ArrayList;

import com.ivan.game.unit.Event;

public class EventManager {
	public EventManager()
	{
		track("���������¼�������...");
		eventlist = new ArrayList();
		eventnamelist = new ArrayList();
		
		File f = new File("data/event/");
		File[] filelist = f.listFiles();
		/*
		 * read all the Event files from data\event\ directory
		 */
		for(int i = 0; i < filelist.length; i++)
		{
			if(filelist[i].getName().endsWith(".dat"))
			{
				track("����ȡ���¼��ļ�: " + filelist[i].getName());
				eventlist.add(new Event(filelist[i]));
				eventnamelist.add(filelist[i].getName());
			}
		}
		if(eventlist.size() == 0 || eventnamelist.size() == 0 || eventlist.size() != eventnamelist.size())
		{
			abort("�¼�������û�ж�ȡ���κ��¼�,�޷�������Ϸ!");
		}
		track("�¼��������������\n");
	}
	public Event getEvent(String eventname)
	{
		if(eventname.endsWith("default.dat"))
			return null;
		int n = eventname.lastIndexOf('/') + 1;
		Event event = null;
		for(int i = 0; i < eventlist.size(); i++)
		{
			event = (Event)eventlist.get(i);
			if(event.getFilename().equals(eventname.substring(n)))
			{
				return event;
			}
		}
		return null;
	}
	public void setMsgMgr(MessageManager m)
	{
		msgmgr = m;
	}
	private void abort(String s)
	{
		System.out.println(s);
		System.exit(0);
	}
	private void track(String s)
	{
		System.out.println(s);
	}
	private ArrayList eventlist;
	private ArrayList eventnamelist;
	private MessageManager msgmgr;
}
