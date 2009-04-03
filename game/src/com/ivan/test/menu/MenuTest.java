package com.ivan.test.menu;

import javax.swing.JFrame;

import com.ivan.menu.Menu;
import com.ivan.menu.MenuItem;
import com.ivan.menu.MenuSystem;
/*
 * һ���˵�ʹ������
 */
public class MenuTest {
	public static void main(String[] args)
	{
		MenuTestFrame frame = new MenuTestFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		/*
		 * ���湹��һ���˵�:
		 *  ���˵�
		 * |item0|
		 * |item1|
		 * |item2|(����ѡ��)
		 * |item3|	    �Ӳ˵�
		 * |item4| -> |item5|(��Чѡ��)
		 * 			  |item6|
		 * 			  |item7|
		 */
		//�������˵�
		Menu rootmenu = new Menu("root");
		MenuItem[] il = new MenuItem[5];
		for(int i = 0; i < 5; i++)
		{
			il[i] = new MenuItem();
		}
		rootmenu.setItemList(il, 5);
		//�����Ӳ˵�1
		Menu submenu = new Menu("submenu");
		MenuItem[] sil = new MenuItem[3];
		for(int i = 0; i < 3; i++)
		{
			sil[i] = new MenuItem();
		}
		submenu.setItemList(sil, 3);
		submenu.setFatherMenu(rootmenu);
		//�����Ӳ˵�2
		Menu submenu2 = new Menu("submenu2");
		MenuItem[] sil2 = new MenuItem[10];
		for(int i = 0; i < 10; i++)
		{
			sil2[i] = new MenuItem();
		}
		submenu2.setItemList(sil2,10);
		submenu2.setFatherMenu(rootmenu);
		//�����˵���ϵ,����Ϊ��item4ѡ����Ӳ˵�
		il[4].setSubMenu(submenu);
		//����item2Ϊ����ѡ��,���������Ӳ˵�Ϊsubmenu2
		//il[2].setVisible(false);
		il[2].setSubMenu(submenu2);
		//����item5Ϊ��Чѡ��
		sil[0].setSelectable(false);
		//�����˵�ϵͳ
		MenuSystem ms = new MenuSystem(rootmenu);
		//�򿪲˵�
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
