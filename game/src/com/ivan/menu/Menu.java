package com.ivan.menu;

public class Menu {
	public Menu()
	{
		name = "menu" + menucount;
		menucount++;
	}
	public Menu(String name)
	{
		this.name = name;
		menucount++;
	}
	public Menu(String name,MenuItem[] il,int length)
	{
		this.name = name;
		itemlist = il;
		this.length = length;
		menucount++;
	}
	public Menu(String name,Menu fathermenu)
	{
		this.name = name;
		fatherMenu = fathermenu;
		menucount++;
	}
	public Menu(String name,Menu fathermenu,MenuItem[] il,int length)
	{
		this.name = name;
		fatherMenu = fathermenu;
		itemlist = il;
		this.length = length;
		menucount++;
	}
	/*
	 * 设置父菜单
	 * @param fm 父菜单
	 */
	public void setFatherMenu(Menu fm)
	{
		fatherMenu = fm;
	}
	/*
	 * 获取项目列表
	 * @return itemlist 项目列表
	 */
	public MenuItem[] getItemList()
	{
		return itemlist;
	}
	/*
	 * 设置项目列表
	 * @param il 项目列表数组
	 * @param length 项目列表数组长度
	 */
	public void setItemList(MenuItem[] il,int length)
	{
		itemlist = il;
		this.length = length;
	}
	public String getName()
	{
		return name;
	}
	public Menu getFatherMenu()
	{
		return fatherMenu;
	}
	public int getLength()
	{
		return length;
	}
	/*
	 * 设置默认选项
	 * @param itemindex 默认选项在项目列表中的下标
	 */
	public void setDefaultItemIndex(int itemindex)
	{
		if(itemindex > length - 1)
		{
			System.out.println("菜单异常:菜单" + getName() + "指定默认选项时,数组越界,现设置为第一项.");
			defaultItem = 0;
			return;
		}
		defaultItem = itemindex;
	}
	public int getDefaultItemIndex()
	{
		return defaultItem;
	}
	public void setCurrentSelect(int index)
	{
		if(index >length - 1)
		{
			System.out.println("菜单异常:菜单" + getName() + "指定当前选项时,数组越界,现设置为第一项.");
			currentItem = 0;
			return;
		}
		currentItem = index;
	}
	public void selectPrevious()
	{
		if (selectMode == Menu.CYCLICALABLE_SELECT) {
			do {
				currentItem--;
				if (currentItem < 0)
					currentItem = length - 1;
			} while (!itemlist[currentItem].IsVisible());
		}
		else if(selectMode == Menu.NON_CYCLICALABLE_SELECT)
		{
			int temp = currentItem;
			do {
				currentItem--;
				if (currentItem < 0)
				{
					currentItem = temp;
					break;
				}
			} while (!itemlist[currentItem].IsVisible());
		}
	}

	public void selectNext() {
		if (selectMode == Menu.CYCLICALABLE_SELECT) {
			do {
				currentItem++;
				if (currentItem > length - 1)
					currentItem = 0;
			} while (!itemlist[currentItem].IsVisible());
		}
		else if(selectMode == Menu.NON_CYCLICALABLE_SELECT)
		{
			int temp = currentItem;
			do {
				currentItem++;
				if (currentItem > length - 1)
				{
					currentItem = temp;
					break;
				}
			} while (!itemlist[currentItem].IsVisible());
		}
	}
	/*
	 * 选定,或打开子菜单
	 * @return 被选定的菜单项目
	 */
	public MenuItem Select()
	{
		if(itemlist[currentItem].IsSelectable())
		{
			return itemlist[currentItem];
		}
		else
			return null;
	}
	public MenuItem getCurrentItem()
	{
		return itemlist[currentItem];
	}
	public void setSelectMode(int sm)
	{
		selectMode = sm;
	}
	public int getSelectMode()
	{
		return selectMode;
	}
	public void setDisplayMode(int dm)
	{
		displayMode = dm;
	}
	public int getDisplayMode()
	{
		return displayMode;
	}
	public int length = 0;
	private static int menucount = 0;
	private MenuItem[] itemlist = null;
	private Menu fatherMenu = null;
	private int defaultItem = 0;
	private int currentItem = 0;
	private String name;
	private int selectMode = CYCLICALABLE_SELECT;
	/*
	 * CYCLICALABLE_SELECT 为循环选择模式
	 * NON_CYCLICALABLE_SELECT 为非循环选择模式
	 */
	public static final int CYCLICALABLE_SELECT = 0;
	public static final int NON_CYCLICALABLE_SELECT = 1;
	
	/*
	 * SHOW_DISABLE_SELECTION 为显示无效选项模式
	 * HIDE_DISABLE_SELECTION 为隐藏无效选项模式
	 */
	private int displayMode = HIDE_DISABLE_SELECTION;
	public static final int SHOW_DISABLE_SELECTION = 0;
	public static final int HIDE_DISABLE_SELECTION = 1;
}
