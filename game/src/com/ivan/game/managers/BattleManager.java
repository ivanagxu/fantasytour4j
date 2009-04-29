package com.ivan.game.managers;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.ivan.game.game.GameCanvas;
import com.ivan.game.unit.BattleState;
import com.ivan.game.unit.Enemy;
import com.ivan.game.unit.Hero;
import com.ivan.game.unit.Item;
import com.ivan.game.unit.Magic;
import com.ivan.game.unit.Motion;
import com.ivan.game.unit.Msg;


public class BattleManager
{
	public BattleManager()
	{
		itemmenuimage = new ImageIcon("data/images/menu4.gif").getImage();
		battleground = new ImageIcon("data/images/battlebackground.gif")
				.getImage();
	}

	public void start(Hero h)
	{
		finish = false;
		dplmgr.playMetEnemyMovie();
		hero = h;
		enemy = h.getEnemy();
		estate = enemy.getBattleState();
		ebstate = new BattleState(estate);
		hstate = h.getBattleState();
		hbstate = new BattleState(hstate);
		pages = 1;
		page = 1;
		select = 1;
		menu = 1;
		heroattack = 1;
		delaymagic = new ArrayList();
		battleloop();
	}

	public void battleloop()
	{
		input = '#';
		while (true)
		{
			if (input != ' ' || heroattack == 0)
			{
				action(input);
				drawBattle();
				draw();
				if (finish) break;
			}
			input = gamecanvas.getInput(true);
			sleep(1);
		}
	}

	public boolean checkWinner()
	{
		if (!hbstate.getWin() || !ebstate.getWin())
		{
			if (!hbstate.getWin())
			{
				dplmgr.playFailMovie();
				dplmgr.setDisplay(GameManager.EXIT_GAME);
				msgmgr.showMessage(new Msg("失败乃成功之母,努力!"));
				hero.setBattle(null, false);
				sleep(200);
				finish = true;
				return true;
			}
			else
			{
				double a = 100 * Math.random();
				msgmgr.showMessage(new Msg("战斗胜利!"));

				if (a < enemy.getItemDropProbability())
				{
					msgmgr.showMessage(new Msg("获得物品:"
							+ enemy.getItem(itmmgr).getName()));
					hero.addItem(enemy.getItem(itmmgr));
				}
				int getMoney = enemy.getHp()/100 + 1;
				int getExp = enemy.getStr();
				hero.addMoney(getMoney);
				msgmgr.showMessage(new Msg("获得经验:"
						+ getExp + "点,金:" + getMoney+"G"));
				
				dplmgr.setDisplay(GameManager.SHOW_MAP);
				hero.setBattle(null, false);
				hero.setState(hbstate);
				if (hero.increaseExp(enemy.getStr()))
				{
					msgmgr.showMessage(new Msg("等级提升!"));
				}
				sleep(200);
				finish = true;
				return true;
			}
		}
		return false;
	}

