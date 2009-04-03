package com.ivan.game.unit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ivan.game.managers.EnemyManager;
import com.ivan.game.managers.EventManager;
import com.ivan.game.managers.ItemManager;
import com.ivan.game.managers.MapManager;
public class Event {
	public Event(File f)
	{
		try
		{
			BufferedReader in = new 
			BufferedReader(new 
					InputStreamReader(new 
							FileInputStream(f)
							));
			
			filename = in.readLine();
			eventname = in.readLine();
			eventinfo = in.readLine();
			needevent = in.readLine();
			followevent = in.readLine();
			needitem = in.readLine();
			eventtype = in.readLine();
			enemyname = in.readLine();
			itemname = in.readLine();
			targetmapname = in.readLine();
			targetx = in.readLine();
			targety = in.readLine();
			money = in.readLine();
			magic = in.readLine();
			
			in.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		if(!filename.equals(f.getName()))
		{
			abort("�¼�:"+f.getName()+"�ļ�����ƥ��");
		}
		if(!needevent.endsWith("default.dat"))
		{
			isneedevent = true;
		}
		if(!followevent.endsWith("default.dat"))
		{
			hasfollowevent = true;
		}
		if(!needitem.endsWith("default.dat"))
		{
			isneeditem = true;
		}
		if(eventtype.equals("�����¼�"))
			type = TRANS_EVENT;
		else if(eventtype.equals("ս���¼�"))
			type = BATTLE_EVENT;
		else if(eventtype.equals("��Ʒ�¼�"))
			type = ITEM_EVENT;
		else if(eventtype.equals("��Ǯ�¼�"))
			type = MONEY_EVENT;
		else if(eventtype.equals("ѧϰ�¼�"))
			type = LEARN_EVENT;
		else
		{
			abort("�¼�: " + eventname + " �����޷�ʶ��!");
		}
		
	}
	public String getName()
	{
		return eventname;
	}
	public String getFilename()
	{
		return filename;
	}
	public String getInfo()
	{
		return eventinfo;
	}
	public Event getNeedEvent(EventManager manager)
	{
		if(isneedevent)
			return manager.getEvent(needevent);
		else
			return null;
	}
	public Event getFollowEvent(EventManager manager)
	{
		if(hasfollowevent)
			return manager.getEvent(followevent);
		else
			return null;
	}
	public Item getNeedItem(ItemManager manager)
	{
		if(isneeditem)
			return manager.getItem(needitem);
		else 
			return null;
	}
	public String getNeedEvent()
	{
		return needevent;
	}
	public String getFollowEvent()
	{
		return followevent;
	}
	public String getNeedItem()
	{
		return needitem;
	}
	public String getItem()
	{
		return itemname;
	}
	public int getType()
	{
		return type;
	}
	public Enemy getEnemy(EnemyManager manager)
	{
		if(type == BATTLE_EVENT)
			return manager.getEnemy(enemyname);
		else
			abort("�¼�: " + eventname + " ����ս���¼�,�����ṩ��������!");
		return null;
	}
	public String getEnemy()
	{
		return enemyname;
	}
	public Item getItem(ItemManager manager)
	{
		if(type == ITEM_EVENT)
			return manager.getItem(itemname);
		else
			abort("�¼�: " + eventname + " ������Ʒ�¼�,�����ṩ��Ʒ����!");
		return null;
	}
	public Map getMap(MapManager manager)
	{
		if(type == TRANS_EVENT)
			return manager.getMap(targetmapname);
		else
			abort("�¼�: " + eventname + " ���Ǵ����¼�,�����ṩ��ͼ����!");
		return null;
	}
	public String getMapFileName()
	{
		return targetmapname;
	}
	public int getTargetMapX()
	{
		if(type == TRANS_EVENT)
			return Integer.parseInt(targetx);
		else
			abort("�¼�: " + eventname + " ���Ǵ����¼�,�����ṩ��ͼ��������!");
		return -1;
	}
	public int getTargetMapY()
	{
		if(type == TRANS_EVENT)
			return Integer.parseInt(targety);
		else
			abort("�¼�: " + eventname + " ���Ǵ����¼�,�����ṩ��ͼ��������!");
		return -1;
	}
	public int getMoney()
	{
		if(type == MONEY_EVENT)
			return Integer.parseInt(money);
		else
			abort("�¼�: " + eventname + " ���ǽ�Ǯ�¼�,�����ṩ��Ǯ����!");
		return -1;
	}
	public String getMagic()
	{
		return magic;
	}
	public boolean isNeedEvent()
	{
		return isneedevent;
	}
	public boolean isNeedItem()
	{
		return isneeditem;
	}
	private void abort(String s)
	{
		System.out.println(s);
		System.exit(0);
	}
	private String filename;
	private String eventname;
	private String eventinfo;
	private String needevent;
	private String followevent;
	private String needitem;
	private String eventtype;
	private String enemyname;
	private String itemname;
	private String targetmapname;
	private String targetx;
	private String targety;
	private String money;
	private String magic;
	
	private int type;
	public final static int TRANS_EVENT = 0;
	public final static int BATTLE_EVENT = 1;
	public final static int ITEM_EVENT = 2;
	public final static int MONEY_EVENT = 3;
	public final static int LEARN_EVENT = 4;
	
	private boolean isneedevent = false;
	private boolean hasfollowevent = false;
	private boolean isneeditem = false;
}
