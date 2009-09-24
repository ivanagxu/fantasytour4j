package com.ivan.game.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public final class GameLogger {
	public static Logger logger = Logger.getLogger("Game");
	static{
		//BasicConfigurator.configure();
		PropertyConfigurator.configure ("data/log/log4j.properties");
	}
}
