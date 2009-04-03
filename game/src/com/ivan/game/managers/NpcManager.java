package com.ivan.game.managers;

import java.io.File;
import java.util.ArrayList;

import com.ivan.game.unit.Npc;

public class NpcManager {
	public NpcManager(ItemManager itmmgr)
	{
		track("��������NPC������...");
		npclist = new ArrayList();
		npcnamelist = new ArrayList();
		File f = new File("data/npc/");
		File[] filelist = f.listFiles();
		/*
		 * read all the npc file from data\npc\ directory
		 */
		for(int i = 0; i < filelist.length; i++)
		{
			if(filelist[i].getName().toLowerCase().endsWith(".dat"))
			{
				track("���ڶ�ȡNPC�ļ�: " + filelist[i].getName());
				npclist.add(new Npc(filelist[i],itmmgr));
				npcnamelist.add(filelist[i].getName());
			}
		}
		if(npclist.size() == 0 || npcnamelist.size() == 0 || npclist.size()!=npcnamelist.size())
		{
			abort("NPC�������޷���ȡ�κ�NPC�ļ�,��Ϸ�޷�����!");
		}
		track("NPC�������������\n");
	}
	/*
	 * get a npc by npc name
	 * @param the npc name
	 * @return a npc object
	 */
	public Npc getNpc(String npcname)
	{
		if(npcname.endsWith("default.dat"))
			return null;
		Npc npc;
		int n = npcname.lastIndexOf('/') + 1;
		for(int i = 0; i < npclist.size(); i++)
		{
			npc = (Npc)npclist.get(i);
			if(npcname.substring(n).equals((String)npcnamelist.get(i)))
			{
				return npc;
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
	private ArrayList npclist;
	private ArrayList npcnamelist;
	private MessageManager msgmgr;
}