	public void action(char input)
	{
		if (heroattack == 1)
		{
			if (input == 'j')
			{
				if (menu == 1)
				{
					if (select == 1)
					{
						menu = 2;
						select = 1;
					}
					else if (select == 2)
					{
						menu = 3;
						select = 1;
						page = 1;
						pages = (hero.getItemNameList().size() - 1) / 10 + 1;
					}
				}
				else if (menu == 2)
				{
					if (hero.getBattleMagic()[select - 1] != null
							&& hbstate.getMp() >= hero.getBattleMagic()[select - 1]
									.getNeedMana())
					{
						for (int i = 0; i < delaymagic.size(); i++)
						{
							if (((DelayMagic) delaymagic.get(i)).owner == 1 &&
									hero.getBattleMagic()[select - 1]
									.getName()
									.equals(
											((DelayMagic) delaymagic.get(i)).magic
													.getName()))
							{
								msgmgr.showMessage(new Msg("该技能已经使用!"));
								return;
							}
						}
						Item needItem = hero.getBattleMagic()[select - 1].getNeedItem(itmmgr);
						if (needItem != null) {
							int index = hero.hasItem(needItem);
							if (index != -1) {
								hero.removeItem(hero.hasItem(needItem), 1);
							}
							else
							{
								msgmgr.showMessage(new Msg("缺少物品:"+needItem.getName()));
								return;
							}
						}
						hbstate.setMp(hbstate.getMp()
								- hero.getBattleMagic()[select - 1]
										.getNeedMana());
						delaymagic.add(new DelayMagic(
								hero.getBattleMagic()[select - 1], heroattack));
						heroattack = 0;
						menu = 1;
						select = 1;
					}
					else
					{
						if(hero.getBattleMagic()[select - 1] == null)
						{
							msgmgr.showMessage(new Msg("你放弃攻击."));
							sleep(200);
						}
						else
						{
							msgmgr.showMessage(new Msg("MP不足!没能使用技能!"));
							sleep(200);
						}
						draw();
						heroattack = 0;
					}
					// menu = 4;//draw magic motion
				}
				else if (menu == 4)
				{
					if (hero.getBattleMagic()[select - 1] != null)
					{
						// drawMagic(hero.getBattleMagic()[select-1]);
						delaymagic.add(new DelayMagic(
								hero.getBattleMagic()[select - 1], 1));
						heroattack = 0;
						menu = 1;
						select = 1;
					}
					else
					{
						menu = 2;
					}

				}
				else if (menu == 3)
				{
					if (hero.getItemList().size() > 0)
					{
						pages = (hero.getItemNameList().size() - 1) / 10 + 1;
						if (page > pages) page = pages;
						int optionnum = 0;
						if (page == pages)
						{
							optionnum = hero.getItemNameList().size() % 10;
							if (optionnum == 0) optionnum = 10;
						}
						else optionnum = 10;

						if (select > optionnum)
						{
							select = optionnum;
						}

						int choose = select + (page - 1) * 10 - 1;
						boolean yes = showYesNoMenu(gamecanvas, "使用 取消");
						if (yes)
						{
							Item item = itmmgr.getItem((String) hero
									.getItemList().get(choose));
							if (hero.useItem(item, choose, hbstate))
							{
								msgmgr.showMessage(new Msg(item
										.getInstruction()));
								menu = 1;
								select = 1;
								heroattack = 0;
							}
							else msgmgr.showMessage(new Msg("现在不能使用该物品."));
							sleep(200);
						}
						else
						{

						}
						//System.out.println("select item " + choose);
					}
				}
				else if (menu == 5)
				{
					// use item;
				}
			}
			else if (input == 'k')
			{
				if (menu == 2)
				{
					menu = 1;
					select = 1;
				}
				else if (menu == 3)
				{
					menu = 1;
					select = 2;
				}
			}
			else if (input == 'w')
			{
				if (menu == 1)
				{
					select--;
					if (select < 1) select = 2;
				}
				else if (menu == 2)
				{
					select--;
					if (select < 1) select = 4;
				}
				else if (menu == 3)
				{
					int optionnum = 0;
					pages = (hero.getItemNameList().size() - 1) / 10 + 1;
					if (page == pages)
					{
						optionnum = hero.getItemNameList().size() % 10;
						if (optionnum == 0) optionnum = 10;
					}
					optionnum = 10;
					select--;
					if (select < 1)
					{
						if (page > 1)
						{
							page--;
						}

						else
						{
							page = pages;
							optionnum = hero.getItemNameList().size() % 10;
							if (optionnum == 0) optionnum = 10;
						}
						select = optionnum;
					}
				}
			}
			else if (input == 's')
			{
				if (menu == 1)
				{
					select++;
					if (select > 2) select = 1;
				}
				else if (menu == 2)
				{
					select++;
					if (select > 4) select = 1;
				}
				else if (menu == 3)
				{
					int optionnum = 0;
					pages = (hero.getItemNameList().size() - 1) / 10 + 1;
					if (page == pages)
					{
						optionnum = hero.getItemNameList().size() % 10;
						if (optionnum == 0) optionnum = 10;
					}
					else optionnum = 10;
					select++;
					if (select > optionnum)
					{
						select = 1;
						page++;
						if (page > pages) page = 1;
					}
				}
			}
			else if (input == 'a')
			{
				int optionnum = 0;
				if (menu == 3)
				{
					page--;
					if (page < 1) page = pages;

					pages = (hero.getItemNameList().size() - 1) / 10 + 1;
					if (page == pages)
					{
						optionnum = hero.getItemNameList().size() % 10;
						if (optionnum == 0) optionnum = 10;
					}
					else optionnum = 10;
					if (select > optionnum)
					{
						select = optionnum;
					}
				}
			}
			else if (input == 'd')
			{
				int optionnum = 0;
				if (menu == 3)
				{
					page++;
					if (page > pages) page = 1;
					pages = (hero.getItemNameList().size() - 1) / 10 + 1;
					if (page == pages)
					{
						optionnum = hero.getItemNameList().size() % 10;
						if (optionnum == 0) optionnum = 10;
					}
					else optionnum = 10;
					if (select > optionnum)
					{
						select = optionnum;
					}
				}
			}
		}
		else
		{
			// enemy attack;
			DelayMagic eMagic = AIMagicSelect(heroattack);
			if(eMagic != null)
				delaymagic.add(eMagic);
			drawMagic();
			heroattack = 1;
		}
	}
	//ai
	public DelayMagic AIMagicSelect(int heroattack)
	{
		DelayMagic magic;
		ArrayList canUseList = new ArrayList();
		ArrayList emagic = enemy.getMagic(mgcmgr);
		
		boolean can = true;
		if(delaymagic.size() == 0)
			canUseList = emagic;
		else
		{
			//搜索AI能用的技能
			for(int i = 0; i < emagic.size(); i++)
			{
				for(int j = 0; j < delaymagic.size(); j++)
				{
					if(((Magic)emagic.get(i)).getName().
							equals(((DelayMagic)delaymagic.get(j)).magic.getName())
							&& ((DelayMagic)delaymagic.get(j)).owner == 0
					)
					{
						can = false;
						break;
					}
				}
				if(can)
				{
					canUseList.add(emagic.get(i));
				}
				can = true;
			}
		}
		if(canUseList.size() == 0)
			return null;
		
		int selectMagic = (int)(Math.random() * canUseList.size());
		//System.out.println(selectMagic+" "+((Magic)canUseList.get(1)).getName());
		magic = new DelayMagic((Magic)canUseList.get(selectMagic),heroattack);
		
		return magic;
		
		/*
		int selectMagic = (int) Math.random() * emagic.size();
		magic = new DelayMagic((Magic)emagic.get(selectMagic),heroattack);
		
		for (int i = 0; i < delaymagic.size(); i++)
		{
			if (((DelayMagic) delaymagic.get(i)).object == 1 &&
					magic.magic
					.getName()
					.equals(
							((DelayMagic) delaymagic.get(i)).magic
									.getName()))
			{
				//msgmgr.showMessage(new Msg("该技能已经使用!"));
				return null;
			}
		}
		return magic;
		*/
	}
	public void draw()
	{
		if (menu == 1)
		{
			// drawBattle();
		}
		else if (menu == 2)
		{
			drawMagicMenu();
		}
		else if (menu == 3)
		{
			drawItemMenu();
		}
		else if (menu == 4)
		{
			// drawMagic();
			// menu = 1;
			// select = 1;
		}
	}

