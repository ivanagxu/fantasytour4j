package com.ivan.game.motioncreater;

import javax.swing.*;

import com.ivan.game.unit.DatFilter;


import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MotionCreaterFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5299062657450918343L;
	public MotionCreaterFrame()
	{
		setTitle("动画预览制作器");
		setResizable(false);

		this.setBounds(312,184,400,400);
		motionshowpanel = new MotionPanel();
		controlpanel = new ControlPanel(motionshowpanel);
		Container content = getContentPane();
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
		//aboutitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));
		
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
								MotionCreaterFrame.this,
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
						chooser.setCurrentDirectory(new File(".//data//motion//"));
						chooser.setFileFilter(new DatFilter());
						int result = chooser.showOpenDialog(MotionCreaterFrame.this);
						if(result == 0)
						{
							controlpanel.readFile(chooser.getSelectedFile());
						}
					}
				});
		
		saveitem.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						controlpanel.save();
					}
				});
		
		newitem.addActionListener(new
				ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						controlpanel.newFile();
					}
				});
		
		JPanel paneltop = new JPanel();
		JPanel panelleft = new JPanel();
		JPanel panelright = new JPanel();
		paneltop.setBackground(Color.ORANGE);
		panelleft.setBackground(Color.ORANGE);
		panelright.setBackground(Color.ORANGE);
		content.add(paneltop,BorderLayout.NORTH);
		content.add(panelleft,BorderLayout.WEST);
		content.add(panelright,BorderLayout.EAST);
		
		content.add(motionshowpanel,BorderLayout.CENTER);
		content.add(controlpanel,BorderLayout.SOUTH);
		
		addWindowListener(new 
				WindowAdapter()
				{
					public void windowClosing(WindowEvent e)
					{
						dispose();
					}
				});
	}
	private MotionPanel motionshowpanel;
	private ControlPanel controlpanel;
}
