package com.ivan.menu;

public class MenuItemEx extends MenuItem{
	public MenuItemEx()
	{
		super();
	}
	public MenuItemEx(String name)
	{
		super(name);
	}
	public MenuItemEx(String name,Object e)
	{
		super(name,e);
	}
	public MenuItemEx(String name,Menu[] subMenus,int menulength)
	{
		super(name);
		this.subMenus = subMenus;
	}
	public void setSubMenu(Menu submenu)
	{
		subMenus = new Menu[1];
		subMenus[0] = submenu;
		length = 1;
		setLeaft(false);
	}
	public void setSubMenu(Menu[] submenus,int length)
	{
		subMenus = submenus;
		this.length = length;
		setLeaft(false);
	}
	public Menu getSubMenu(int index)
	{
		if(index > length - 1)
		{
			System.out.print("�˵��쳣:�˵�" + this.getName() + "û��ָ�����Ӳ˵�,ֻ���ؿղ˵�");
			return null;
		}
		else
		{
			return subMenus[index];
		}
	}
	private Menu[] subMenus;
	private int length;
	
}
