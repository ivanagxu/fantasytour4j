package com.ivan.game.unit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


import com.ivan.game.managers.EnemyManager;
import com.ivan.game.managers.EventManager;
import com.ivan.game.managers.NpcManager;


public class Map {
	public Map(File f)
	{
		try
		{
			BufferedReader in = new 
			BufferedReader(new 
					InputStreamReader(new 
							FileInputStream(f)
							,"utf-8"));
			soundfile = in.readLine();
			soundfile.replace(' ','\0');
			String tablex = in.readLine();
			String tabley = in.readLine();
			
			map = new MapPoint[Integer.parseInt(tabley)][Integer.parseInt(tablex)];
			MapPoint temp;
			String mapstring = "";
			for(int i = 0 ; i < Integer.parseInt(tabley); i ++)
			{
				for(int j = 0; j<Integer.parseInt(tablex); j++)
				{
					temp = new MapPoint("");
					mapstring = in.readLine();
					if(mapstring == null)
						abort("地图读取错误,文件没有内容!,或文件已到尽头!");
					temp.readFromString(mapstring);
					map[i][j] = temp;
					map[i][j].y = new Integer(i).toString();
					map[i][j].x = new Integer(j).toString();
				}
			}
			mapname = map[0][0].mapname;
			width = map[0].length;
			height = map.length;
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public void initMapPointUnit()
	{
		for(int i = 0; i < height; i++)
		{
			for(int j = 0 ; j < width; j++)
			{
				if(map[i][j].hasEnemy())
					map[i][j].aenemy = map[i][j].getEnemy(emymgr);
				if(map[i][j].hasEvent())
					map[i][j].aevent = map[i][j].getEvent(evtmgr);
				if(map[i][j].hasNpc())
					map[i][j].anpc = map[i][j].getNpc(npcmgr);
			}
		}
	}
	public MapPoint getMapPoint(int x,int y)
	{
		if(x >= 0 && x < width && y >=0 && y < height)
			return map[y][x];
		else
		{
			abort("地图: " + mapname + "size没有(" + x + "," + y + ")的坐标!");
			return null;
		}
	}
	public void initForHero(Hero hero)
	{
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				if(map[i][j].anpc != null)
				{
					if(hero.findNpc(map[i][j].anpc.getName()))
					{
						map[i][j].anpc.leave();
					}
				}
			}
		}
	}
	public String getMusic()
	{
		return soundfile;
	}
	public String getMapName()
	{
		return mapname;
	}
	public int getMapWidth()
	{
		return width;
	}
	public int getMapHeight()
	{
		return height;
	}
	public void setNpcMgr(NpcManager n)
	{
		npcmgr = n;
	}
	public void setEventMgr(EventManager e)
	{
		evtmgr = e;
	}
	public void setEnemyManager(EnemyManager e)
	{
		emymgr = e;
	}
	private void abort(String s)
	{
		System.out.println(s);
		System.exit(0);
	}
	private MapPoint[][] map;
	private String mapname;
	private NpcManager npcmgr;
	private EventManager evtmgr;
	private EnemyManager emymgr;
	private int height;
	private int width;
	private String soundfile;
}
