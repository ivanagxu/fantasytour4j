package com.ivan.game.game;

import javax.swing.JFrame;

import com.ivan.game.managers.GameManager;


public class Game
{
	public Game()
	{
		gameframe = new MainFrame();
		gameframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameframe.setVisible(true);
	}
	public void start()
	{
		gmemgr = new GameManager(gameframe);
		gmemgr.startGame();
	}
	/*
	 * display
	 */
	private MainFrame gameframe;
	private GameManager gmemgr;
}