	public void drawBattle()
	{
		Graphics canvas = doubleBuffer.getGraphics();
		
		canvas.setColor(Color.WHITE);
		canvas.fillRect(0, 0, 260, 260);
		canvas.setColor(Color.BLACK);
		canvas.drawImage(battleground, 0, 0, null);
		Image himage;
		Image eimage;
		if (heroattack == 1)
		{
			himage = hero.getBattleImage()[2];
			eimage = enemy.getImage()[1];
		}
		else
		{
			himage = hero.getBattleImage()[4];
			eimage = enemy.getImage()[2];
		}
		
		canvas.drawImage(himage, 0, 50, null);
		canvas.drawImage(eimage, 160, 50, null);

		/*
		 * hprect
		 */
		canvas.drawRect(10, 20, 100, 10);
		canvas.drawRect(150, 20, 100, 10);
		canvas.drawRect(60, 33, 50, 7);

		int length = (int) Math.round((double) hbstate.getHp()
				/ (double) hbstate.getHpmx() * 100);
		canvas.fillRect(10, 22, length, 7);
		length = (int) Math.round((double) ebstate.getHp()
				/ (double) ebstate.getHpmx() * 100);
		canvas.fillRect(150, 22, length, 7);
		length = (int) Math.round((double) hbstate.getMp()
				/ (double) hbstate.getMpmx() * 50);
		canvas.fillRect(60, 35, length, 4);

		/*
		 * string
		 */
		canvas.drawString("vs", 125, 27);
		canvas.drawString(enemy.getName() + ebstate.getHp() + "/"
				+ ebstate.getHpmx(), 150, 13);
		canvas.drawString(hbstate.getHp() + "/" + hbstate.getHpmx(), 10, 13);
		canvas.drawString("mp:", 10, 42);

		if (menu == 1)
		{
			if (select == 1)
			{
				canvas.drawRect(13, 209, 65, 15);
			}
			else if (select == 2)
			{
				canvas.drawRect(13, 226, 65, 15);
			}
		}
		canvas.dispose();
		this.canvas.drawImage(doubleBuffer,0,0,null);
	}

