package com.ivan.game.itemcreater;
import javax.swing.*;

import com.ivan.game.unit.DatFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class ItemCreaterFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6587928654892814159L;
	public ItemCreaterFrame()
	{
		setTitle("物品制作器");
		setResizable(false);

		this.setBounds(312,184,DEFAULT_WIDTH,DEFAULT_HEIGHT);
		Container content = getContentPane();
		panel = new ItemCreaterPanel();
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu editmenu = new JMenu("Edit");
		JMenu helpmenu = new JMenu("Help");
		
		menuBar.add(editmenu);
		menuBar.add(helpmenu);

		JMenuItem newitem = new JMenuItem("New",'N');
		JMenuItem openitem = new JMenuItem("Open",'O');
		JMenuItem saveitem = new JMenuItem("Save",'S');
		JMenuItem exititem = new JMenuItem("Exit",'E');
		JMenuItem aboutitem = new JMenuItem("About");
		
		newitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
		openitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
		saveitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
		exititem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
		
		editmenu.add(newitem);
		editmenu.addSeparator();
		editmenu.add(openitem);
		editmenu.add(saveitem);
		editmenu.addSeparator();
		editmenu.add(exititem);
		helpmenu.add(aboutitem);
		
		exititem.addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						int NotExit = 0;
						NotExit = JOptionPane.showConfirmDialog(
								ItemCreaterFrame.this,
								"是否退出?",
								"等待确认",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.INFORMATION_MESSAGE);
						if(NotExit == 0)
							dispose();
					}
				});
		
		aboutitem.addActionListener(new
				ActionListener()
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
							Runtime.getRuntime().exec(
									"cmd.exe /c start data/MyGameDoc/index.htm");
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				});
		
		openitem.addActionListener(new 
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						JFileChooser chooser = new JFileChooser();
						chooser.setCurrentDirectory(new File(".//data//item//"));
						chooser.setFileFilter(new DatFilter());
						int result = chooser.showOpenDialog(ItemCreaterFrame.this);
						if(result == 0)
						{
							panel.readFile(chooser.getSelectedFile());
						}
					}
				});
		
		saveitem.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						panel.save();
					}
				});
		
		newitem.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						panel.newFile();
					}
				});
		
		content.add(panel);
		
		addWindowListener(new 
				WindowAdapter()
				{
					public void windowClosing(WindowEvent e)
					{
						dispose();
					}
				});
	}
	private ItemCreaterPanel panel;
	private final int DEFAULT_WIDTH = 400;
	private final int DEFAULT_HEIGHT = 400;
}
