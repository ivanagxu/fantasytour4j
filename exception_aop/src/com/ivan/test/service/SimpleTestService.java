package com.ivan.test.service;

import com.ivan.test.interfaces.SimpleService;
import com.ivan.test.logger.Logger;

public class SimpleTestService implements SimpleService{
	private Logger logger = new Logger();   
	public void start() {
		logger.entry_info("ServiceStart Start.");
		for(int i = 0; i < 100; i++)
		{
			try {
				Thread.sleep(100l);
			}
			catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}

	public void stop() {
		logger.entry_info("ServiceStop Start.");
		int i = 1 / 0;
	}
	
}