	public void drawMagicMenu()
	{
		Magic[] magic = hero.getBattleMagic();
		canvas.setColor(Color.BLACK);
		for (int i = 0; i < 4; i++)
		{
			if (magic[i] != null)
			{
				canvas.drawString(magic[i].getName(), 170, 180 + i * 20);
			}
			else
			{
				canvas.drawString("------", 170, 180 + i * 20);
			}
		}
		canvas.drawRect(160, 143 + select * 20, 70, 20);
	}

	public void drawMagic()
	{
		if (delaymagic.size() == 0) return;
		for (int i = 0; i < delaymagic.size(); i++)
		{
			DelayMagic magic = (DelayMagic) delaymagic.get(i);
			if (magic.object == 1)
			{
				magic.delay--;
				Motion motion = magic.magic.getMotion(mtnmgr);
				ArrayList imagelist = motion.getImageList();
				if (motion.getType() == Motion.MOVE_MOTION)
				{
					canvas.setColor(getBgColor(magic.magic));
					for (int x = 70; x < 160; x++)
					{
						drawBattle();
						canvas.drawImage((Image) imagelist.get(0), x, 50, null);
						
						sleep(motion.getTimeSpace());
					}
				}
				else
				{
					canvas.setColor(getBgColor(magic.magic));
					for (int j = 0; j < imagelist.size(); j++)
					{
						drawBattle();
						canvas.drawImage((Image) imagelist.get(j), 150, 50,
								null);
					
						sleep(motion.getTimeSpace());
					}
				}
				MagicEffect(magic);
				drawBattle();
				if (checkWinner()) break;
				draw();
			}
			else if (magic.object == 0)
			{
				magic.delay--;
				Motion motion = magic.magic.getMotion(mtnmgr);
				ArrayList imagelist = motion.getImageList();
				if (motion.getType() == Motion.MOVE_MOTION)
				{
					canvas.setColor(Color.WHITE);
					for (int x = 120; x > 0; x--)
					{
						canvas.setColor(getBgColor(magic.magic));
						drawBattle();
						canvas.drawImage((Image) imagelist.get(0), x, 50, null);
			
						sleep(motion.getTimeSpace());
					}
				}
				else
				{
					canvas.setColor(Color.WHITE);
					for (int j = 0; j < imagelist.size(); j++)
					{
						drawBattle();
						canvas
								.drawImage((Image) imagelist.get(j), 10, 50,
										null);
					
						sleep(motion.getTimeSpace());
					}
				}
				MagicEffect(magic);
				drawBattle();
				if (checkWinner()) break;
				draw();
			}

		}
		for (int i = 0; i < delaymagic.size(); i++)
		{
			DelayMagic magic = (DelayMagic) delaymagic.get(i);
			if (magic.delay == 0)
			{
				delaymagic.remove(i);
				i--;
			}
		}
	}

