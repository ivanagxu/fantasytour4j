package com.ivan.game.unit;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.ivan.game.managers.ItemManager;
import com.ivan.game.managers.MagicManager;

public class Enemy {
	
	/*
	 * 从文件数据进行构造
	 * @param f a enemy file at data\enemy\
	 */
	public Enemy(File f)
	{
		magic = new ArrayList();
		try{
			FileInputStream fin = new FileInputStream(f);
			InputStreamReader in = new InputStreamReader(fin);
			BufferedReader inf = new BufferedReader(in);
			name = inf.readLine();
			property = inf.readLine();
			hp = inf.readLine();
			mp = inf.readLine();
			str = inf.readLine();
			def = inf.readLine();
			mstr = inf.readLine();
			mdef = inf.readLine();
			hs = inf.readLine();
			jouk = inf.readLine();
			money = inf.readLine();
			item = inf.readLine();
			dropprobability = inf.readLine();
			image = inf.readLine();
			hurtimage = inf.readLine();
			attackimage = inf.readLine();
			
			String amagic;
			amagic = inf.readLine();
			while (!amagic.equals("#"))
			{
				magic.add(amagic);
				amagic = inf.readLine();
			}
			in.close();
		}
		catch(FileNotFoundException e)
		{
			abort("找不到enemy文件!");
		}
		catch(IOException e)
		{
			abort("读enemy文件出现io异常!");
		}
		
		if(Integer.parseInt(money) != 0)
		{
			hasMoney = true;
		}
		if(!item.endsWith("default.dat"))
		{
			hasItem = true;
		}
		if(dropprobability.equals("0"))
		{
			hasItem = false;
		}
		images = new Image[3];
		images[0] = new ImageIcon(image).getImage();
		images[1] = new ImageIcon(hurtimage).getImage();
		images[2] = new ImageIcon(attackimage).getImage();
		if(images[0] == null || images[1] == null || images[2] == null)
		{
			abort("怪物: " + name + " 贴图丢失!");
		}
	}
	/*
	 * get the enemy's magic
	 * @return the enemy's Magics,ArrayList type.
	 */
	public ArrayList getMagic(MagicManager manager)
	{
		ArrayList magiclist = new ArrayList();
		for (int i = 0; i < magic.size(); i++)
		{
			Magic amagic = manager.getMagic((String)magic.get(i));
			if(amagic != null)
				magiclist.add(amagic);
			else{
				abort("Enemy: " + name + " 出现技能丢失,估计有文件出错!");
			}
		}
		return magiclist;
	}
	/*
	 * get the drop item
	 * @return the item
	 */
	public Item getItem(ItemManager manager)
	{
		if(hasItem)
			return manager.getItem(item);
		else
			return null;
	}
	/*
	 * get the battle images
	 * @return the images
	 */
	public Image[] getImage()
	{
		return images;
	}
	/*
	 * get the battle state
	 * @return the battle state
	 */
	public BattleState getBattleState()
	{
		BattleState state = new 
		BattleState(
				name,								//名字
				property,							//属性
				Integer.parseInt(hp), 				//血
				Integer.parseInt(hp), 				//血上限
				Integer.parseInt(mp), 				//法力
				Integer.parseInt(mp), 				//法力上限
				Integer.parseInt(str),				//攻击力
				Integer.parseInt(def), 				//防御力
				Integer.parseInt(mstr),				//魔力
				Integer.parseInt(mdef),				//魔防
				Integer.parseInt(hs),				//致命
				Integer.parseInt(jouk)				//躲闪率
				);

		return state;
	}
	public String getName()
	{
		return name;
	}
	public String getProperty()
	{
		return property;
	}
	public int getHp()
	{
		return Integer.parseInt(hp);
	}
	public int getMp()
	{
		return Integer.parseInt(mp);
	}
	public int getStr()
	{
		return Integer.parseInt(str);
	}
	public int getDef()
	{
		return Integer.parseInt(def);
	}
	public int getMstr()
	{
		return Integer.parseInt(mstr);
	}
	public int getHs()
	{
		return Integer.parseInt(hs);
	}
	public int getJouk()
	{
		return Integer.parseInt(jouk);
	}
	public int getMoney()
	{
		return Integer.parseInt(money);
	}
	public int getItemDropProbability()
	{
		return Integer.parseInt(dropprobability);
	}
	public boolean hasItem()
	{
		return hasItem;
	}
	public boolean hasMoney()
	{
		return hasMoney;
	}
	/*
	 * 异常中止程序
	 * @param s 输出出错原因
	 */
	private void abort(String s)
	{
		System.out.println(s);
		System.exit(0);
	}
	private String name;
	private String property;
	private String hp;
	private String mp;
	private String str;
	private String def;
	private String mstr;
	private String mdef;
	private String hs;
	private String jouk;
	private ArrayList magic;		//file
	private String money;
	private String item;			//file
	private String dropprobability;
	private String image;			//file
	private String hurtimage;		//file
	private String attackimage;		//file
	
	private Image[] images;
	boolean hasItem = false;
	boolean hasMoney = false;
}
