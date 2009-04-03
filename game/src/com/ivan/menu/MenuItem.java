package com.ivan.menu;

public class MenuItem {
	public MenuItem()
	{
		name = "item" + menucount;
		menucount++;
	}
	public MenuItem(String name)
	{
		this.name = name;
		menucount++;
	}
	public MenuItem(String name,Menu submenu)
	{
		this.name = name;
		subMenu = submenu;
		menucount++;
	}
	public MenuItem(String name,Object e)
	{
		this.name = name;
		elem = e;
		menucount++;
	}
	public MenuItem(String name,Menu submenu,Object e)
	{
		this.name = name;
		subMenu = submenu;
		elem = e;
		menucount++;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	public void setSubMenu(Menu menu)
	{
		subMenu = menu;
		setLeaft(false);
	}
	public Menu getSubMenu()
	{
		return subMenu;
	}
	public void setLeaft(boolean b)
	{
		isLeaft = b;
	}
	public boolean IsLeaft()
	{
		return isLeaft;
	}
	public void setElem(Object e)
	{
		elem = e;
	}
	public Object getElem()
	{
		return elem;
	}
	public boolean IsVisible()
	{
		return visible;
	}
	public void setVisible(boolean b)
	{
		visible = b;
	}
	public void setSelectable(boolean b)
	{
		selectable = b;
	}
	public boolean IsSelectable()
	{
		return selectable;
	}
	private String name;
	private Menu subMenu = null;
	private boolean isLeaft = true;
	private boolean visible = true;
	private boolean selectable = true;
	private Object elem;
	private static int menucount = 0;
}