	public void MagicEffect(DelayMagic magic)
	{
		Magic m = magic.magic;
		if (m.getType() == Magic.ATTACK_MAGIC)
		{
			if (magic.object == 1)
			{
				int hurt = 0;
				if (m.getAttackType() == Magic.NORMAL_ATTACK)
				{
					hurt = (hbstate.getStr() + m.getDmg())
							* (1 - ebstate.getDef() / 300);
					if (hurt < 0) hurt = 1;
				}
				else if (m.getAttackType() == Magic.MAGIC_ATTACK)
				{
					hurt = hbstate.getMstr() + hbstate.getMstr() 
							* (1 - ebstate.getMdef() / 300);
					if (hurt < 0) hurt = 1;
				}

				if (m.getProperty() == Magic.ALL_PROPERTY)
				{
					hurt = hurt + hurt / 2;
				}
				else if (m.getProperty() == Magic.FIRE_PROPERTY)
				{
					if (ebstate.getProperty() == Magic.ALL_PROPERTY)
					{
						hurt = hurt - hurt / 2;
					}
					else if (ebstate.getProperty() == Magic.ICE_PROPERTY)
					{
						hurt = hurt + hurt / 2;
					}
					else if (ebstate.getProperty() == Magic.WATER_PROPERTY)
					{
						hurt = hurt - hurt / 2;
					}
				}
				else if (m.getProperty() == Magic.WATER_PROPERTY)
				{
					if (ebstate.getProperty() == Magic.ALL_PROPERTY)
					{
						hurt = hurt - hurt / 2;
					}
					else if (ebstate.getProperty() == Magic.ICE_PROPERTY)
					{
						hurt = hurt - hurt / 2;
					}
					else if (ebstate.getProperty() == Magic.FIRE_PROPERTY)
					{
						hurt = hurt + hurt / 2;
					}
				}
				else if (m.getProperty() == Magic.ICE_PROPERTY)
				{
					if (ebstate.getProperty() == Magic.ALL_PROPERTY)
					{
						hurt = hurt - hurt / 2;
					}
					else if (ebstate.getProperty() == Magic.WATER_PROPERTY)
					{
						hurt = hurt + hurt / 2;
					}
					else if (ebstate.getProperty() == Magic.FIRE_PROPERTY)
					{
						hurt = hurt - hurt / 2;
					}
				}
				double hit = 100 * Math.random();
				if (hit < hbstate.getHs())
				{
					hurt = hurt * 2;
					msgmgr.showMessage(new Msg("你发出致命一击"));
				}
				hit = 100 * Math.random();
				if (hit > ebstate.getJouk())
				{
					ebstate.setHp(ebstate.getHp() - hurt);
					msgmgr.showMessage(new Msg("你击中敌人造成" + hurt + "点伤害!"));
				}
				else
				{
					msgmgr.showMessage(new Msg("你的攻击被敌人躲开了- -!"));
				}
			}
			else
			{
				int hurt = 0;
				if (m.getAttackType() == Magic.NORMAL_ATTACK)
				{
					hurt = (ebstate.getStr() + m.getDmg())
							* (1 - hbstate.getDef() / 300);
					if (hurt < 0) hurt = 1;
				}
				else if (m.getAttackType() == Magic.MAGIC_ATTACK)
				{
					hurt = ebstate.getMstr() * (1 - hbstate.getMdef() / 300);
					if (hurt < 0) hurt = 1;
				}
				if (m.getProperty() == Magic.ALL_PROPERTY)
				{
					hurt = hurt + hurt / 2;
				}
				else if (m.getProperty() == Magic.FIRE_PROPERTY)
				{
					if (hbstate.getProperty() == Magic.ALL_PROPERTY)
					{
						hurt = hurt - hurt / 2;
					}
					else if (hbstate.getProperty() == Magic.ICE_PROPERTY)
					{
						hurt = hurt + hurt / 2;
					}
					else if (hbstate.getProperty() == Magic.WATER_PROPERTY)
					{
						hurt = hurt - hurt / 2;
					}
				}
				else if (m.getProperty() == Magic.WATER_PROPERTY)
				{
					if (hbstate.getProperty() == Magic.ALL_PROPERTY)
					{
						hurt = hurt - hurt / 2;
					}
					else if (hbstate.getProperty() == Magic.ICE_PROPERTY)
					{
						hurt = hurt - hurt / 2;
					}
					else if (hbstate.getProperty() == Magic.FIRE_PROPERTY)
					{
						hurt = hurt + hurt / 2;
					}
				}
				else if (m.getProperty() == Magic.ICE_PROPERTY)
				{
					if (hbstate.getProperty() == Magic.ALL_PROPERTY)
					{
						hurt = hurt - hurt / 2;
					}
					else if (hbstate.getProperty() == Magic.WATER_PROPERTY)
					{
						hurt = hurt + hurt / 2;
					}
					else if (hbstate.getProperty() == Magic.FIRE_PROPERTY)
					{
						hurt = hurt - hurt / 2;
					}
				}

				double hit = 100 * Math.random();
				if (hit < ebstate.getHs())
				{
					hurt = hurt * 2;
					msgmgr.showMessage(new Msg("敌人发出致命一击!"));
				}
				hit = 100 * Math.random();
				if (hit > hbstate.getJouk())
				{
					msgmgr.showMessage(new Msg("敌人击中你造成" + hurt + "点伤害!"));
					hbstate.setHp(hbstate.getHp() - hurt);
				}
				else
				{
					msgmgr.showMessage(new Msg("你躲开了攻击!"));
				}
			}

		}
		else if (m.getType() == Magic.HEAL_MAGIC)
		{
			if (magic.object == 0)
			{
				hbstate.setHp(hbstate.getHp() + m.getHealHp());
			}
			else
			{
				ebstate.setHp(ebstate.getHp() + m.getHealHp());
			}
		}
		else if (m.getType() == Magic.BUFF_MAGIC)
		{
			if (magic.object == 0)
			{
				hbstate.setHpmx(hstate.getHpmx() + m.getBuffHp());
				hbstate.setMpmx(hstate.getMpmx() + m.getBuffMp());
				hbstate.setstr(hstate.getStr() + m.getBuffStr());
				hbstate.setDef(hstate.getDef() + m.getBuffDef());
				hbstate.setMstr(hstate.getMstr() + m.getBuffMstr());
				hbstate.setMdef(hstate.getMdef() + m.getBuffMdef());
				hbstate.setHs(hstate.getHs() + m.getBuffHs());
				hbstate.setHitp(hstate.getJouk() + m.getBuffJouk());
			}
			else if (magic.object == 1)
			{
				ebstate.setHpmx(estate.getHpmx() + m.getBuffHp());
				ebstate.setMpmx(estate.getMpmx() + m.getBuffMp());
				ebstate.setstr(estate.getStr() + m.getBuffStr());
				ebstate.setDef(estate.getDef() + m.getBuffDef());
				ebstate.setMstr(estate.getMstr() + m.getBuffMstr());
				ebstate.setMdef(estate.getMdef() + m.getBuffMdef());
				ebstate.setHs(estate.getHs() + m.getBuffHs());
				ebstate.setHitp(estate.getJouk() + m.getBuffJouk());
			}
		}
		else if (m.getType() == Magic.DEBUFF_MAGIC)
		{
			if (magic.object == 1)
			{
				ebstate.setHpmx(estate.getHpmx() - m.getDeBuffHp());
				ebstate.setMpmx(estate.getMpmx() - m.getDeBuffMp());
				ebstate.setstr(estate.getStr() - m.getDeBuffStr());
				ebstate.setDef(estate.getDef() - m.getDeBuffDef());
				ebstate.setMstr(estate.getMstr() - m.getDeBuffMstr());
				ebstate.setMdef(estate.getMdef() - m.getDeBuffMdef());
				ebstate.setHs(estate.getHs() - m.getDeBuffHs());
				ebstate.setHitp(estate.getJouk() - m.getDeBuffJouk());
			}
			else
			{
				hbstate.setHpmx(hstate.getHpmx() - m.getDeBuffHp());
				hbstate.setMpmx(hstate.getMpmx() - m.getDeBuffMp());
				hbstate.setstr(hstate.getStr() - m.getDeBuffStr());
				hbstate.setDef(hstate.getDef() - m.getDeBuffDef());
				hbstate.setMstr(hstate.getMstr() - m.getDeBuffMstr());
				hbstate.setMdef(hstate.getMdef() - m.getDeBuffMdef());
				hbstate.setHs(hstate.getHs() - m.getDeBuffHs());
				hbstate.setHitp(hstate.getJouk() - m.getDeBuffJouk());
			}
		}
	}

