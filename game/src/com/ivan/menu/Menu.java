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
	 * ���ø��˵�
	 * @param fm ���˵�
	 */
	public void setFatherMenu(Menu fm)
	{
		fatherMenu = fm;
	}
	/*
	 * ��ȡ��Ŀ�б�
	 * @return itemlist ��Ŀ�б�
	 */
	public MenuItem[] getItemList()
	{
		return itemlist;
	}
	/*
	 * ������Ŀ�б�
	 * @param il ��Ŀ�б�����
	 * @param length ��Ŀ�б����鳤��
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
	 * ����Ĭ��ѡ��
	 * @param itemindex Ĭ��ѡ������Ŀ�б��е��±�
	 */
	public void setDefaultItemIndex(int itemindex)
	{
		if(itemindex > length - 1)
		{
			System.out.println("�˵��쳣:�˵�" + getName() + "ָ��Ĭ��ѡ��ʱ,����Խ��,������Ϊ��һ��.");
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
			System.out.println("�˵��쳣:�˵�" + getName() + "ָ����ǰѡ��ʱ,����Խ��,������Ϊ��һ��.");
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
	 * ѡ��,����Ӳ˵�
	 * @return ��ѡ���Ĳ˵���Ŀ
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
	 * CYCLICALABLE_SELECT Ϊѭ��ѡ��ģʽ
	 * NON_CYCLICALABLE_SELECT Ϊ��ѭ��ѡ��ģʽ
	 */
	public static final int CYCLICALABLE_SELECT = 0;
	public static final int NON_CYCLICALABLE_SELECT = 1;
	
	/*
	 * SHOW_DISABLE_SELECTION Ϊ��ʾ��Чѡ��ģʽ
	 * HIDE_DISABLE_SELECTION Ϊ������Чѡ��ģʽ
	 */
	private int displayMode = HIDE_DISABLE_SELECTION;
	public static final int SHOW_DISABLE_SELECTION = 0;
	public static final int HIDE_DISABLE_SELECTION = 1;
}
