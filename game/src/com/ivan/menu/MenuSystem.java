package com.ivan.menu;
/*
 * 简单菜单系统
 * 功能有:
 * 		前后选择
 * 		打开子菜单
 */
public class MenuSystem {
	public MenuSystem()
	{
		
	}
	public MenuSystem(Menu rootmenu)
	{
		this.rootMenu = rootmenu;
	}
	public void setRootMenu(Menu rootmenu)
	{
		this.rootMenu = rootmenu;
	}
	public void Open()
	{
		currentMenu = rootMenu;
		Show();
	}
	public void Show()
	{
		visible = true;
	}
	public void Hide()
	{
		visible = false;
	}
	public boolean IsVisible()
	{
		return visible;
	}
	public MenuItem Select()
	{
		MenuItem selectItem = currentMenu.Select();
		if (selectItem == null)
			return null;
		if (!selectItem.IsLeaft())
		{
			currentMenu = selectItem.getSubMenu();
		}
		return selectItem;
	}
	public void selectPrevious()
	{
		currentMenu.selectPrevious();
	}
	public void selectNext()
	{
		currentMenu.selectNext();
	}
	public void back()
	{
		if(currentMenu != rootMenu)
		{
			currentMenu = currentMenu.getFatherMenu();
		}
	}
	public void setCurrentMenu(Menu m)
	{
		currentMenu = m;
	}
	public Menu getCurrentMenu()
	{
		return currentMenu;
	}
	public MenuItem getCurrentItem()
	{
		return currentMenu.getCurrentItem();
	}
	private boolean visible = false;
	private Menu currentMenu;
	private Menu rootMenu;
}
