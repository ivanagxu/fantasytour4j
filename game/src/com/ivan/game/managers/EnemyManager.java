package com.ivan.game.managers;

import java.io.File;
import java.util.ArrayList;

import com.ivan.game.unit.Enemy;

public class EnemyManager {
	public EnemyManager()
	{
		track("�������ɹ��������...");
		enemylist = new ArrayList();
		enemynamelist = new ArrayList();
		
		File f = new File("data/enemy/");
		File[] filelist = f.listFiles();
		/*
		 * read all the enemy files
		 */
		for(int i = 0; i < filelist.length; i++)
		{
			if(filelist[i].getName().toLowerCase().endsWith(".dat"))
			{
				track("���ڶ�ȡ�����ļ�: "+filelist[i].getName());
				enemylist.add(new Enemy(filelist[i]));
				enemynamelist.add(filelist[i].getName());
			}
		}
		if(enemylist.size() == 0 || enemynamelist.size() == 0 
				|| enemylist.size()!=enemynamelist.size())
		{
			abort("���������û�ж�ȡ���κι����ļ�,�޷�������Ϸ!");
		}
		track("����������������\n");
	}
	/*
	 * get a enemy whitch is named enemyname
	 * @enemyname the enemy name
	 * @return the enemy named enemynane - -!
	 */
	public Enemy getEnemy(String enemyname)
	{
		if(enemyname.endsWith("default.dat"))
			return null;
		int n = enemyname.lastIndexOf('/') + 1;
		Enemy enemy;
		for(int i = 0; i < enemylist.size(); i++)
		{
			enemy = (Enemy)enemylist.get(i);
			if((enemyname.substring(n)).equals((String)enemynamelist.get(i)))
			{
				return enemy;
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
	private ArrayList enemylist;
	private ArrayList enemynamelist;
	private MessageManager msgmgr;
}
