package com.ivan.game.unit;
import java.awt.Image;
import java.io.*;
import java.util.ArrayList;

import javax.swing.ImageIcon;
public class Motion {
	public Motion(File f)
	{
		imagelist = new ArrayList();
		imagenamelist = new ArrayList();
		try{
			timespace = "";
			delaytime = "";
			motiontype = "";
			motionname = "";
			
			FileInputStream in = new FileInputStream(f);
			byte[] readbuff = new byte[(int)f.length()];
			in.read(readbuff,0,(int)f.length());
			String readData = new String(readbuff,"utf-8");
			int i = 0;
			while(readData.charAt(i) != '\n')
			{
				motionname += readData.charAt(i);
				i++;
			}
			i++;
			while(readData.charAt(i) != '\n')
			{
				timespace += readData.charAt(i);
				i++;
			}
			i++;
			while(readData.charAt(i) != '\n')
			{
				delaytime += readData.charAt(i);
				i++;
			}
			i++;
			while(readData.charAt(i) != '\n')
			{
				motiontype += readData.charAt(i);
				i++;
			}
			i++;
			String imagename = "";
			while(readData.charAt(i) != '#')
			{
				if(readData.charAt(i) != '\n')
				{
					imagename += readData.charAt(i);
					i++;
				}
				else
				{
					i++;
					imagenamelist.add(imagename);
					File imagefile = new File(imagename);
					Image image = new ImageIcon(imagefile.getPath()).getImage();
					if(image != null)
					{
						imagelist.add(image);
						imagename = "";
					}
				}
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
		
		if(motiontype.equals("变换"))
			type = CHANGE_MOTION;
		else if(motiontype.equals("平移"))
			type = MOVE_MOTION;
		else
		{
			abort("动画: " + motionname + " 类型无法识别!");
		}
		
		if(imagelist.size() == 0 || imagenamelist.size() == 0)
			abort("动画: " + motionname + " 贴图丢失!");
		
	}
	public String getName()
	{
		return motionname;
	}
	public ArrayList getImageList()
	{
		return imagelist;
	}
	public int getTimeSpace()
	{
		return Integer.parseInt(timespace);
	}
	public int getDelayTime()
	{
		return Integer.parseInt(delaytime);
	}
	public int getType()
	{
		return type;
	}
	private void abort(String s)
	{
		System.out.println(s);
		System.exit(0);
	}
	private String timespace = "1000";
	private String delaytime = "1000";
	private String motiontype;
	private String motionname;
	
	private ArrayList imagelist;
	private ArrayList imagenamelist;
	
	private int type;
	public static final int MOVE_MOTION = 0;
	public static final int CHANGE_MOTION = 1;
}
