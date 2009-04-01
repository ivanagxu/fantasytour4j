package com.ivan.dbm;


public class DataSynchronization {

	public static void main(String[] args)
	{
		if (args.length == 0 || args[0].trim().length() == 0)
			throw new RuntimeException("args is need");

		DMProperties.getInstance().configure(args[0]);
		DMLogger.getLogger().info("start");
		DataReader readThread = new DataReader();
		Thread t = new Thread(readThread);
		t.start();
		long delayMillis = 5000;
		try {
			t.join(delayMillis);
			while (true) {
				if (t.isAlive()) {
					t.join(delayMillis);
				} else {
					break;
				}
			}
			
		}
		catch (InterruptedException e) {
			DMLogger.getLogger().error(e.getMessage());
			e.printStackTrace();
		}
	}
}
