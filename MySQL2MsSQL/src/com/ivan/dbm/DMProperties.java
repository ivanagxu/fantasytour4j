package com.ivan.dbm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DMProperties {
	private Properties props = new Properties();
	private static DMProperties instance = null;
	private DMProperties()
	{
		
	}
	public void configure(String configFile)
	{
		try{
			InputStream inStream = new FileInputStream(new File(configFile));   
			props.load(inStream);
			inStream.close();
		}
		catch(IOException e)
		{
			DMLogger.getLogger().error(e.getMessage());
			e.printStackTrace();
		}
	}
	public static DMProperties getInstance()
	{
		synchronized(DMProperties.class)
		{
			if(instance == null)
			{
				instance = new DMProperties();
			}
			return instance;
		}
	}
	public String getProps(String key)
	{
		return props.getProperty(key);
	}
}
