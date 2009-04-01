package com.ivan.test.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Logger {
	private static Log log = LogFactory.getLog(Logger.class);

	public void entry_info(String message) {
		//log.info(message);
		System.out.println(message);
	}

	public void entry_error(String message) {
		//log.error(message);
		System.out.println(message);
	}
}