	/*
	 * 物品菜单
	 */
	public void drawItemMenu()
	{
		canvas.fillRect(0, 0, 260, 160);
		canvas.drawImage(itemmenuimage, 0, 0, null);
		canvas.setColor(Color.WHITE);
		// canvas.drawString("物品列表",100,20);
		canvas.setColor(Color.BLACK);
		ArrayList itemlist = hero.getItemNameList();
		ArrayList itemnumlist = hero.getItemNumList();

		int num = itemlist.size();

		pages = (hero.getItemNameList().size() - 1) / 10 + 1;
		if (page > pages) page--;
		int optionnum = 0;
		if (page == pages)
		{
			optionnum = hero.getItemNameList().size() % 10;
			if (optionnum == 0) optionnum = 10;
		}
		else optionnum = 10;
		if (select > optionnum)
		{
			select = optionnum;
		}

		int showindex = (page - 1) * 10;
		int shownum = 0;
		if (page == pages)
		{
			shownum = num % 10;
		}
		else
		{
			shownum = 10;
		}
		if (shownum == 0) shownum = 10;
		if (num == 0)
		{
			canvas.drawString("没有物品.", 100, 100);
			return;
		}
		else
		{
			for (int i = 0; i < shownum; i++)
			{
				canvas.drawString((String) itemlist.get(showindex + i), 65,
						54 + 17 * i);
				canvas.drawString(
						"x" + (String) itemnumlist.get(showindex + i), 180,
						54 + 17 * i);
				canvas.drawImage(itmmgr.getItem(
						(String) hero.getItemList().get(showindex + i))
						.getImage(), 39, 42 + 17 * i, null);
			}
			// canvas.drawImage(cursor,45,45+(select-1)*17,null);

			int scrollheight = 160 / pages;
			canvas.drawRect(210, 50, 8, 160);
			canvas.fillRect(212, 50 + (page - 1) * scrollheight, 5,
					scrollheight);

			canvas.setColor(Color.ORANGE);
			canvas.drawRect(62, 42 + (select - 1) * 17, 140, 14);

			// canvas.fillRect(30,230,198,25);
			int choose = select + (page - 1) * 10 - 1;
			canvas.setColor(Color.BLACK);
			canvas.drawString(itmmgr.getItem(
					(String) hero.getItemList().get(choose)).getInstruction(),
					40, 245);
		}
	}

