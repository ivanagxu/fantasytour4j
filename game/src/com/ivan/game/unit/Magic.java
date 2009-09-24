package com.ivan.game.unit;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ivan.game.managers.ItemManager;
import com.ivan.game.managers.MotionManager;

public class Magic {
	public Magic(File f)
	{
		try{
			FileInputStream fin = new FileInputStream(f);
			BufferedReader in = new BufferedReader(new InputStreamReader(fin,"utf-8"));
			
			magicname = in.readLine();
			magictype = in.readLine();
			attacktype = in.readLine();
			magicproperty = in.readLine();
			magicmotion = in.readLine();
			needitem = in.readLine();
			magicdelay = in.readLine();
			magicinfo = in.readLine();
			dmg = in.readLine();
			needmp = in.readLine();
			buffhp = in.readLine();
			buffmp = in.readLine();
			buffdef = in.readLine();
			buffmdef = in.readLine();
			buffstr = in.readLine();
			buffmstr = in.readLine();
			buffhs = in.readLine();
			buffjouk = in.readLine();
			healhp = in.readLine();
			antidotal = in.readLine();
			cleardebuff = in.readLine();
			debuffhp = in.readLine();
			debuffmp = in.readLine();
			debuffdef = in.readLine();
			debuffmdef = in.readLine();
			debuffstr = in.readLine();
			debuffmstr = in.readLine();
			debuffhs = in.readLine();
			debuffjouk = in.readLine();
			
			if(magictype.equals("攻击"))
				type = ATTACK_MAGIC;
			else if(magictype.equals("治疗"))
				type = HEAL_MAGIC;
			else if(magictype.equals("buff"))
				type = BUFF_MAGIC;
			else if(magictype.equals("debuff"))
				type = DEBUFF_MAGIC;
			else
				type = -1;
			
			if(attacktype.equals("普通攻击"))
				actype = NORMAL_ATTACK;
			else if(attacktype.equals("魔法攻击"))
				actype = MAGIC_ATTACK;
			else
				actype = -1;
			
			if(magicproperty.equals("普"))
				property = NORMAL_PROPERTY;
			else if(magicproperty.equals("全"))
				property = ALL_PROPERTY;
			else if(magicproperty.equals("火"))
				property = FIRE_PROPERTY;
			else if(magicproperty.equals("冰"))
				property = ICE_PROPERTY;
			else if(magictype.equals("水"))
				property = WATER_PROPERTY;
			else
				property = -1;
			
			if(property == -1 || actype == -1 || type == -1)
			{
				abort("无法分析技能类型!");
			}
			in.close();
		}
		catch(FileNotFoundException e)
		{
			abort("找不到magic文件!");
		}
		catch(IOException e)
		{
			abort("读magic文件出现io异常!");
		}
	}
	
