package com.ivan.game.managers;

import java.awt.Graphics;
import java.io.File;

import com.ivan.game.game.GameCanvas;
import com.ivan.game.game.MainFrame;
import com.ivan.game.unit.Hero;
import com.ivan.game.unit.Msg;
import com.ivan.game.unit.SimFonts;


public class GameManager {
	public GameManager(MainFrame f)
	{
		gamestate = GAME_INITING;
		
		frame = f;
		gamecanvas = frame.getCanvas();
		gamecanvas.equipListener(true);
		setGameCanvas(gamecanvas.getGraphics());
		
		initManagers();
		
		gamestate = GAME_READY;
	}
	public void setGameCanvas(Graphics g)
	{
		canvas = g;
	}
	public void initManagers()
	{
		msgmgr = new MessageManager(frame);
		
		emymgr = new EnemyManager();
		evtmgr = new EventManager();
		itmmgr = new ItemManager();
		mgcmgr = new MagicManager();
		mtnmgr = new MotionManager();
		npcmgr = new NpcManager(itmmgr);
		dlpmgr = new DisplayManager();
		mapmgr = new MapManager();
		batmgr = new BattleManager();
		sndmgr = new SoundManager();
		
		
		dlpmgr.setCanvas(canvas);
		dlpmgr.setSoundManager(sndmgr);
		
		mapmgr.setDisplayMgr(dlpmgr);
		mapmgr.setMsgMgr(msgmgr);
		mapmgr.setBufferCanvas(frame);
		mapmgr.setEventMgr(evtmgr);
		mapmgr.setNpcMgr(npcmgr);
		mapmgr.setEnemyMgr(emymgr);
		
		batmgr.setDisplayManager(dlpmgr);
		batmgr.setMsgMgr(msgmgr);
		batmgr.setGameCanvas(gamecanvas);
		batmgr.setItemManager(itmmgr);
		batmgr.setMotionManager(mtnmgr);
		batmgr.setMagicManager(mgcmgr);
		batmgr.setBufferCanvas(frame);
		
		menu = new MenuManager();
		menu.setDisplayMgr(dlpmgr);
		menu.setMsgMgr(msgmgr);
		menu.setItemManager(itmmgr);
		menu.setGameCanvas(gamecanvas);
		menu.setMagicMagr(mgcmgr);
		
		mapmgr.setMenuMgr(menu);
	}
	public void startGame()
	{
		while(true)
		{
			if(gamestate == GAME_READY)
			{
				System.out.println("游戏启动.\n");
				dlpmgr.playStartMovie();

				gamestate = GAME_RUNNING;
				display = GameManager.SHOW_MENU;
				startmenu();
				mainloop();
			}
		}
	}
	public void startmenu()
	{
		input = '#';
		File f = new File("data/save/IvAn.sav");
		if (f.exists()) 
		{
			menu.setMenu(1,2);
		}
		
		do
		{
			if(input != ' ')
			{
				if(display == GameManager.SHOW_MENU)
				{
					menu.action(input,display);
					menu.draw(canvas,display);
				}
			}
			input = gamecanvas.getInput(true);
			display = menu.getDisplay(display);
			if(display != GameManager.SHOW_MENU)
				break;
			sleep(1);
		}while(true);
		
		if(menu.getSelect() == 1)
		{
			hero = new Hero("IvAn");
			hero.initEventName(evtmgr);
			hero.initItemName(itmmgr);
			hero.initMagicName(mgcmgr);
		}
		else if(menu.getSelect() == 2)
		{
			hero = new Hero(new File("data/save/IvAn.sav"));
			hero.initEventName(evtmgr);
			hero.initItemName(itmmgr);
			hero.initMagicName(mgcmgr);
		}
		else if(menu.getSelect() == 3)
		{
			close();
		}
		menu.setMenu(0,0);
		menu.setHero(hero);
		
		dlpmgr.setDisplay(GameManager.SHOW_MAP);
	}
	public void mainloop()
	{
		/*
		 * 进入游戏主循环
		 */
		input = '#';
		do
		{
			if(input != ' ')
			{
				/*
				 * do action
				 */
				if(display == GameManager.SHOW_MAP)
				{
					if(input == 'w')
					{
						hero.moveUp(mapmgr.getMap());
					}
					else if(input == 's')
					{
						hero.moveDown(mapmgr.getMap());
					}
					else if(input == 'a')
					{
						hero.moveLeft(mapmgr.getMap());
					}
					else if(input == 'd')
					{
						hero.moveRight(mapmgr.getMap());
					}
					mapmgr.action(input,display,hero);
				}
				else if(display == GameManager.SHOW_BATTLE)
				{
					batmgr.start(hero);
					dlpmgr.play(mapmgr.getMap().getMusic());
				}
				else if(display == GameManager.SHOW_MENU || 
						display == GameManager.SHOW_MAP_MENU ||
						display == GameManager.SHOW_BATTLE_MENU||
						display == GameManager.SHOW_BUYMENU)
				{
					menu.action(input,display);
					gamecanvas.getInput(true);
				}
				else if(display == GameManager.EXIT_GAME)
				{
					gamestate = GAME_FINISH;
					break;
				}
				
				if(hero.inBattle())
				{
					dlpmgr.setDisplay(GameManager.SHOW_BATTLE);
				}
				display = dlpmgr.getDisplay();
				
				/*
				 * 独立
				 */
				if(display == GameManager.SAVE_GAME)
				{
					hero.save();
					msgmgr.showMessage(new Msg("记录已保存."));
					dlpmgr.setDisplay(GameManager.SHOW_MAP);
				}
				/*
				 * draw
				 */
				if(display == GameManager.SHOW_MAP || display == GameManager.SHOW_MAP_MENU)
				{
					mapmgr.draw(display,hero,canvas);
				}
				if(display == GameManager.SHOW_BATTLE || display == GameManager.SHOW_BATTLE_MENU)
				{
				}
				if(display == GameManager.SHOW_MENU || 
						display == GameManager.SHOW_MAP_MENU||
						display == GameManager.SHOW_BATTLE_MENU||
						display == GameManager.SHOW_BUYMENU)
				{
					menu.draw(canvas,display);
				}
			}
			input = gamecanvas.getInput(false);
			sleep(1);
		}while(true);
		
		/*
		 * 到这里,主循环已退出,初始化并回到游戏主开始菜单
		 */
		if(gamestate == GAME_FINISH)
		{
			System.exit(0);
		}
	}
	public void close()
	{
		System.exit(0);
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
	/*
	 * managers
	 */
	private MessageManager msgmgr;
	private EnemyManager emymgr;
	private EventManager evtmgr;
	private ItemManager itmmgr;
	private MagicManager mgcmgr;
	private MapManager mapmgr;
	private MotionManager mtnmgr;
	private NpcManager npcmgr;
	private DisplayManager dlpmgr;
	private MenuManager menu;
	private BattleManager batmgr;
	private SoundManager sndmgr;
	
	private Hero hero;
	
	private int display = 0;
	public static final int SHOW_MAP = 1;
	public static final int SHOW_BATTLE = 2;
	public static final int INIT_MAP = 3;
	public static final int SHOW_MENU = 4;
	public static final int SHOW_BATTLE_MENU = 5;
	public static final int SHOW_MAP_MENU = 6;
	public static final int SAVE_GAME = 7;
	public static final int EXIT_GAME = 8;
	public static final int SHOW_BUYMENU = 9;
	
	private int gamestate;
	public static final int GAME_INITING = 0;
	public static final int GAME_SAVING = 1;
	public static final int GAME_LOADING = 2;
	public static final int GAME_READY = 3;
	public static final int GAME_RUNNING = 4;
	public static final int GAME_PAUSE = 5;
	public static final int GAME_FINISH = 6;
	public static final int GAME_ERROR = 7;
	
	
	private MainFrame frame;
	private GameCanvas gamecanvas;
	private Graphics canvas;
	private char input = ' ';
}


