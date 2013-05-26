package com.d2.soundalarm;

import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard.Key;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.AlarmClock;
import android.provider.MediaStore.Audio;
import android.view.KeyCharacterMap.KeyData;
import android.view.KeyEvent;
import android.view.SurfaceView;

public class BeeActivity extends Activity {
	
	SurfaceView beeSurface;
	MediaPlayer mMediaPlayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		beeSurface=new BeeSurface(this,handler);
		setContentView(beeSurface);
		PlayAlarmRing();
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
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			if (mMediaPlayer!=null) mMediaPlayer.stop();
			
			finish();
		};		
	};
	
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_BACK||
			keyCode==KeyEvent.KEYCODE_HOME){
			return true;
		}
		return false;
	};
}
