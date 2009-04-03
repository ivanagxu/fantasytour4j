package com.ivan.test.menu;

import java.awt.Container;

import javax.swing.JFrame;

public class MenuTestFrame extends JFrame{
	public MenuTestFrame()
	{
		this.setSize(400,400);
		this.setTitle("Menu Test");
		Container content = this.getContentPane();
		panel = new MenuTestPanel();
		panel.setFocusable(true);
		content.add(panel);
	}
	public char getCh()
	{
		return panel.getPressedKey();
	}
	private MenuTestPanel panel;
}
