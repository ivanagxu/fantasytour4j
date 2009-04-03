package com.ivan.game.managers;

import java.io.File;
import java.util.ArrayList;

import com.ivan.game.unit.Magic;

public class MagicManager {

	public MagicManager()
	{
		track("正在生成技能管理器...");
		magiclist = new ArrayList();
		magicnamelist = new ArrayList();
		
		File f = new File("data/magic/");
		File filelist[] = f.listFiles();
		/*
		 * read all the magic from data\magic\
		 */
		for(int i = 0; i<filelist.length;i++)
		{
			if(filelist[i].getPath().toLowerCase().endsWith(".dat"))
			{
				track("正在读取技能文件: " + filelist[i].getName());
				magiclist.add(new Magic(filelist[i]));
				magicnamelist.add(filelist[i].getName());
			}
		}
		if(magiclist.size() == 0 || magicnamelist.size() == 0 || magiclist.size() != magicnamelist.size())
		{
			abort("技能管理器没有读取到任何技能文件,游戏无法进行!");
		}
		track("技能管理器生成完毕\n");
	}
	/*
	 * get a magic by offering the magic name
	 * @param magicname the magic name
	 * @return the magic
	 */
	public Magic getMagic(String magicname)
	{
		if(magicname.endsWith("default.dat"))
			return null;
		int n = magicname.lastIndexOf('/') + 1;
		Magic magic = null;
		for(int i = 0; i < magiclist.size(); i++)
		{
			magic = (Magic)magiclist.get(i);
			if(magicname.substring(n).equals(magicnamelist.get(i)))
			{
				return magic;
			}
		}
		return null;
	}
	public Magic getMagicByName(String magicname)
	{
		Magic magic = null;
		for(int i = 0; i < magiclist.size(); i++)
		{
			magic = (Magic)magiclist.get(i);
			if(magicname.equals(magic.getName()))
			{
				return magic;
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
	private ArrayList magiclist;
	private ArrayList magicnamelist;
	private MessageManager msgmgr;
}
