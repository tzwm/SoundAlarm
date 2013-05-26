package com.d2.soundalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {

		 if ("android.alarm.demo.action".equals(intent.getAction())) {
			 Intent in=new Intent(context, WordActivity.class);
			 context.startActivity(in);	 
			 return;
		 }
	}
}