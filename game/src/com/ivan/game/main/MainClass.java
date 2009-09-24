package com.ivan.game.main;


import com.ivan.game.game.Game;
import com.ivan.game.logger.GameLogger;

public class MainClass {
	public static void main(String[] args)
	{
		GameLogger.logger.info("Game Init");
		Game game = new Game();
		game.start();
		
	}
}
