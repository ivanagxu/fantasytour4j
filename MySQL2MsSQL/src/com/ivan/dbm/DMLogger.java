package com.ivan.dbm;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DMLogger { 
	private static Logger logger = Logger.getLogger("DM");
	private static DMLogger instance = null;
	private DMLogger()
	{
		
		PropertyConfigurator.configure (DMProperties.getInstance().getProps("log4j.config"));
	}
	public static Logger getLogger()
	{
		if(instance == null)
		synchronized(DMLogger.class)
		{
			instance = new DMLogger();
		}
		return logger;
	}
}
