package com.ivan.game.game;

import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URI;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.ivan.game.enemycreater.EnemyCreater;
import com.ivan.game.eventcreater.EventCreater;
import com.ivan.game.itemcreater.ItemCreater;
import com.ivan.game.magiccreater.MagicCreater;
import com.ivan.game.mapcreater.MapCreater;
import com.ivan.game.motioncreater.MotionCreater;
import com.ivan.game.npccreater.NpcCreater;




public class MainFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4955974398924842320L;
	public MainFrame()
	{
		setTitle("Game");
		setResizable(false);
		this.setBounds(312, 184, 266, 310);
		Container content = getContentPane();

		JMenuBar menuBar = new JMenuBar();

		setJMenuBar(menuBar);

		JMenu gamemenu = new JMenu("Start");
		JMenu toolmenu = new JMenu("Tools");
		JMenu aboutmenu = new JMenu("Help");

		menuBar.add(gamemenu);

		menuBar.add(toolmenu);

		menuBar.add(aboutmenu);

		JMenuItem newitem = new JMenuItem("New Game", 'N');
		JMenuItem loaditem = new JMenuItem("Load Game", 'L');
		JMenuItem saveitem = new JMenuItem("Save Game", 'S');
		JMenuItem exititem = new JMenuItem("Exit", 'E');

		JMenuItem eventitem = new JMenuItem("Event Creater");
		JMenuItem itemitem = new JMenuItem("Item Creater");
		JMenuItem motionitem = new JMenuItem("Motion Creater");
		JMenuItem magicitem = new JMenuItem("Magic Creater");
		JMenuItem npcitem = new JMenuItem("Npc Creater");
		JMenuItem enemyitem = new JMenuItem("Enemy Creater");
		JMenuItem mapitem = new JMenuItem("Map Creater");

		JMenuItem aboutitem = new JMenuItem("help");

		newitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_MASK));
		loaditem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				InputEvent.CTRL_MASK));
		saveitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
		exititem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				InputEvent.CTRL_MASK));
		//aboutitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));

		eventitem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new EventCreater();
			}
		});
		itemitem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new ItemCreater();
			}
		});
		motionitem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new MotionCreater();
			}
		});
		magicitem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new MagicCreater();
			}
		});
		npcitem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new NpcCreater();
			}
		});
		enemyitem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new EnemyCreater();
			}
		});
		mapitem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				new MapCreater();
			}
		});

		aboutitem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				/*
				 * JOptionPane.showMessageDialog(MainFrame.this, "love IvAn",
				 * "关于", JOptionPane.INFORMATION_MESSAGE);
				 * 
				 */
				try
				{
					//Desktop desktop = Desktop.getDesktop();
					//desktop.browse(new URI(System.getProperty("user.dir") + "/data/MyGameDoc/index.htm"));
					Runtime.getRuntime().exec("cmd.exe /c start data/MyGameDoc/index.htm");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

			}
		});

		exititem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				int NotExit = 0;
				NotExit = JOptionPane.showConfirmDialog(MainFrame.this,
						"是否退出?", "等待确认", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				if (NotExit == 0)
					System.exit(0);
			}
		});

		gamemenu.add(newitem);
		gamemenu.addSeparator();
		gamemenu.add(loaditem);
		gamemenu.add(saveitem);
		gamemenu.addSeparator();
		gamemenu.add(exititem);

		toolmenu.add(eventitem);
		toolmenu.add(itemitem);
		toolmenu.add(motionitem);
		toolmenu.add(magicitem);
		toolmenu.add(npcitem);
		toolmenu.add(enemyitem);
		toolmenu.add(mapitem);

		aboutmenu.add(aboutitem);

		canvas = new GameCanvas();
		canvas.setBackground(Color.BLACK);
		content.add(canvas);
	}
	public Graphics GetCanvasGraphics()
	{
		return canvas.getGraphics();
	}
	public GameCanvas getCanvas()
	{
		return canvas;
	}
	private GameCanvas canvas;
}
