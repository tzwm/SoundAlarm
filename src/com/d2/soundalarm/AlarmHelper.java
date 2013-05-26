package com.d2.soundalarm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmHelper {
	
	private Context c;
	private AlarmManager mAlarmManager;
	public static AlarmHelper instance;
    public static AlarmHelper getInstance(Context c){
        if (instance==null) instance=new AlarmHelper(c);
        return instance;
    }

	public AlarmHelper(Context c) {
		this.c = c;
		mAlarmManager = (AlarmManager) c
				.getSystemService(Context.ALARM_SERVICE);
	}	
	
	int baseid=32;
	
	public int generateId(){
		int id=baseid;
		while (list.get(id)!=null) ++id;
		return id;
	}
	
	static public class Alarm{
		int id;
		String title;
		String content;
		long time;
		boolean opened=true;
        public Alarm() {

        }
		public Alarm(int id,String title,String content,long time) {
			this.id=id;
			this.time=time;
			this.title=title;
			this.content=content;
		}
	}
	
	Map<Integer,Alarm> list=new HashMap<Integer, AlarmHelper.Alarm>();
	
	public void openAlarm(Alarm alarm){
		openAlarm(alarm.id,alarm.title,alarm.content,alarm.time);
	}
	    
	public void openAlarm(int id, String title, String content, Calendar calendar){
		openAlarm(id,title,content,calendar.getTimeInMillis());
	}
	    
	public void openAlarm(int id, String title, String content, long time) {
		Alarm alarm=list.get(id);
		if (alarm!=null) {
			alarm.opened=true;
		}else{
			alarm=new Alarm(id, title, content, time);
			list.put(id, alarm);
		}
		Intent intent = new Intent();
		intent.putExtra("_id", id);
		intent.putExtra("title", title);
		intent.putExtra("content", content);
		intent.setClass(c, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(c, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mAlarmManager.set(AlarmManager.RTC_WAKEUP, time, pi);
		
	}
	 
	public boolean closeAlarm(int id) {
		Alarm alarm=list.get(id);
		if (alarm==null) return false;
		if (alarm.opened==false) return true;
		alarm.opened=false;
		Intent intent = new Intent();
		intent.putExtra("_id", alarm.id);
		intent.putExtra("title", alarm.title);
		intent.putExtra("content", alarm.content);
		intent.setClass(c, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(c, id, intent, 0);
		mAlarmManager.cancel(pi);
		return true;
	}
	
	public boolean deleteAlarm(int id){
		if (closeAlarm(id)){
			list.remove(id);
			return true;
		}
		return false;
	}
	
	public Alarm getAlarm(int id){
		return list.get(id);
	}
	
	public List<Alarm> getAlarms(){
		List<Alarm> ret=new ArrayList<AlarmHelper.Alarm>();
		Set<Integer> keys=list.keySet();
		for (Integer key:keys){
			ret.add(list.get(key));
		}
		return ret;
	}
}