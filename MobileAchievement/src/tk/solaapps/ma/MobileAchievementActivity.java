package tk.solaapps.ma;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MobileAchievementActivity extends Activity {
	private MAService maService;
	private ArrayList<String> values;

	
/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		doBindService();
		
		
		values = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
		ListView list = (ListView) findViewById(R.id.list);
		list.setAdapter(adapter);

		// List<String> wordList = s.getWordList();
		// Toast.makeText(this, wordList.get(0), Toast.LENGTH_LONG).show();
	}

	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder binder) {
			maService = ((MAService.MyBinder) binder).getService();
			Toast.makeText(MobileAchievementActivity.this, "Connected",
					Toast.LENGTH_SHORT).show();
		}

		public void onServiceDisconnected(ComponentName className) {
			maService = null;
		}
	};
	
	private ArrayAdapter<String> adapter;

	void doBindService() {
		bindService(new Intent(this, MAService.class), mConnection,
				Context.BIND_AUTO_CREATE);
	}

	public void showServiceData(View view) {
		if (maService != null) {
			List<Integer> wordIdList = maService.getWordList();
			values.clear();
			for(int i = 0; i < wordIdList.size(); i++)
			{
				values.add(STR(wordIdList.get(i)));
			}
			adapter.notifyDataSetChanged();
		}
	}
	
	public String STR(int id)
	{
		return getResources().getString(id);
	}
}