package com.d2.soundalarm;

import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.widget.Button;
import android.widget.TextView;

public class MorningShoutActivity extends Activity {
	static TextView text;
	static TextView text1;
	Button btn;
	Button btn1;
	static int i=0;
	RecordThread tt=null;
	private MediaPlayer mMediaPlayer=null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morningshout);
        PlayAlarmRing();
        
        tt=new RecordThread(r);           //点击按钮，启动线程
		tt.start();
		
		text1=(TextView)findViewById(R.id.text1);
        text=(TextView)findViewById(R.id.text);
        

        Time t=new Time(); 

        t.setToNow(); // 取得系统时间。
        int year = t.year;
        int month = t.month;
        int date = t.monthDay;
        int hour = t.hour;    // 0-23
        int minute = t.minute;
        int second = t.second;
        if(hour<10){
        	if(minute<10){
        		 text.setText("0"+hour+":0"+minute);
        		 }else{
                    text.setText("0"+hour+":"+minute);
        		 }
        }else{
        	 text.setText(hour+":"+minute);
        }
        
        
    }
    
    class MyHandler extends Handler{
    	
    }
    
    private void PlayAlarmRing() {
		Uri alert = RingtoneManager
		.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		try {
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setDataSource(this, alert);
		final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
			mMediaPlayer.setLooping(true);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		}
		} catch (IllegalStateException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
    
    private void StopPlayAlarmRing() {
    	mMediaPlayer.stop();
    }
    
    /*OnClickListener listener=new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
				tt=new RecordThread(r);           //点击按钮，启动线程
				tt.start();
		}
    	
    };*/
    
    MyHandler r=new MyHandler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);  //接收到message后更新UI，并通过isblow停止线程
			if(i>0){
				StopPlayAlarmRing();			
			}
			//text.setText("你吹了一下屏幕"+i);
			Parameter.isblow=false;
	
		}
    };
}
