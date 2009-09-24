package com.ivan.menu;
/*
 * 扩展菜单系统,支持多种打开子菜单模式,即一个选项可以包含多个子菜单.
 */
public class MenuSystemEx extends MenuSystem{
	public MenuSystemEx(Menu rootmenu)
	{
		super(rootmenu);
	}
	public MenuItem Select()
	{
		return Select(0);
	}
	public MenuItem Select(int selectIndex)
	{
		MenuItemEx selectItem = (MenuItemEx)getCurrentMenu().Select();
		if (selectItem == null)
			return null;
		if (!selectItem.IsLeaft())
		{
			setCurrentMenu(selectItem.getSubMenu(selectIndex));
		}
		return selectItem;
	}
}
