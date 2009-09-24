package com.ivan.game.unit;
import java.awt.Image;

import javax.swing.ImageIcon;

import com.ivan.game.managers.EnemyManager;
import com.ivan.game.managers.EventManager;
import com.ivan.game.managers.NpcManager;


public class MapPoint {
	public MapPoint(String name)
	{
		mapname = name;
		x = "0";
		y = "0";
		event = "data/default.dat";
		npc = "data/default.dat";
		enemy = "data/default.dat";
		probability = "0";
		imagepath = "data/images/world/ground.gif";
		image = new ImageIcon(imagepath).getImage();
		walkable = "是";
		anpc = null;
		aevent = null;
		aenemy = null;
	}
	public void readFromString(String as)
	{
		as.replace(' ','\0');
		int i = 0;
		init();
		String s = as.substring(as.lastIndexOf(" ")+1);
		while(s.charAt(i) != '#')
		{
			mapname += s.charAt(i);
			i++;
		}
		i++;
		while(s.charAt(i) != '#')
		{
			x += s.charAt(i);
			i++;
		}
		i++;
		while(s.charAt(i) != '#')
		{
			y += s.charAt(i);
			i++;
		}
		i++;
		while(s.charAt(i) != '#')
		{
			event += s.charAt(i);
			i++;
		}
		i++;
		while(s.charAt(i) != '#')
		{
			npc += s.charAt(i);
			i++;
		}
		i++;
		while(s.charAt(i) != '#')
		{
			enemy += s.charAt(i);
			i++;
		}
		i++;
		while(s.charAt(i) != '#')
		{
			probability += s.charAt(i);
			i++;
		}
		i++;
		while(s.charAt(i) != '#')
		{
			imagepath += s.charAt(i);
			i++;
		}
		i++;
		while(s.charAt(i) != '#')
		{
			walkable += s.charAt(i);
			i++;
		}
		
		if(event.endsWith("default.dat"))
			hasevent = false;
		else
		{
			hasevent = true;
			//aevent = new Event(new File(event));
		}
		
		if(npc.endsWith("default.dat"))
			hasnpc = false;
		else
		{
			hasnpc = true;
			//anpc = new Npc(new File(npc));
		}
		
		if(enemy.endsWith("default.dat"))
			hasenemy = false;
		else
		{
			hasenemy = true;
			//aenemy = new Enemy(new File(enemy));
		}
		
		image = new ImageIcon(imagepath).getImage();
		
		if(image == null)
			abort("地图" + mapname + " 坐标:(" + x + "," + y + ")贴图丢失!");
		
		if(walkable.equals("是"))
			iswalkable = true;
		else if(walkable.equals("否"))
			iswalkable = false;
		else
		{
			abort("地图" + mapname + " 坐标:(" + x + "," + y + ")行走属性无法识别!");
		}
	}
	public void init()
	{
		mapname = "";
		x = "";
		y = "";
		event = "";
		npc = "";
		enemy = "";
		probability = "";
		imagepath = "";
		walkable = "";
		image = null;
	}
	public String toString()
	{
		String s = new String("");
		s = mapname + "#" +
		x + "#" +
		y + "#" +
		event + "#" +
		npc + "#" +
		enemy + "#" +
		probability + "#" +
		imagepath + "#" +
		walkable + "#" +
		"\n                                                                                  ";
		return s;
	}
	public String getMapName()
	{
		return mapname;
	}
	public int getX()
	{
		return Integer.parseInt(x);
	}
	public int getY()
	{
		return Integer.parseInt(y);
	}
	public Event getEvent(EventManager manager)
	{
		if(hasevent)
			return manager.getEvent(event);
		return null;
	}
	public Npc getNpc(NpcManager manager)
	{
		if(hasnpc)
			return manager.getNpc(npc);
		else
			return null;
	}
	public Enemy getEnemy(EnemyManager manager)
	{
		if(hasenemy)
			return manager.getEnemy(enemy);
		else
			return null;
	}
	public int getEnemyAttackProbability()
	{
		return Integer.parseInt(probability);
	}
	public Image getImageIcon()
	{
		return image;
	}
	public boolean IsWalkabel()
	{
		return iswalkable;
	}
	public boolean hasEvent()
	{
		return hasevent;
	}
	public boolean hasNpc()
	{
		return hasnpc;
	}
	public boolean hasEnemy()
	{
		return hasenemy;
	}
	private void abort(String s)
	{
		System.out.println(s);
		System.exit(0);
	}
	public String mapname;
	public String x;
	public String y;
	public String event;
	public Event aevent;
	public String npc;
	public Npc anpc;
	public String enemy;
	public Enemy aenemy;
	public String probability;
	public String imagepath;
	public Image image;
	public String walkable;
	
	private boolean hasevent = false;
	private boolean hasnpc = false;
	private boolean hasenemy = false;
	private boolean iswalkable = true;
	
}
