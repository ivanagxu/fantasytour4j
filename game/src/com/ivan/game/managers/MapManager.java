package com.ivan.game.managers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.ivan.game.unit.Event;
import com.ivan.game.unit.Hero;
import com.ivan.game.unit.Map;
import com.ivan.game.unit.MapPoint;
import com.ivan.game.unit.Msg;
import com.ivan.game.unit.Npc;



public class MapManager {
	public MapManager()
	{
		x = 0;
		y = 0;
		mappoint = new MapPoint[15][15];
		for(int i = 0; i < 15; i++)
		{
			for(int j = 0; j < 15; j++)
				mappoint[i][j] = null;
		}
		mapfile = new File("data/map/testmap.dat");
	}
	public void CreateMap(Hero h)
	{
		if(h == null)
			abort("不能生成地图,原因:没主角");
		track("正在生成地图...");
		mapfile = new File(h.getMapFile());
		map = new Map(mapfile);
		
		map.setEnemyManager(emymgr);
		map.setEventMgr(evtmgr);
		map.setNpcMgr(npcmgr);
		map.initMapPointUnit();
		
		map.initForHero(h);
		dplmgr.changeMapMovie(map.getMapName(),map.getMusic());
		x = h.getX();
		y = h.getY();
		track("地图生成完毕\n");
	}
	public Map getMap()
	{
		return map;
	}
	public Map getMap(String mapname)
	{
		return new Map(new File(mapname));
	}
	public void draw(int d,Hero hero,Graphics g)
	{
		canvas = g;
		display = d;
		if(display == GameManager.SHOW_MAP)
		{
			//System.out.println(mapfile.getName() + " " + hero.getMapFile().substring(hero.getMapFile().lastIndexOf("/") + 1,hero.getMapFile().length()));
			if(!mapfile.getName().equals(hero.getMapFile().substring(hero.getMapFile().lastIndexOf("/") + 1,hero.getMapFile().length())))
			{
				CreateMap(hero);
				getPartMap(hero);
			}
			int tx = x - hero.getX();
			int ty = y - hero.getY();

			/*
			 * 当主角坐标发生非移动变化(如传送),重新获取主角附近地图进行重画
			 */
			if(Math.abs(tx)>1 || Math.abs(ty)>1)
			{
				if(display == GameManager.SHOW_MAP)
				{
					/*
					 * 获取主角附近15*15的地图部分
					 */
					getPartMap(hero);
					/*
					 * 获取缓冲区doubleBuffer;
					 */
					Graphics g2 = doubleBuffer.getGraphics();
					g2.setColor(Color.BLACK);
					g2.fillRect(0,0,260,260);
					paint(g2,0,tx,ty);

					g2.dispose();

					//canvas.drawImage(hero.getCurrentImage(),120,120,null);
					canvas.drawImage(doubleBuffer,0,0,null);
					canvas.drawImage(hero.getCurrentImage(),120,120,null);
					x = hero.getX();
					y = hero.getY();
				}
			}
			/*
			 * 主角坐标只发生移动变化,先画图再获取主角附近地图
			 */
			else
			{
				for (int t = 0; t < 21; t += 2) 
				{
					sleep(10);

					/*
					 * 获得后缓存doubleBuffer
					 */
					Graphics g2 = doubleBuffer.getGraphics();
					g2.setColor(Color.BLACK);
					g2.fillRect(0, 0, 260, 260);
					paint(g2, t, tx, ty);

					

					//canvas.drawImage(hero.getCurrentImage(), 120, 120, null);
					
					if(t > 6 && t < 14)
						g2.drawImage(hero.getCurrentImage2(), 120, 120, null);
					else
						g2.drawImage(hero.getCurrentImage(), 120, 120, null);
					canvas.drawImage(doubleBuffer, 0, 0, null);
					g2.dispose();
					/*
					 * 主角坐标没变,只画一次,返回
					 */
					if(tx == 0 && ty == 0)
					{
						return;
					}
						
				}
				x = hero.getX();
				y = hero.getY();
				getPartMap(hero);
			}
		}
		/*
		 * 如果显示菜单,直接画缓冲区图像
		 */
		else if(display == GameManager.SHOW_MAP_MENU)
		{
			canvas.drawImage(doubleBuffer,0,0,null);
			canvas.drawImage(hero.getCurrentImage(),120,120,null);
		}
	}
	private void paint(Graphics g2,int t,int tx,int ty)
	{
		for(int i = 0; i < 15; i++)
		{
			for(int j = 0; j < 15; j++)
			{
				if(mappoint[i][j] != null)
				{
					if(mappoint[i][j].hasNpc() && !mappoint[i][j].getNpc(npcmgr).isLeave())
					{
						g2.drawImage(mappoint[i][j].getImageIcon(),
								(j-1)*20 + t*tx,
								(i-1)*20 + t*ty,
								null);
						g2.drawImage(mappoint[i][j].getNpc(npcmgr).getCurrentImage(),
								(j-1)*20 + t*tx,
								(i-1)*20 + t*ty,
								null);
					}
					else
					{
						g2.drawImage(mappoint[i][j].getImageIcon(),
								(j-1)*20 + t*tx,
								(i-1)*20 + t*ty,
								null);
					}
				}
			}
		}
	}
	private void getPartMap(Hero hero)
	{
		MapPoint p;
		int m = 0;
		int n = 0;
		for(int i = hero.getY() - 7; i < hero.getY() + 8; i++)
		{
			for(int j = hero.getX() -7; j < hero.getX() + 8; j++)
			{
				p = null;
				if(i >= 0 && i < map.getMapHeight() && j >=0 && j < map.getMapWidth())
				{
					p = map.getMapPoint(j,i);
				}
				mappoint[m][n] = p;
				n++;
			}
			m++;
			n = 0;
		}
	}
	public void action(char input,int d,Hero hero)
	{
		display = d;
		if(hero.getNextEvent() != null)
		{
			Event e = hero.getNextEvent();
			if(hero.executeEvent(e))
				msgmgr.showMessage(new Msg(e.getInfo()));
			e = null;
		}
		if(input == 'h')
		{
			dplmgr.setDisplay(GameManager.SHOW_MAP_MENU);
		}
		else if(input == 'j')
		{
			int tx = 0;
			int ty = 0;
			if(hero.getFace() == 0)
			{
				tx = 0;
				ty = 1;
			}
			else if(hero.getFace() == 1)
			{
				tx = 0;
				ty = -1;
			}
			else if(hero.getFace() == 2)
			{
				tx = -1;
				ty = 0;
			}
			else if(hero.getFace() == 3)
			{
				tx = 1;
				ty = 0;
			}
			if (x + tx >= 0 && x + tx < map.getMapWidth() && y + ty >= 0
					&& y + ty < map.getMapHeight()) 
			{
				Npc npc = map.getMapPoint(x + tx, y + ty).getNpc(npcmgr);
				if (npc != null) 
				{
					if (!npc.isLeave()) 
					{
						npc.talk(hero);
						Msg msg = new Msg(npc.getName() + ":" + npc.getMsg());
						draw(display,hero,canvas);
						msgmgr.showMessage(msg);
						/*
						 * 避免一次按键触发多次action,线程睡眠一会- -!
						 */
						sleep(400);
						if (npc.getType() == Npc.HEAL_NPC) 
						{
							hero.fullyRecover();
						}
						else
						if (npc.getType() == Npc.SELL_NPC) 
						{
							if (npc.getSellItemList().size() > 0) {
								dplmgr.setDisplay(GameManager.SHOW_BUYMENU);
								menumgr.setSeller(npc);
								menumgr.setMenu(9, 1);
							}
						}
						if (npc.hasEvent()) 
						{
							Event event = npc.getEvent(evtmgr);
							if (hero.executeEvent(event))
							{
								msgmgr.showMessage(new Msg(event.getInfo()));
								sleep(200);
							}
						}
						if (npc.isleaveable()) 
						{
							if (npc.IsNeedEventToLeave()) 
							{
								ArrayList eventlist = hero.getEventlist();
								for (int i = 0; i < eventlist.size(); i++) 
								{
									if (npc.getLeaveEvent().equals(
											(String) eventlist.get(i))) 
									{
										msgmgr.showMessage(new Msg(npc
												.getLeaveMsg()));
										npc.leave();
										hero.addLeaveNpc(npc.getName());
										break;
									}
								}
							} 
							else if (npc.IsNeedItemToLeave()) 
							{
								ArrayList itemlist = hero.getItemList();
								for (int i = 0; i < itemlist.size(); i++) 
								{
									if (npc.getLeaveItem().equals(
											(String) itemlist.get(i))) 
									{
										msgmgr.showMessage(new Msg(npc
												.getLeaveMsg()));
										npc.leave();
										hero.addLeaveNpc(npc.getName());
										break;
									}
								}
							}
						}

					}
				}
			}
		}
		/*
		 * 检查地图是否为主角所在的地图,不是则重画地图
		 */
		if(!mapfile.getName().equals(hero.getMapFile().substring(hero.getMapFile().lastIndexOf("/") + 1,hero.getMapFile().length())))
		{
			CreateMap(hero);
			getPartMap(hero);
		}
		MapPoint p = map.getMapPoint(x,y);
		//System.out.println(p.hasEvent());
		//System.out.println(p.event);
		if(p.hasEvent())
		{
			Event event = p.getEvent(evtmgr);
			//System.out.println(event.getName());
			if(event != null)
			{
				hero.executeEvent(event);
			}
		}
		
	}
	public int getDisplay()
	{
		return display;
	}
	public void setMsgMgr(MessageManager m)
	{
		msgmgr = m;
	}
	public void setDisplayMgr(DisplayManager m)
	{
		dplmgr = m;
	}
	public void setEventMgr(EventManager m)
	{
		evtmgr = m;
	}
	public void setNpcMgr(NpcManager m)
	{
		npcmgr = m;
	}
	public void setEnemyMgr(EnemyManager e)
	{
		emymgr = e;
	}
	public void setMenuMgr(MenuManager m)
	{
		menumgr = m;
	}
	public void setBufferCanvas(JFrame frame)
	{
		doubleBuffer = frame.createImage(260,260);
	}
	private void track(String s)
	{
		System.out.println(s);
	}
	public void sleep(int ms)
	{
		try{
			Thread.sleep(ms);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	public void abort(String s)
	{
		System.out.println(s);
		System.exit(0);
	}
	private int x;
	private int y;
	private Map map;
	private MapPoint[][] mappoint;
	private int display;
	private Graphics canvas;
	private File mapfile;
	private MessageManager msgmgr;
	private DisplayManager dplmgr;
	private MenuManager menumgr;
	private NpcManager npcmgr;
	private EventManager evtmgr;
	private EnemyManager emymgr;
	private Image doubleBuffer;
}
