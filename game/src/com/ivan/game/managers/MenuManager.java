package com.ivan.game.managers;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.ivan.game.game.GameCanvas;
import com.ivan.game.logger.GameLogger;
import com.ivan.game.unit.Hero;
import com.ivan.game.unit.Item;
import com.ivan.game.unit.Magic;
import com.ivan.game.unit.Msg;
import com.ivan.game.unit.Npc;

/*
 * menu								select
 * 1������Ϸ���˵�						1������Ϸ
 * 									2��ȡ����
 * 									3�˳���Ϸ
 * 2���˵�							1״̬
 * 									2����
 * 									3����
 * 									4ϵͳ
 */
public class MenuManager {
	public MenuManager()
	{
		menu = 1;
		select = 1;
		/*
		 * ����˵���ͼ
		 */
		cursor = new ImageIcon("data/images/cursor.gif").getImage();
		mainmenuimage = new ImageIcon("data/images/menu2.gif").getImage();
		loginmenuimage = new ImageIcon("data/images/head.gif").getImage();
		itemmenuimage = new ImageIcon("data/images/menu4.gif").getImage();
		systemmenuimage = new ImageIcon("data/images/menu6.gif").getImage();
		herostateimage = new ImageIcon("data/images/hero/state.gif").getImage();
		magicmenuimage = new ImageIcon("data/images/menu5.gif").getImage();
		buyitemimage = new ImageIcon("data/images/menu10.gif").getImage();
		buynumimage = new ImageIcon("data/images/buynum.gif").getImage();
	}
	public void setMenu(int m,int n)
	{
		menu = m;
		select = n;
	}
	public int getMenu()
	{
		return menu;
	}
	public int getSelect()
	{
		return select;
	}
	public void Select(boolean b)
	{
		if (b == true) {
			//java.awt.Toolkit.getDefaultToolkit();
			if(menu == 1)//�ǿ�ʼ����˵�
			{
				if (select == 1) //��ʼ����Ϸ
				{
					display = GameManager.SHOW_MAP;
					dplmgr.setDisplay(GameManager.SHOW_MAP);
				}
				if (select == 2) //��ȡ����
				{
					File f = new File("data/save/IvAn.sav");
					if (!f.exists()) 
					{
						msgmgr.showMessage(new Msg("����û�м�¼."));
						return;
					}
					else 
					{
						display = GameManager.SHOW_MAP;
						dplmgr.setDisplay(GameManager.SHOW_MAP);
					}
				}
				if (select == 3) //�˳�
				{
					msgmgr.showMessage(new Msg("����:Ivan QQ:175300750"));
					dplmgr.playExitMovie();
					display = GameManager.GAME_FINISH;
					dplmgr.setDisplay(GameManager.GAME_FINISH);
				}
			}
			else if(menu == 0)
			{
				menu = 2;
				select = 1;
			}
			else if(menu == 2)//�����˵�
			{
				if(select == 4)//ϵͳ
				{
					menu = 6;
					select = 1;
				}
				else if(select == 2)//����
				{
					menu = 4;
					select = 1;
					page = 1;
					pages = (hero.getItemNameList().size()-1)/10+1;
				}
				else if(select == 1)//״̬
				{
					menu = 7;
				}
				else if(select == 3)//����
				{
					menu = 5;
					select = 1;
					selectmagic = 0;
				}
			}
			else if(menu == 6)//ϵͳ�˵�
			{
				if(select == 1)//����
				{
					dplmgr.setDisplay(GameManager.SAVE_GAME);
					menu = 0;
					select = 0;
				}
				else if(select == 2)//�˳�
				{
					dplmgr.setDisplay(GameManager.EXIT_GAME);
					menu = 1;
					select = 1;
				}
			}
			else if(menu == 4)//���߲˵�
			{
				if(hero.getItemList().size() > 0)
				{
					pages = (hero.getItemNameList().size()-1)/10+1;
					if(page > pages)
						page = pages;
					int optionnum = 0;
					if(page == pages)
					{
						optionnum = hero.getItemNameList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					else
						optionnum = 10;

					if(select > optionnum)
					{
						select = optionnum;
					}

					int choose = select + (page-1)*10 -1;
					boolean yes = showYesNoMenu(gamecanvas,"ʹ�� ȡ��");
					if(yes)
					{
						Item item = itmmgr.getItem((String)hero.getItemList().get(choose));
						if(hero.useItem(item,choose))
							msgmgr.showMessage(new Msg(item.getInstruction()));
						else
							msgmgr.showMessage(new Msg("���ڲ���ʹ��."));
						//sleep(200);
					}
					else
					{
					
					}
					//System.out.println("select item " + choose);
				}
			}
			else if(menu == 5)//���м��ܲ˵�
			{
				if(hero.getMagicList().size() > 0)
				{
					selectmagic = select - 1;
					menu = 8;
					select = 1;
					pages = (hero.getMagicList().size()-1)/10+1;
					page = 1;
				}
			}
			else if(menu == 8)//���м��ܲ˵�
			{
				if(showYesNoMenu(gamecanvas,"ʹ�� ȡ��"))
				{
					int choose =hero.getMagicList().size() -( select + (page-1)*10 -1) -1;
					//System.out.println(choose);
					for(int i = 0; i < 4; i++)
					{
						if(hero.getBattleMagic()[i] != null)
						{
							if(hero.getBattleMagic()[i].getName().equals(hero.getMagicNameList().get(choose)))
							{
								msgmgr.showMessage(new Msg("�ü����Ѿ�ʹ��"));
								//sleep(200);
								return;
							}
						}
					}
					hero.getBattleMagic()[selectmagic] = 
						mgcmgr.getMagic((String)hero.getMagicList().get(choose));
					menu = 5;
					select = selectmagic + 1;
				}
			}
			else if(menu == 9)
			{
				if(select == 1)//buy
				{
					if(seller.getSellItemList().size() == 0)
					{
						msgmgr.showMessage(new Msg(seller.getName()+":��û����Ʒ���Գ���."));
						//sleep(200);
					}
					else
					{
						menu = 10;
						select = 1;
						page = 1;
					}
				}else if(select == 2)//sell
				{
					if(hero.getItemList().size() == 0)
					{
						msgmgr.showMessage(new Msg(seller.getName()+":��û����Ʒ."));
						//sleep(200);
					}
					else
					{
						menu = 11;
						select = 1;
						page = 1;
					}
				}else if(select == 3)
				{
					menu = 0;
					select = 0;
					dplmgr.setDisplay(GameManager.SHOW_MAP);
				}
			}
			else if(menu == 10)
			{
				int money = hero.getMoney();
				int n;
				int choose = select + (page-1)*10 -1;
				Item item = (Item)seller.getSellItemList().get(choose);
				if(money >= item.getPrice())
				{
					int max = 0;
					if(item.getPrice() > 0)
					{
						max = money / item.getPrice();
						int index = hero.hasItem(item);
						if(index != -1)
						{
							int canAdd = 99 - Integer.parseInt((String)hero.getItemNumList().get(index));
							max = Math.min(max, canAdd);
						}
					}
					else
					{
						max = 99;
						GameLogger.logger.warn("��Ʒ�۸񲻺���!:"+ item.getFileName());
					}
					
					n = showBuyNumMenu(gamecanvas,max);
					if(n != 0)
					{
						for(int i = 0; i < n; i++)
						{
							hero.addItem(item);
						}
						hero.useMoney(n * item.getPrice());
					}
				}
				else//Ǯ����
				{
					msgmgr.showMessage(new Msg(seller.getName()+":���Ǯ����.."));
					//sleep(200);
				}
			}
			else if(menu == 11)
			{
				if(hero.getItemNumList().size() == 0)
					return;
				int n = 0;
				int choose = select + (page-1)*10 -1;
				int max = Integer.parseInt((String)hero.getItemNumList().get(choose));
				n = showBuyNumMenu(gamecanvas,max);
				hero.addMoney(n * itmmgr.getItem(
						(String)hero.getItemList().get(choose)).getPrice() / 2);
				hero.removeItem(choose,n);
				
			}
		}
		else if(b == false)
		{
			if(menu == 2)
			{
				menu = 0;
				select = 0;
				dplmgr.setDisplay(GameManager.SHOW_MAP);
			}
			else if(menu == 6)
			{
				menu = 2;
				select = 4;
			}
			else if(menu == 4)
			{
				menu = 2;
				select = 2;
			}
			else if(menu == 7)
			{
				menu = 2;
				select = 1;
			}
			else if(menu == 5)
			{
				menu = 2;
				select = 3;
				selectmagic = 0;
			}
			else if(menu == 8)
			{
				menu = 5;
				select = selectmagic + 1;
			}
			else if(menu == 9)
			{
				menu = 0;
				select = 0;
				dplmgr.setDisplay(GameManager.SHOW_MAP);
			}
			else if(menu == 10)
			{
				menu = 9;
				select = 1;
			}
			else if(menu == 11)
			{
				menu = 9;
				select = 2;
			}
		}
	}
	public void draw(Graphics canvas,int d)
	{
		if(d == GameManager.SHOW_MENU || 
				d == GameManager.SHOW_MAP_MENU ||
				d == GameManager.SHOW_BATTLE_MENU ||
				d == GameManager.SHOW_BUYMENU)
		{
			if(menu == 1)
			{
				paintLogin(canvas);
			}
			else if(menu == 2)
			{
				paintMainMenu(canvas);
			}
			else if(menu == 6)
			{
				paintSystemMenu(canvas);
			}
			else if(menu == 4)
			{
				paintItemMenu(canvas,page);
			}
			else if(menu == 7)
			{
				paintStateMenu(canvas);
			}
			else if(menu == 5)
			{
				paintChooseMagicMenu(canvas);
			}
			else if(menu == 8)
			{
				paintChangeMagicMenu(canvas);
			}
			else if(menu == 9)
			{
				paintBuySellMenu(canvas);
			}
			else if(menu == 10)
			{
				paintBuyMenu(canvas);
			}
			else if(menu == 11)
			{
				paintSellMenu(canvas);
			}
		}
	}
	public void action(char input,int d)
	{
		if(d == GameManager.SHOW_MENU || 
				d == GameManager.SHOW_MAP_MENU ||
				d == GameManager.SHOW_BATTLE_MENU||
				d == GameManager.SHOW_BUYMENU)
		{
			sleep(200);
			display = d;
			if(input == 'j')
			{
				Select(true);
			}
			else if(input == 'k')
			{
				Select(false);
			}
			else if(input == 'h')
			{
				if(d == GameManager.SHOW_MAP_MENU)
				{
					if(menu == 0)
					{
						menu = 2;
						select = 1;
					}
					else if(menu == 2)
					{
						menu = 0;
						select = 0;
						dplmgr.setDisplay(GameManager.SHOW_MAP);
					}
				}
			}
			else if(input == 'w')
			{
				int optionnum = 0;
				if(menu == 1)
				{
					optionnum = 3;
					select--;
					if(select < 1)
					{
						select = optionnum;
					}
				}
				else if(menu == 2)
				{
					optionnum = 4;
					select--;
					if(select < 1)
					{
						select = optionnum;
					}
				}
				else if(menu == 6)
				{
					select--;
					optionnum = 2;
					if(select < 1)
					{
						select = optionnum;
					}
				}
				else if(menu == 5)
				{
					select --;
					if(select < 1)
						select = 4;
				}
				else if(menu == 8)
				{
					pages = (hero.getMagicList().size()-1)/10+1;
					if(page == pages)
					{
						optionnum = hero.getMagicList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					optionnum = 10;
					select--;
					if(select < 1)
					{
						if(page > 1)
						{
							page--;
						}
						
						else
						{
							page = pages;
							optionnum = hero.getMagicList().size()%10;
							if(optionnum == 0)
								optionnum = 10;
						}
						select = optionnum;
					}
				}
				else if(menu == 4)
				{
					pages = (hero.getItemNameList().size()-1)/10+1;
					if(page == pages)
					{
						optionnum = hero.getItemNameList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					optionnum = 10;
					select--;
					if(select < 1)
					{
						if(page > 1)
						{
							page--;
						}
						
						else
						{
							page = pages;
							optionnum = hero.getItemNameList().size()%10;
							if(optionnum == 0)
								optionnum = 10;
						}
						select = optionnum;
					}
				}
				else if(menu == 9)
				{
					optionnum = 3;
					select--;
					if(select < 1)
					{
						select = optionnum;
					}
				}
				else if(menu == 10)
				{
					pages = (seller.getSellItemList().size()-1)/10+1;
					if(page == pages)
					{
						optionnum = seller.getSellItemList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					optionnum = 10;
					select--;
					if(select < 1)
					{
						if(page > 1)
						{
							page--;
						}
						
						else
						{
							page = pages;
							optionnum = seller.getSellItemList().size()%10;
							if(optionnum == 0)
								optionnum = 10;
						}
						select = optionnum;
					}
				}
				else if(menu == 11)//�����˵�
				{
					pages = (hero.getItemList().size()-1)/10+1;
					if(page == pages)
					{
						optionnum = hero.getItemList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					optionnum = 10;
					select--;
					if(select < 1)
					{
						if(page > 1)
						{
							page--;
						}
						
						else
						{
							page = pages;
							optionnum = hero.getItemList().size()%10;
							if(optionnum == 0)
								optionnum = 10;
						}
						select = optionnum;
					}
				}
			}
			else if(input == 's')
			{
				int optionnum = 0;
				if(menu == 1)
				{
					optionnum = 3;
				}
				else if(menu == 2)
				{
					optionnum = 4;
				}
				else if(menu == 6)
				{
					optionnum = 2;
				}
				else if(menu == 5)
				{
					optionnum = 4;
				}
				else if(menu == 4)
				{
					pages = (hero.getItemNameList().size()-1)/10+1;
					if(page == pages)
					{
						optionnum = hero.getItemNameList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					else
						optionnum = 10;
				}
				else if(menu == 8)
				{
					pages = (hero.getMagicList().size()-1)/10+1;
					if(page == pages)
					{
						optionnum = hero.getMagicList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					else
						optionnum = 10;
				}
				else if(menu == 9)
				{
					optionnum = 3;
				}
				else if(menu == 10)
				{
					pages = (seller.getSellItemList().size()-1)/10+1;
					if(page == pages)
					{
						optionnum = seller.getSellItemList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					else
						optionnum = 10;
				}
				else if(menu == 11)
				{
					pages = (hero.getItemList().size()-1)/10+1;
					if(page == pages)
					{
						optionnum = hero.getItemList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					else
						optionnum = 10;
				}
				select++;
				if(select > optionnum)
				{
					select = 1;
					page++;
					if(page > pages)
						page = 1;
				}
			}
			else if(input == 'a')
			{
				int optionnum = 0;
				if(menu == 4)
				{
					page--;
					pages = (hero.getItemNameList().size()-1)/10+1;
					if(page < 1)
						page = pages;
					
					
					if(page == pages)
					{
						optionnum = hero.getItemNameList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					else
						optionnum = 10;
					if(select > optionnum)
					{
						select = optionnum;
					}
				}
				else if(menu == 8)
				{
					page--;
					pages = (hero.getMagicList().size()-1)/10+1;
					if(page < 1)
						page = pages;
					
					
					if(page == pages)
					{
						optionnum = hero.getMagicList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					else
						optionnum = 10;
					if(select > optionnum)
					{
						select = optionnum;
					}
				}
				else if(menu == 10)
				{
					page--;
					pages = (seller.getSellItemList().size()-1)/10+1;
					if(page < 1)
						page = pages;
					
				
					if(page == pages)
					{
						optionnum = seller.getSellItemList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					else
						optionnum = 10;
					if(select > optionnum)
					{
						select = optionnum;
					}
				}
				else if(menu == 11)
				{
					page--;
					pages = (hero.getItemList().size()-1)/10+1;
					if(page < 1)
						page = pages;
					
					
					if(page == pages)
					{
						optionnum = hero.getItemList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					else
						optionnum = 10;
					if(select > optionnum)
					{
						select = optionnum;
					}
				}
				
			}
			else if(input == 'd')
			{
				int optionnum = 0;
				if(menu == 4)
				{
					page++;
					pages = (hero.getItemNameList().size()-1)/10+1;
					if(page > pages)
						page = 1;
					
					if(page == pages)
					{
						optionnum = hero.getItemNameList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					else
						optionnum = 10;
					if(select > optionnum)
					{
						select = optionnum;
					}
				}
				else if(menu == 8)
				{
					page++;
					pages = (hero.getMagicList().size()-1)/10+1;
					if(page > pages)
						page = 1;
					
					if(page == pages)
					{
						optionnum = hero.getMagicList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					else
						optionnum = 10;
					if(select > optionnum)
					{
						select = optionnum;
					}
				}
				else if(menu == 10)
				{
					page++;
					pages = (seller.getSellItemList().size()-1)/10+1;
					if(page > pages)
						page = 1;
					if(page == pages)
					{
						optionnum = seller.getSellItemList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					else
						optionnum = 10;
					if(select > optionnum)
					{
						select = optionnum;
					}
				}
				else if(menu == 11)
				{
					page++;
					pages = (hero.getItemList().size()-1)/10+1;
					if(page > pages)
						page = 1;
					if(page == pages)
					{
						optionnum = hero.getItemList().size()%10;
						if(optionnum == 0)
							optionnum = 10;
					}
					else
						optionnum = 10;
					if(select > optionnum)
					{
						select = optionnum;
					}
				}
			}
		}
	}
	/*
	 * ���˵�
	 */
	public void paintMainMenu(Graphics canvas)
	{
		
		int x = 175;
		int y = 70;
		canvas.drawImage(mainmenuimage,x,y,null);
		if(select == 1)
		{
			canvas.drawImage(cursor,x+5,y+15,null);
		}
		else if(select == 2)
		{
			canvas.drawImage(cursor,x+5,y+35,null);
		}
		else if(select == 3)
		{
			canvas.drawImage(cursor,x+5,y+57,null);
		}
		else if(select == 4)
		{
			canvas.drawImage(cursor,x+5,y+77,null);
		}
	}
	/*
	 * ϵͳѡ��˵�
	 */
	public void paintSystemMenu(Graphics canvas)
	{
		
		canvas.drawImage(systemmenuimage,100,105,null);
		if(select == 1)
		{
			canvas.drawImage(cursor,110,113,null);
		}
		else if(select == 2)
		{
			canvas.drawImage(cursor,110,126,null);
		}
	}
	/*
	 * ��ͷ�˵�
	 */
	public void paintLogin(Graphics canvas)
	{
		
		canvas.drawImage(loginmenuimage,0,0,null);
		if(select == 1)
		{
			canvas.drawImage(cursor,80,123,null);
			canvas.drawString("��ʼ����Ϸ",100,230);
		}
		else if(select == 2)
		{
			canvas.drawImage(cursor,80,139,null);
			canvas.drawString("�Ӽ�¼��ʼ",100,230);
		}
		else if(select == 3)
		{
			canvas.drawImage(cursor,80,155,null);
			canvas.drawString("��������?",100,230);
		}
	}
	/*
	 * ��Ʒ�˵�
	 */
	public void paintItemMenu(Graphics canvas,int page)
	{
		canvas.fillRect(0,0,260,160);
		canvas.drawImage(itemmenuimage,0,0,null);
		canvas.setColor(Color.WHITE);
		//canvas.drawString("��Ʒ�б�",100,20);
		canvas.setColor(Color.BLACK);
		ArrayList itemlist = hero.getItemNameList();
		ArrayList itemnumlist = hero.getItemNumList();
		
		
		int num = itemlist.size();
		
		pages = (hero.getItemNameList().size()-1)/10+1;
		if(page > pages)
			page--;
		int optionnum = 0;
		if(page == pages)
		{
			optionnum = hero.getItemNameList().size()%10;
			if(optionnum == 0)
				optionnum = 10;
		}
		else
			optionnum = 10;
		if(select > optionnum)
		{
			select = optionnum;
		}
		
		int showindex = (page-1)*10;
		int shownum = 0;
		if(page == pages)
		{
			shownum = num%10;
		}
		else
		{
			shownum = 10;
		}
		if(shownum == 0)
			shownum = 10;
		if(num == 0)
		{
			canvas.drawString("û����Ʒ.",100,100);
			return;
		}
		else
		{
			for(int i = 0; i < shownum; i++)
			{
				canvas.drawString((String)itemlist.get(showindex+i),68,54+17*i);
				canvas.drawString("x"+(String)itemnumlist.get(showindex+i),180,54+17*i);
				canvas.drawImage(itmmgr.getItem(
						(String)hero.getItemList().get(showindex+i)).getImage(),
						39,42+17*i,null);
			}
			//canvas.drawImage(cursor,45,45+(select-1)*17,null);
			
			int scrollheight = 160/pages;
			canvas.drawRect(210,50,8,160);
			canvas.fillRect(212,50+(page-1)*scrollheight,5,scrollheight);
			
			canvas.setColor(Color.ORANGE);
			canvas.drawRect(65,42+(select-1)*17,140,14);
			
			//canvas.fillRect(30,230,198,25);
			int choose = select + (page-1)*10 -1;
			canvas.setColor(Color.BLACK);
			canvas.drawString(itmmgr.getItem(
					(String)hero.getItemList().get(choose)).getInstruction()
					,40,245);
		}
	}
	public void paintChooseMagicMenu(Graphics canvas)
	{
		canvas.setColor(Color.BLACK);
		canvas.drawImage(magicmenuimage,0,0,null);
		Magic[] magic = hero.getBattleMagic();
		for(int i = 0; i < 4; i++)
		{
			if(magic[i] != null)
			{
				canvas.setColor(magic[i].getMagicColor());
				canvas.drawString(magic[i].getName(),30,64+i*13);
			}
			else
			{
				canvas.drawString("---------",30,64+i*13);
			}
		}
		canvas.setColor(Color.ORANGE);
		canvas.drawRect(25,53+(select-1)*13,90,13);
		canvas.setColor(Color.BLACK);
		if(magic[select - 1] != null)
		{
			String info = magic[select -1].getInfo()+
			"[����:"+magic[select -1].getPropertyString()+"]";
			canvas.setColor(magic[select -1].getMagicColor());
			if(info.length() < 16)
				canvas.drawString(info,30,223);
			else
			{
				canvas.drawString(info.substring(0, 16),30,223);
				canvas.drawString(info.substring(16),30,240);
			}
		}
		
		ArrayList magiclist = hero.getMagicList();
		ArrayList magicnamelist = hero.getMagicNameList();
		
		ArrayList inverselist = new ArrayList();
		for(int i = magicnamelist.size() -1; i >= 0; i--)
		{
			inverselist.add(magicnamelist.get(i));
		}
		int shownum = 0;
		if(magiclist.size()>9)
		{
			shownum = 10;
		}
		else
		{
			shownum = magiclist.size();
		}
		if(shownum == 0)
		{
			canvas.drawString("û��ѧ������",140,130);
			return;
		}
		for(int i = 0; i < shownum; i++)
		{
			canvas.setColor(mgcmgr.getMagicByName((String)inverselist.get(i)).getMagicColor());
			canvas.drawString((String)inverselist.get(i),150,63+i*14);
		}
		int scrollheight = 140/((magiclist.size()-1)/10 + 1);
		canvas.drawRect(230,52,8,140);
		canvas.fillRect(232,52,5,scrollheight);
	}
	public void paintChangeMagicMenu(Graphics canvas)
	{
		canvas.setColor(Color.BLACK);
		int choose =hero.getMagicList().size() -( select + (page-1)*10 -1) -1;
		canvas.drawImage(magicmenuimage,0,0,null);
		Magic[] magic = hero.getBattleMagic();
		for(int i = 0; i < 4; i++)
		{
			if(magic[i] != null)
			{
				canvas.setColor(magic[i].getMagicColor());
				canvas.drawString(magic[i].getName(),30,64+i*13);
			}
			else
			{
				canvas.drawString("---------",30,64+i*13);
			}
		}
		canvas.setColor(Color.ORANGE);
		canvas.drawRect(25,53+(selectmagic)*13,90,13);
		canvas.setColor(Color.BLACK);
		
		ArrayList magiclist = hero.getMagicList();
		ArrayList magicnamelist = hero.getMagicNameList();
		
		ArrayList inverselist = new ArrayList();
		for(int i = magicnamelist.size() -1; i >= 0; i--)
		{
			inverselist.add(magicnamelist.get(i));
		}
		
		int shownum = 0;
		if(page == pages)
		{
			shownum = magiclist.size()%10;
		}
		else
		{
			shownum = 10;
		}
		if(shownum == 0)
			shownum = 10;
		
		int showindex = (page-1)*10;
	
		for(int i = 0; i < shownum; i++)
		{
			canvas.setColor(mgcmgr.getMagicByName((String)inverselist.get(showindex+i)).getMagicColor());
			canvas.drawString((String)inverselist.get(showindex+i),150,63+14*i);
		}
		int scrollheight = 140/((magiclist.size()-1)/10 + 1);
		canvas.setColor(Color.BLACK);
		canvas.drawRect(230,52,8,140);
		canvas.fillRect(232,52+(page-1)*scrollheight,5,scrollheight);
		canvas.setColor(Color.BLUE);
		canvas.drawRect(147,51+14*(select-1),80,14);
		
		canvas.setColor(Color.BLACK);
		
		
		String info = mgcmgr.getMagic((String)magiclist.get(choose)).getInfo()+
		"[����:"+mgcmgr.getMagic((String)magiclist.get(choose)).getPropertyString()+"]";
		canvas.setColor(Color.BLACK);
		if(info.length() < 16)
			canvas.drawString(info,30,223);
		else
		{
			canvas.drawString(info.substring(0, 16),30,223);
			canvas.drawString(info.substring(16),30,240);
		}
	}
	/*
	 * ����״̬�˵�
	 */
	public void paintStateMenu(Graphics canvas)
	{
		canvas.drawImage(herostateimage,0,0,null);
		canvas.setColor(Color.BLACK);
		
		canvas.drawString(new Integer(hero.getBattleState().getHp())+"/"+
				new Integer(hero.getBattleState().getHpmx())
				,40,220);
		canvas.drawImage(hero.getBattleImage()[0],20,20,null);
		canvas.drawString(new Integer(hero.getBattleState().getMp()).toString() + "/" + 
				new Integer(hero.getBattleState().getMpmx()),40,244);
		
		canvas.drawString(new Integer(hero.getBattleState().getStr()).toString(),180,44);
		
		canvas.drawString(new Integer(hero.getBattleState().getMstr()).toString(),180,71);
		
		canvas.drawString(new Integer(hero.getBattleState().getDef()).toString(),180,97);
		
		canvas.drawString(new Integer(hero.getBattleState().getMdef()).toString(),180,124);
		
		canvas.drawString(new Integer(hero.getBattleState().getHs()).toString(),180,149);
		
		canvas.drawString(new Integer(hero.getBattleState().getJouk()).toString(),180,176);
		
		canvas.drawString(new Integer(hero.getExp()).toString(),190,202);
		
		canvas.drawString(new Integer(hero.getExpNext()-hero.getExp()).toString(),160,245);
		
		canvas.drawString(new Integer(hero.getLevel()).toString(),40,194);
	}
	public void paintBuySellMenu(Graphics canvas)
	{
		canvas.setColor(Color.WHITE);
		canvas.fillRect(10,5,41,53);
		canvas.setColor(Color.BLACK);
		canvas.drawRect(10, 5, 41, 53);
		canvas.drawLine(52, 6, 52, 59);
		canvas.drawLine(11, 59, 52, 59);
		
		canvas.setColor(Color.BLACK);
		canvas.drawString("����",17,18);
		canvas.drawString("����",17,35);
		canvas.drawString("ȡ��",17,52);
		
		canvas.setColor(Color.RED);
		canvas.drawRect(11, 6+17*(select - 1), 39, 17);
	}
	public void paintBuyMenu(Graphics canvas)//����˵�
	{
		//System.out.println("page = "+ page+" select = "+select+" money = "+hero.getMoney());
		canvas.drawImage(buyitemimage,0,0,null);
		canvas.drawImage(seller.getImages()[0],12,12,null);
		canvas.setColor(Color.BLACK);
		
		canvas.drawString(seller.getMsg(),20,58);
		canvas.drawString("��:", 160, 248);
		canvas.setColor(Color.RED);
		canvas.drawString(""+hero.getMoney()+"G",176,248);
		canvas.setColor(Color.BLACK);
		
		ArrayList itemlist = seller.getSellItemList();
		pages = (itemlist.size()-1)/10+1;
		if(page > pages)
			page--;
		
		int shownum = 0;
		if(page == pages)
		{
			shownum = itemlist.size()%10;
		}
		else
		{
			shownum = 10;
		}
		if(shownum == 0 && itemlist.size() > 0)
			shownum = 10;
		int showindex = (page-1)*10;
		
		Item item;
		String price;
		
		//npc��Ʒ
		for(int i = 0; i < shownum; i++)
		{
			if(i % 2 == 0)
			{
				canvas.setColor(Color.ORANGE);
			}
			else
			{
				canvas.setColor(new Color(160,222,74));
			}
			
			item = (Item)itemlist.get(showindex + i);
			
			price = "" + item.getPrice() + "g";
			int pos = 115 - price.length()*8;
			canvas.drawString(item.getName(), 20, 81+17*i);
			canvas.drawString(price, pos, 81+17*i);
		}
		
		shownum = hero.getItemNameList().size();
		if(shownum > 10)
		{
			shownum = 10;
		}
		//������Ʒ
		for(int i = 0; i < shownum; i++)
		{
			if(i % 2 == 0)
			{
				canvas.setColor(Color.ORANGE);
			}
			else
			{
				canvas.setColor(new Color(160,222,74));
			}
			canvas.drawString((String)hero.getItemNameList().get(i), 152, 61+17*i);
			canvas.drawString("x"+(String)hero.getItemNumList().get(i), 220, 61+17*i);
		}
		
		//ѡ���
		canvas.setColor(Color.ORANGE);
		canvas.drawRect(18, 53+17*select, 97, 14);
		int choose = select + (page-1)*10 -1;
		//��Ʒ˵��
		if(select % 2 == 0)
		{
			canvas.setColor(Color.ORANGE);
		}
		else
		{
			canvas.setColor(new Color(160,222,74));
		}
		canvas.drawString(((Item)itemlist.get(choose)).getInstruction(), 49, 30);
	}
	public void paintSellMenu(Graphics canvas)//�����˵�
	{
		//System.out.println("page = "+ page+" select = "+select+" money = "+hero.getMoney());
		canvas.drawImage(buyitemimage,0,0,null);
		canvas.drawImage(seller.getImages()[0],12,12,null);
		canvas.setColor(Color.BLACK);
		
		canvas.drawString("��Ҫ����ʲô",20,58);
		canvas.drawString("��:", 160, 248);
		canvas.setColor(Color.RED);
		canvas.drawString(""+hero.getMoney()+"G",176,248);
		canvas.setColor(Color.BLACK);
		
		ArrayList itemlist = hero.getItemNameList();
		ArrayList itemnumlist = hero.getItemNumList();
		
		
		int num = itemlist.size();
		
		pages = (hero.getItemNameList().size()-1)/10+1;
		if(page > pages)
			page--;
		int optionnum = 0;
		if(page == pages)
		{
			optionnum = hero.getItemNameList().size()%10;
			if(optionnum == 0)
				optionnum = 10;
		}
		else
			optionnum = 10;
		if(select > optionnum)
		{
			select = optionnum;
		}
		
		int showindex = (page-1)*10;
		int shownum = 0;
		if(page == pages)
		{
			shownum = num%10;
		}
		else
		{
			shownum = 10;
		}
		if(shownum == 0)
			shownum = 10;
		if(num == 0)
		{
			canvas.drawString("û����Ʒ.",100,100);
			return;
		}
		else
		{
			for(int i = 0; i < shownum; i++)
			{
				if(i % 2 == 0)
				{
					canvas.setColor(Color.ORANGE);
				}
				else
				{
					canvas.setColor(new Color(160,222,74));
				}
				canvas.drawString((String)itemlist.get(showindex+i),152, 61+17*i);
				canvas.drawString("x"+(String)itemnumlist.get(showindex+i),220, 61+17*i);
			}
			
			int choose = select + (page-1)*10 -1;
			canvas.setColor(Color.BLACK);
			canvas.drawString(itmmgr.getItem(
					(String)hero.getItemList().get(choose)).getInstruction()
					,49, 30);
		}
		shownum = seller.getSellItemList().size();
		if(shownum > 10)
			shownum = 10;
		//npc��Ʒ
		Item item;
		String price;
		itemlist = seller.getSellItemList();
		for(int i = 0; i < shownum; i++)
		{
			if(i % 2 == 0)
			{
				canvas.setColor(Color.ORANGE);
			}
			else
			{
				canvas.setColor(new Color(160,222,74));
			}
			
			item = (Item)itemlist.get(i);
			
			price = "" + item.getPrice() + "g";
			int pos = 115 - price.length()*8;
			canvas.drawString(item.getName(), 20, 81+17*i);
			canvas.drawString(price, pos, 81+17*i);
		}
		//ѡ���
		canvas.setColor(Color.ORANGE);
		canvas.drawRect(145, 33+17*select, 95, 14);
		int choose = select + (page-1)*10 -1;
		//��Ʒ˵��
		if(select % 2 == 0)
		{
			canvas.setColor(Color.ORANGE);
		}
		else
		{
			canvas.setColor(new Color(160,222,74));
		}
		canvas.drawString(itmmgr.getItem(
				(String)hero.getItemList().get(choose)).getInstruction(), 49, 30);
	}
	/*
	 * @param max ���Թ�����������
	 * @return n ��������
	 */
	public int showBuyNumMenu(GameCanvas g, int max)
	{
		gamecanvas = g;
		char input = '#';
		Graphics canvas = gamecanvas.getGraphics();
		
		Item item = null;
		if(menu == 10)
		{
			ArrayList itemlist = seller.getSellItemList();
			int choose = select + (page-1)*10 -1;
			item = (Item)itemlist.get(choose);
		}
		else if(menu == 11)
		{
			int choose = select + (page-1)*10 -1;
			item = itmmgr.getItem(
					(String)hero.getItemList().get(choose));
		}
		
		
		
		int s = 1;
		int n = 0;
		int cost = 0;
		
		canvas.drawImage(buynumimage, 77, 108, null);
		canvas.drawImage(item.getImage(), 84, 115, null);
		canvas.drawString(item.getName(), 102, 125);
		
		if(menu == 10)
			cost = n * item.getPrice();
		else if(menu == 11)
			cost = (int)(n * item.getPrice() /2);
		canvas.setColor(Color.DARK_GRAY);
		canvas.drawString(""+cost+"g", 85, 145);
		canvas.drawString(""+n, 130, 145);
		if(s == 1)
			canvas.drawRect(152, 114, 27, 17);
		else
			canvas.drawRect(152, 131, 27, 17);
		
		
		do{
			input = gamecanvas.getInput(true);
			if(input != ' ')
			{
				if(input == 'a')
				{
					n--;
					if(n < 1)
						n = max;
				}
				else if(input == 'd')
				{
					n++;
					if(n > max)
						n = 1;
				}
				else if(input == 'j')
				{
					if(s != 1)
						n = 0;
					//sleep(200);
					break;
				}
				else if(input == 'k')
				{
					n = 0;
					//sleep(200);
					break;
				}
				else if(input == 'w')
				{
					s = 3 - s;
					//sleep(200);
				}
				else if(input == 's')
				{
					s = 3 - s;
					//sleep(200);
				}
				canvas.drawImage(buynumimage, 77, 108, null);
				canvas.drawImage(item.getImage(), 84, 115, null);
				canvas.drawString(item.getName(), 102, 125);
				
				if(menu == 10)
					cost = n * item.getPrice();
				else if(menu == 11)
					cost = (int)(n * item.getPrice() /2);
				canvas.setColor(Color.DARK_GRAY);
				canvas.drawString(""+cost+"g", 85, 145);
				canvas.drawString(""+n, 130, 145);
				if(s == 1)
					canvas.drawRect(152, 114, 27, 17);
				else
					canvas.drawRect(152, 131, 27, 17);
				sleep(10);
			}
			sleep(10);
		}while(true);
		return n;
	}
	/*
	 * yes no �˵�
	 * @param s ���������ʽΪ"XX  YY",XXΪ��,YYΪ��
	 */
	public boolean showYesNoMenu(GameCanvas g,String s)
	{
		gamecanvas = g;
		boolean b = true;
		char input = '#';
		Graphics canvas = gamecanvas.getGraphics();
		canvas.setColor(Color.WHITE);
		canvas.fillRect(92,115,65,20);
		canvas.setColor(Color.YELLOW);
		canvas.drawRect(93,116,63,18);
		do{
			input = gamecanvas.checkInput();
			canvas.setColor(Color.BLACK);
			canvas.drawString(s,96,129);
			if(b)
			{
				canvas.setColor(Color.ORANGE);
				canvas.drawRect(94,117,25,15);
				canvas.setColor(Color.WHITE);
				canvas.drawRect(129,117,25,15);
			}
			else
			{
				canvas.setColor(Color.ORANGE);
				canvas.drawRect(129,117,25,15);
				canvas.setColor(Color.WHITE);
				canvas.drawRect(94,117,25,15);
			}
			
			if(input != ' ')
			{
				if(input == 'a')
				{
					b = true;
					gamecanvas.getInput(true);
				}
				else if(input == 'd')
				{
					b = false;
					gamecanvas.getInput(true);
				}
				else if(input == 'j')
				{
					gamecanvas.getInput(true);
					break;
				}
				else if(input == 'k')
				{
					gamecanvas.getInput(true);
					b = false;
					break;
				}
			}
			
			sleep(10);
		}while(true);
		return b;
	}
	public int getDisplay(int d)
	{
		if(display != d)
			return display;
		else
			return d;
	}
	public void sleep(int ms)
	{
		try{
			Thread.sleep(ms);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	public void setMsgMgr(MessageManager m)
	{
		msgmgr = m;
	}
	public void setHero(Hero h)
	{
		hero = h;
	}
	public void setDisplayMgr(DisplayManager m)
	{
		dplmgr = m;
	}
	public void setItemManager(ItemManager m)
	{
		itmmgr = m;
	}
	public void setGameCanvas(GameCanvas g)
	{
		gamecanvas = g;
	}
	public void setMagicMagr(MagicManager m)
	{
		mgcmgr = m;
	}
	public void setSeller(Npc n)
	{
		seller = n;
	}

	private int display = 0;
	private int menu;
	private int select;
	private int page = 1;
	private int pages = 1;
	private Image cursor;
	private Npc seller;
	private MessageManager msgmgr;
	private ItemManager itmmgr;
	private MagicManager mgcmgr;
	private DisplayManager dplmgr;
	private Hero hero;
	private Image mainmenuimage;
	private Image loginmenuimage;
	private Image itemmenuimage;
	private Image systemmenuimage;
	private Image herostateimage;
	private Image magicmenuimage;
	private Image buyitemimage;
	private Image buynumimage;
	private GameCanvas gamecanvas;
	private int selectmagic = 0;
}