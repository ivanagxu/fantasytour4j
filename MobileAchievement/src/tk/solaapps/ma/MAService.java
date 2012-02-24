package tk.solaapps.ma;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MAService extends Service {
	private Timer timer = new Timer();
	private static final long UPDATE_INTERVAL = 5000;
	private final IBinder mBinder = new MyBinder();
	private ArrayList<Integer> list = new ArrayList<Integer>();
	private int[] fixedList = 
		{ 
			R.string.ach_battery_full, 
			R.string.ach_contact_10, 
			R.string.ach_contact_50, 
			R.string.ach_contact_100
		};
	
	private int index = 0;

	public void onCreate() {
		super.onCreate();
		pollForUpdates();
	}

	private void pollForUpdates() {
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				// Imagine here a freaking cool web access ;-)
				if (list.size() >= 4) {
					list.remove(0);
				}
				list.add(fixedList[index++]);
				if (index >= fixedList.length) {
					index = 0;
				}
			}
		}, 0, UPDATE_INTERVAL);
		Log.i(getClass().getSimpleName(), "Timer started.");

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (timer != null) {
			timer.cancel();
		}
		Log.i(getClass().getSimpleName(), "Timer stopped.");

	}

	// We return the binder class upon a call of bindService
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}

	public class MyBinder extends Binder {
		MAService getService() {
			return MAService.this;
		}
	}

	public List<Integer> getWordList() {
		return list;
	}

}