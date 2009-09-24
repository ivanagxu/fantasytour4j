package com.ivan.game.managers;

import java.io.File;
import java.util.ArrayList;

import com.ivan.game.unit.Item;

public class ItemManager {
	public ItemManager()
	{
		track("正在生成物品管理器...");
		itemlist = new ArrayList();
		itemnamelist = new ArrayList();
		
		File f = new File("data/item/");
		File filelist[] = f.listFiles();
		for(int i = 0; i < filelist.length; i++)
		{
			if(filelist[i].getPath().toLowerCase().endsWith(".dat"))
			{
				track("正在读取物品文件: " + filelist[i].getName());
				itemlist.add(new Item(filelist[i]));
				itemnamelist.add(filelist[i].getName());
			}
		}
		if(itemlist.size() == 0 || itemnamelist.size() == 0 || itemlist.size()!=itemnamelist.size())
		{
			abort("物品管理器没有读取到任何物品文件,无法进行游戏!");
		}
		track("物品管理器生成完毕\n");
	}
	
	/*
	 * get a item object from a item path
	 * @param itemname the name of the item to get
	 * @return if the item is not exist return null
	 */
	public Item getItem(String itemname)
	{
		if(itemname.endsWith("default.dat"))
			return null;
		Item item;
		int n = itemname.lastIndexOf('/') + 1;
		for(int i = 0; i < itemlist.size(); i++)
		{
			item = (Item)itemlist.get(i);
			if (item.getFileName().equals(itemname.substring(n)))
			{
				return item;
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
	private ArrayList itemlist;
	private ArrayList itemnamelist;
	private MessageManager msgmgr;
}
