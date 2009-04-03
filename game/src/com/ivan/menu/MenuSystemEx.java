package com.ivan.menu;
/*
 * ��չ�˵�ϵͳ,֧�ֶ��ִ��Ӳ˵�ģʽ,��һ��ѡ����԰�������Ӳ˵�.
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