	/*
	 * yes no 菜单 @param s 输入参数格式为"XX YY",XX为真,YY为假
	 */
	public boolean showYesNoMenu(GameCanvas g, String s)
	{
		sleep(200);
		gamecanvas = g;
		boolean b = true;
		char input = '#';
		Graphics canvas = gamecanvas.getGraphics();
		canvas.setColor(Color.WHITE);
		canvas.fillRect(92, 115, 65, 20);
		canvas.setColor(Color.YELLOW);
		canvas.drawRect(93, 116, 63, 18);
		do
		{
			input = gamecanvas.getInput(true);
			canvas.setColor(Color.BLACK);
			canvas.drawString(s, 96, 129);
			if (b)
			{
				canvas.setColor(Color.ORANGE);
				canvas.drawRect(94, 117, 25, 15);
				canvas.setColor(Color.WHITE);
				canvas.drawRect(129, 117, 25, 15);
			}
			else
			{
				canvas.setColor(Color.ORANGE);
				canvas.drawRect(129, 117, 25, 15);
				canvas.setColor(Color.WHITE);
				canvas.drawRect(94, 117, 25, 15);
			}

			if (input != ' ')
			{
				if (input == 'a')
				{
					b = true;
				}
				else if (input == 'd')
				{
					b = false;
				}
				else if (input == 'j')
				{
					break;
				}
				else if (input == 'k')
				{
					b = false;
					break;
				}
			}
		} while (true);
		return b;
	}

