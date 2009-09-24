package com.ivan.game.unit;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;

import com.ivan.game.managers.EventManager;


public class Item {
	public Item(File f)
	{
		try
		{
			BufferedReader in = new 
			BufferedReader(new 
					InputStreamReader(new 
							FileInputStream(f)
							,"utf-8"));
			
			filename = in.readLine();
			itemname = in.readLine();
			itemtype = in.readLine();
			oneoff = in.readLine();
			actevent = in.readLine();
			hp = in.readLine();
			mp = in.readLine();
			antidotal = in.readLine();
			imagename = in.readLine();
			instruction = in.readLine();
			sellable = in.readLine();
			itemprice = in.readLine();
			
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
		if (!f.getName().equals(filename))
		{
			abort("加载物品文件: " + f.getName() + " 出现异常!文件名不匹配");
		}
		
		if(itemtype.equals("一般物品"))
			type = NORMAL_ITEM;
		else if(itemtype.equals("战斗物品"))
			type = BATTLE_ITEM;
		else if(itemtype.equals("事件物品"))
			type = EVENT_ITEM;
		else
		{
			abort("物品: " + itemname + " 类型无法识别!");
		}
		
		if(oneoff.equals("是"))
			isoneoff = true;
		else if(oneoff.equals("否"))
			isoneoff = false;
		else
		{
			abort("物品: "+ itemname + " 使用性质无法识别!");
		}
		
		if(!actevent.endsWith("default.dat"))
			hasevent = false;
		else
			hasevent = true;
		
		if(antidotal.equals("是"))
			isantidotal = true;
		else if(antidotal.equals("否"))
			isantidotal = false;
		else
		{
			abort("物品: " + itemname + " 解毒属性无法识别!");
		}
		
		itemimage = new ImageIcon(imagename).getImage();
		if(itemimage == null)
		{
			abort("物品: "+ itemname + " 图标丢失!");
		}
		
		if(sellable.equals("是"))
			issellable = true;
		else if (sellable.equals("否"))
			issellable = false;
		else
		{
			abort("物品: "+ itemname + " 出售属性无法识别!");
		}
		
		if(!hp.equals("0"))
		{
			ishealhp = true;
		}
		if(!mp.equals("0"))
		{
			isrecovermp = true;
		}
	}
	public String getFileName()
	{
		return filename;
	}
	public String getName()
	{
		return itemname;
	}
	public int getItemType()
	{
		return type;
	}
	public boolean IsOneOff()
	{
		return isoneoff;
	}
	public Event getEvent(EventManager manager)
	{
		if(hasevent)
			return manager.getEvent(actevent);
		else
			return null;
	}
	public String getEvent()
	{
		return actevent;
	}
	public int getHealHp()
	{
		return Integer.parseInt(hp);
	}
	public int getRecoverMp()
	{
		return Integer.parseInt(mp);
	}
	public Image getImage()
	{
		return itemimage;
	}
	public boolean IsAntidotal()
	{
		return isantidotal;
	}
	public String getInstruction()
	{
		return instruction;
	}
	public boolean IsSellabel()
	{
		return issellable;
	}
	public int getPrice()
	{
		return Integer.parseInt(itemprice);
	}
	public boolean hasEvent()
	{
		return hasevent;
	}
	public boolean IsHealHp()
	{
		return ishealhp;
	}
	public boolean IsRecoverMp()
	{
		return isrecovermp;
	}
	public void reName()
	{
		NumberTester t = new NumberTester();
		String newname;
		if(t.test(itemname.substring(itemname.length()-2)))
		{
			newname = itemname.substring(0,itemname.length()-2)+
			(Integer.parseInt(itemname.substring(itemname.length()-2))+1);
		}
		else if(t.test(itemname.substring(itemname.length()-1)))
		{
			newname = itemname.substring(0,itemname.length()-1)+
			(Integer.parseInt(itemname.substring(itemname.length()-1))+1);
		}
		else
		{
			newname = itemname + "1";
		}
		itemname = newname;
	}
	private void abort(String s)
	{
		System.out.println(s);
		System.exit(0);
	}
	private String filename;
	private String itemname;
	private String itemtype;
	private String oneoff;
	private String actevent;
	private String hp;
	private String mp;
	private String antidotal;
	private String imagename;
	private String instruction;
	private String sellable;
	private String itemprice;
	
	private int type;
	public static final int NORMAL_ITEM = 0;
	public static final int BATTLE_ITEM = 1;
	public static final int EVENT_ITEM = 2;
	
	private boolean isoneoff = true;
	private boolean hasevent = false;
	private boolean isantidotal = false;
	private Image itemimage;
	private boolean issellable = true;
	
	private boolean ishealhp = false;
	private boolean isrecovermp = false;
}
