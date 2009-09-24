package com.ivan.test.menu;

import javax.swing.JFrame;

import com.ivan.menu.Menu;
import com.ivan.menu.MenuItem;
import com.ivan.menu.MenuSystem;
/*
 * 一个菜单使用例子
 */
public class MenuTest {
	public static void main(String[] args)
	{
		MenuTestFrame frame = new MenuTestFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		/*
		 * 下面构建一个菜单:
		 *  根菜单
		 * |item0|
		 * |item1|
		 * |item2|(隐藏选项)
		 * |item3|	    子菜单
		 * |item4| -> |item5|(无效选项)
		 * 			  |item6|
		 * 			  |item7|
		 */
		//创建根菜单
		Menu rootmenu = new Menu("root");
		MenuItem[] il = new MenuItem[5];
		for(int i = 0; i < 5; i++)
		{
			il[i] = new MenuItem();
		}
		rootmenu.setItemList(il, 5);
		//创建子菜单1
		Menu submenu = new Menu("submenu");
		MenuItem[] sil = new MenuItem[3];
		for(int i = 0; i < 3; i++)
		{
			sil[i] = new MenuItem();
		}
		submenu.setItemList(sil, 3);
		submenu.setFatherMenu(rootmenu);
		//创建子菜单2
		Menu submenu2 = new Menu("submenu2");
		MenuItem[] sil2 = new MenuItem[10];
		for(int i = 0; i < 10; i++)
		{
			sil2[i] = new MenuItem();
		}
		submenu2.setItemList(sil2,10);
		submenu2.setFatherMenu(rootmenu);
		//建立菜单联系,设置为由item4选项打开子菜单
		il[4].setSubMenu(submenu);
		//设置item2为隐藏选项,并设置其子菜单为submenu2
		//il[2].setVisible(false);
		il[2].setSubMenu(submenu2);
		//设置item5为无效选项
		sil[0].setSelectable(false);
		//建立菜单系统
		MenuSystem ms = new MenuSystem(rootmenu);
		//打开菜单
		ms.Open();
		
		System.out.println("now you selected " + ms.getCurrentItem().getName());
		char in;
		while (true) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
			in = frame.getCh();
			if (in == ' ')
				continue;
			else {
				if (in == 'w') {
					ms.selectPrevious();
					System.out.println("now you selected " + ms.getCurrentItem().getName());
				} else if (in == 's') {
					ms.selectNext();
					System.out.println("now you selected " + ms.getCurrentItem().getName());
				} else if(in == 'j')
				{
					if(ms.Select() == null)
					{
						System.out.println("cant select it");
					}
					System.out.println("now you selected " + ms.getCurrentItem().getName());
				} else if(in == 'k')
				{
					ms.back();
					System.out.println("now you selected " + ms.getCurrentItem().getName());
				}
				try {
					Thread.sleep(400);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