	public void setMsgMgr(MessageManager m)
	{
		msgmgr = m;
	}

	public void setGameCanvas(GameCanvas g)
	{
		gamecanvas = g;
		canvas = g.getGraphics();
	}

	public void setDisplayManager(DisplayManager d)
	{
		dplmgr = d;
	}

	public void setItemManager(ItemManager m)
	{
		itmmgr = m;
	}

	public void setMotionManager(MotionManager m)
	{
		mtnmgr = m;
	}

	public void setMagicManager(MagicManager m)
	{
		mgcmgr = m;
	}
	public Color getBgColor(Magic magic)
	{
		Color c;
		switch(magic.getProperty())
		{
		case Magic.NORMAL_PROPERTY:c = new Color(0,85,98);break;
		case Magic.FIRE_PROPERTY:c = new Color(255,0,0);break;
		case Magic.WATER_PROPERTY:c = new Color(0,85,98);break;
		case Magic.ICE_PROPERTY:c = new Color(73,196,247);break;
		case Magic.ALL_PROPERTY:c = new Color(120,54,210);break;
		default:c = new Color(255,255,255);break;
		}
		return c;
	}
	public void setBufferCanvas(JFrame frame)
	{
		doubleBuffer = frame.createImage(260,260);
	}
	public void sleep(int ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	private Graphics canvas;

	private MessageManager msgmgr;

	private GameCanvas gamecanvas;

	private DisplayManager dplmgr;

	private ItemManager itmmgr;

	private MotionManager mtnmgr;

	private MagicManager mgcmgr;

	private Image itemmenuimage;

	private Image battleground;

	private BattleState hstate;

	private BattleState estate;

	private BattleState hbstate;

	private BattleState ebstate;

	private Enemy enemy;

	private Hero hero;

	private int menu = 1;

	private int select = 1;

	private int pages = 1;

	private int page = 1;

	private char input;

	private int heroattack = 1;

	private ArrayList delaymagic;
	private Image doubleBuffer;
	private boolean finish = false;
	
	private AlphaComposite achalf = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f);
	private AlphaComposite acfull = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);

	private class DelayMagic
	{
		DelayMagic(Magic m, int heroattack)
		{
			magic = m;
			delay = magic.getDelay();
			object = heroattack;
			owner = heroattack;
			if (magic.getMagicType() == Magic.BUFF_MAGIC
					|| magic.getMagicType() == magic.HEAL_MAGIC) 
			{
				object = 1 - heroattack;
			}
		}
		
		public int owner = 0;
		
		public Magic magic = null;

		public int delay = 1;

		public int object = 1;
	}
}
