package com.ivan.game.unit;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.ivan.game.managers.EventManager;
import com.ivan.game.managers.ItemManager;


public class Npc {
	public Npc(File f)
	{
		try
		{
			imagename = new String[4];
			images = new Image[4];
			sellitemlist = new ArrayList();
			BufferedReader in = new 
			BufferedReader(new 
					InputStreamReader(new 
							FileInputStream(f)
							,"GBK"));
			
			name = in.readLine();
			npctype = in.readLine();
			msg = in.readLine();
			lmsg = in.readLine();
			event = in.readLine();
			imagename[0] = in.readLine();
			imagename[1] = in.readLine();
			imagename[2] = in.readLine();
			imagename[3] = in.readLine();
			leaveable = in.readLine();
			leavemsg = in.readLine();
			leaveitem = in.readLine();
			leaveevent = in.readLine();
			String temp = in.readLine();
			while(!temp.equals("#"))
			{
				sellitemlist.add(temp);
				temp = in.readLine();
			}
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
		if(npctype.equals("信息"))
			type = MESSAGE_NPC;
		else if(npctype.equals("治疗"))
			type = HEAL_NPC;
		else if(npctype.equals("出售"))
			type = SELL_NPC;
		else
		{
			abort("NPC: " + name + " 的类型无法识别!");
		}
		if(!event.endsWith("default.dat"))
			hasevent = true;
		else hasevent = false;
		
		for(int i = 0; i < 4; i++)
		{
			images[i] = new ImageIcon(imagename[i]).getImage();
			if(images[i] == null)
				abort("NPC: " + name + " 贴图丢失!");
		}
		
		if(leaveable.equals("是"))
			isleaveable = true;
		else
			isleaveable = false;
		
		if(!leaveitem.endsWith("default.dat"))
			isneeditemtoleave = true;
		else
			isneeditemtoleave = false;
		
		if(!leaveevent.endsWith("default.dat"))
			isneedeventtoleave = true;
		else
			isneedeventtoleave = false;
	}
	public Npc(File f,ItemManager itmmgr)
	{
		try
		{
			imagename = new String[4];
			images = new Image[4];
			ArrayList itemlist = new ArrayList();
			sellitemlist = new ArrayList();
			BufferedReader in = new 
			BufferedReader(new 
					InputStreamReader(new 
							FileInputStream(f)
							,"GBK"));
			
			name = in.readLine();
			npctype = in.readLine();
			msg = in.readLine();
			lmsg = in.readLine();
			event = in.readLine();
			imagename[0] = in.readLine();
			imagename[1] = in.readLine();
			imagename[2] = in.readLine();
			imagename[3] = in.readLine();
			leaveable = in.readLine();
			leavemsg = in.readLine();
			leaveitem = in.readLine();
			leaveevent = in.readLine();
			String temp = in.readLine();
			while(!temp.equals("#"))
			{
				itemlist.add(temp);
				temp = in.readLine();
			}
			in.close();
			
			for(int i = 0; i < itemlist.size(); i++)
			{
				//System.out.println((String)itemlist.get(i));
				//System.out.println(itmmgr == null);
				sellitemlist.add(itmmgr.getItem((String)itemlist.get(i)));
			}
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		if(npctype.equals("信息"))
			type = MESSAGE_NPC;
		else if(npctype.equals("治疗"))
			type = HEAL_NPC;
		else if(npctype.equals("出售"))
			type = SELL_NPC;
		else
		{
			abort("NPC: " + name + " 的类型无法识别!");
		}
		if(!event.endsWith("default.dat"))
			hasevent = true;
		else hasevent = false;
		
		for(int i = 0; i < 4; i++)
		{
			images[i] = new ImageIcon(imagename[i]).getImage();
			if(images[i] == null)
				abort("NPC: " + name + " 贴图丢失!");
		}
		
		if(leaveable.equals("是"))
			isleaveable = true;
		else
			isleaveable = false;
		
		if(!leaveitem.endsWith("default.dat"))
			isneeditemtoleave = true;
		else
			isneeditemtoleave = false;
		
		if(!leaveevent.endsWith("default.dat"))
			isneedeventtoleave = true;
		else
			isneedeventtoleave = false;
	}

	public String getName()
	{
		return name;
	}
	public int getType()
	{
		return type;
	}
	public String getMsg()
	{
		if(hastalked)
			return lmsg;
		else
		{
			hastalked = true;
			return msg;
		}
	}
	public void talk(Hero hero)
	{
		if(hero.getFace() == 0)
			face = 1;
		else if(hero.getFace() == 1)
			face =0;
		else if(hero.getFace() == 2)
			face = 3;
		else if(hero.getFace() == 3)
			face = 2;
	}
	public Image getCurrentImage()
	{
		return images[face];
	}
	public Event getEvent(EventManager manager)
	{
		if(hasevent)
			return manager.getEvent(event);
		else
			return null;
	}
	public Image[] getImages()
	{
		return images;
	}
	public String[] getImagesName()
	{
		return imagename;
	}
	public boolean isleaveable()
	{
		return isleaveable;
	}
	public void leave()
	{
		isleave = true;
	}
	public String getLeaveMsg()
	{
		return leavemsg;
	}
	public boolean hasEvent()
	{
		return hasevent;
	}
	public Item getLeaveItem(ItemManager manager)
	{
		if(isneeditemtoleave)
			return manager.getItem(leaveitem);
		else
			return null;
	}
	public String getLeaveItem()
	{
		return leaveitem;
	}
	public Event getLeaveEvent(EventManager manager)
	{
		if(isneedeventtoleave)
			return manager.getEvent(leaveevent);
		else
			return null;
	}
	public String getLeaveEvent()
	{
		return leaveevent;
	}
	public String getEvent()
	{
		return event;
	}
	public ArrayList getSellItemList()
	{
		if(type == SELL_NPC)
			return sellitemlist;
		else
			return null;
	}
	public boolean hasTalked()
	{
		return hastalked;
	}
	public boolean IsNeedItemToLeave()
	{
		return isneeditemtoleave;
	}
	public boolean IsNeedEventToLeave()
	{
		return isneedeventtoleave;
	}
	public boolean isLeave()
	{
		return isleave;
	}
	private void abort(String s)
	{
		System.out.println(s);
		System.exit(0);
	}
	private String name;
	private String npctype;
	private String msg;
	private String lmsg;
	private String event;
	private String[] imagename;
	private Image[] images;
	private String leaveable;
	private String leavemsg;
	private String leaveitem;
	private String leaveevent;
	private ArrayList sellitemlist;
	
	public static final int MESSAGE_NPC = 0;
	public static final int HEAL_NPC = 1;
	public static final int SELL_NPC = 2;
	private int type;
	
	private int face = 0;
	private boolean isleaveable = false;
	private boolean hastalked = false;
	private boolean hasevent = false;
	private boolean isleave = false;
	private boolean isneeditemtoleave = false;
	private boolean isneedeventtoleave = false;
}
