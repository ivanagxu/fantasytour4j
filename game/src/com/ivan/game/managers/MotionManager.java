package com.ivan.game.managers;

import java.io.File;
import java.util.ArrayList;

import com.ivan.game.unit.Motion;


public class MotionManager {
	public MotionManager()
	{
		track("�������ɶ���������...");
		File f = new File("data/motion/");
		File filelist[] = f.listFiles();
		motionlist = new ArrayList();
		motionnamelist = new ArrayList();
		/*
		 * read all the motion files from the data\motion\
		 */
		for(int i = 0; i<filelist.length;i++)
		{
			if(filelist[i].getPath().toLowerCase().endsWith(".dat"))
			{
				track("���ڶ�ȡ�����ļ�: " + filelist[i].getName());
				motionlist.add(new Motion(filelist[i]));
				motionnamelist.add(filelist[i].getName());
			}
		}
		if(motionlist.size() == 0 || motionnamelist.size() == 0 || motionlist.size()!=motionnamelist.size())
		{
			abort("����������û�ж�ȡ���κζ����ļ�,��Ϸ�޷�����!");
		}
		track("�����������������.\n");
	}
	/*
	 * get a motion object from a motion path
	 * @param motionname the name of the motion to get
	 * @return if the motion is not exist return null
	 */
	public Motion getMotion(String motionname)
	{
		if(motionname.endsWith("default.dat"))
			return null;
		int n = motionname.lastIndexOf('/') + 1;
		Motion motion;
		for(int i = 0; i < motionlist.size(); i++)
		{
			motion = (Motion)motionlist.get(i);
			if (motionnamelist.get(i).equals(motionname.substring(n)))
			{
				return motion;
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
	private ArrayList motionnamelist;
	private ArrayList motionlist;
	private MessageManager msgmgr;
}