	public String getName()
	{
		return magicname;
	}
	public int getMagicType()
	{
		return type;
	}
	public int getAttackType()
	{
		return actype;
	}
	public int getProperty()
	{
		return property;
	}
	public Motion getMotion(MotionManager manager)
	{
		return manager.getMotion(magicmotion);
	}
	public Item getNeedItem(ItemManager manager)
	{
		if(!needitem.endsWith("default.dat"))
			return manager.getItem(needitem);
		else
			return null;
	}
	public int getDelay()
	{
		return Integer.parseInt(magicdelay);
	}
	public String getInfo()
	{
		return magicinfo;
	}
	public int getNeedMana()
	{
		return Integer.parseInt(needmp);
	}
	/*
	 * get attack magic data
	 */
	public int getDmg()
	{
		if(typeMatch(ATTACK_MAGIC))
			return Integer.parseInt(dmg);
		return -1;
	}
	/*
	 * get buff magic data
	 */
	public int getBuffHp()
	{
		if(typeMatch(BUFF_MAGIC))
			return Integer.parseInt(buffhp);
		return -1;
	}
	public int getBuffMp()
	{
		if(typeMatch(BUFF_MAGIC))
			return Integer.parseInt(buffmp);
		return -1;
	}
	public int getBuffDef()
	{
		if(typeMatch(BUFF_MAGIC))
			return Integer.parseInt(buffdef);
		return -1;
	}
	public int getBuffMdef()
	{
		if(typeMatch(BUFF_MAGIC))
			return Integer.parseInt(buffmdef);
		return -1;
	}
	public int getBuffStr()
	{
		if(typeMatch(BUFF_MAGIC))
			return Integer.parseInt(buffstr);
		return -1;
	}
	public int getBuffMstr()
	{
		if(typeMatch(BUFF_MAGIC))
			return Integer.parseInt(buffmstr);
		return -1;
	}
	public int getBuffHs()
	{
		if(typeMatch(BUFF_MAGIC))
			return Integer.parseInt(buffhs);
		return -1;
	}
	public int getBuffJouk()
	{
		if(typeMatch(BUFF_MAGIC))
			return Integer.parseInt(buffjouk);
		return -1;
	}
	/*
	 * get debuff data
	 */
	public int getDeBuffHp()
	{
		if(typeMatch(DEBUFF_MAGIC))
			return Integer.parseInt(debuffhp);
		return -1;
	}
	public int getDeBuffMp()
	{
		if(typeMatch(DEBUFF_MAGIC))
			return Integer.parseInt(debuffmp);
		return -1;
	}
	public int getDeBuffDef()
	{
		if(typeMatch(DEBUFF_MAGIC))
			return Integer.parseInt(debuffdef);
		return -1;
	}
	public int getDeBuffMdef()
	{
		if(typeMatch(DEBUFF_MAGIC))
			return Integer.parseInt(debuffmdef);
		return -1;
	}
	public int getDeBuffStr()
	{
		if(typeMatch(DEBUFF_MAGIC))
			return Integer.parseInt(debuffstr);
		return -1;
	}
	public int getDeBuffMstr()
	{
		if(typeMatch(DEBUFF_MAGIC))
			return Integer.parseInt(debuffmstr);
		return -1;
	}
	public int getDeBuffHs()
	{
		if(typeMatch(DEBUFF_MAGIC))
			return Integer.parseInt(debuffhs);
		return -1;
	}
	public int getDeBuffJouk()
	{
		if(typeMatch(DEBUFF_MAGIC))
			return Integer.parseInt(debuffjouk);
		return -1;
	}
	/*
	 * get heal magic data
	 */
	public int getHealHp()
	{
		if(typeMatch(HEAL_MAGIC))
			return Integer.parseInt(healhp);
		return -1;
	}
	public boolean isAntidotal()
	{
		if(antidotal.equals("是"))
			return true;
		else if(antidotal.equals("否"))
			return false;
		else
		{
			abort("技能: " + magicname + " 的antidotal属性出现异常!");
			return false;
		}
	}
	public boolean isClearDebuff()
	{
		if(cleardebuff.equals("是"))
			return true;
		else if(cleardebuff.equals("否"))
			return false;
		else
		{
			abort("技能 " + magicname + " 的cleardebuff属性出现异常!");
			return false;
		}
	}
	/*
	 * to judge wether the function can be call in a magic tyoe
	 * @param t the function can be call only by this type
	 */
	private boolean typeMatch(int t)
	{
		if(type == t)
			return true;
		else
		{
			abort("技能: "+magicname+" 非法使用!");
			return false;
		}
			
	}
	public int getType()
	{
		return type;
	}
	public void reName()
	{
		NumberTester t = new NumberTester();
		String newname;
		if(t.test(magicname.substring(magicname.length()-2)))
		{
			newname = magicname.substring(0,magicname.length()-2)+
			(Integer.parseInt(magicname.substring(magicname.length()-2))+1);
		}
		else if(t.test(magicname.substring(magicname.length()-1)))
		{
			newname = magicname.substring(0,magicname.length()-1)+
			(Integer.parseInt(magicname.substring(magicname.length()-1))+1);
		}
		else
		{
			newname = magicname + "1";
		}
		magicname = newname;
	}
	public String getPropertyString()
	{
		String s = null;
		switch(property)
		{
		case Magic.NORMAL_PROPERTY:s="普通";break;
		case Magic.FIRE_PROPERTY:s="火";break;
		case Magic.WATER_PROPERTY:s="水";break;
		case Magic.ICE_PROPERTY:s="冰";break;
		case Magic.ALL_PROPERTY:s="全能";break;
		default:s="未知";break;
		}
		return s;
	}
	public Color getMagicColor()
	{
		Color c;
		switch(getProperty())
		{
		case Magic.NORMAL_PROPERTY:c = new Color(0,0,0);break;
		case Magic.FIRE_PROPERTY:c = new Color(255,0,0);break;
		case Magic.WATER_PROPERTY:c = new Color(0,85,98);break;
		case Magic.ICE_PROPERTY:c = new Color(73,196,247);break;
		case Magic.ALL_PROPERTY:c = new Color(120,54,210);break;
		default:c = new Color(0,0,0);break;
		}
		return c;
	}
	private void abort(String s)
	{
		System.out.println(s);
		System.exit(0);
	}
	private String magicname;
	private String magictype;
	private String attacktype;
	private String magicproperty;
	private String magicmotion;
	private String needitem;
	private String magicdelay;
	private String magicinfo;
	
	//attack magic
	private String dmg;
	private String needmp;
	
	//buff magic
	private String buffhp;
	private String buffmp;
	private String buffdef;
	private String buffmdef;
	private String buffstr;
	private String buffmstr;
	private String buffhs;
	private String buffjouk;
	
	//heal magic
	private String healhp;
	private String antidotal;
	private String cleardebuff;
	//debuff magic
	private String debuffhp;
	private String debuffmp;
	private String debuffdef;
	private String debuffmdef;
	private String debuffstr;
	private String debuffmstr;
	private String debuffhs;
	private String debuffjouk;
	
	public static final int ATTACK_MAGIC = 0;
	public static final int HEAL_MAGIC = 1;
	public static final int BUFF_MAGIC = 2;
	public static final int DEBUFF_MAGIC = 3;
	private int type;
	
	public static final int MAGIC_ATTACK = 1;
	public static final int NORMAL_ATTACK = 0;
	private int actype;
	
	public static final int NORMAL_PROPERTY = 0;
	public static final int ALL_PROPERTY = 1;
	public static final int FIRE_PROPERTY = 2;
	public static final int ICE_PROPERTY = 3;
	public static final int WATER_PROPERTY = 4;
	private int property;
}
