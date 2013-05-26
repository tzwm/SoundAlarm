package com.d2.soundalarm;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.d2.soundalarm.R;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class WordActivity extends Activity implements OnClickListener{
	
	HashMap<String,String> map=new HashMap<String, String>();
	EditText et;
	TextView tv;
	Button bn;
	
	MediaPlayer mMediaPlayer;
	private String key;

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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word);
		map.put("学校","school" );
		map.put("蜜蜂", "bee");
		map.put("单词","word");
		Set<String> keys=map.keySet();
		Iterator<String> iter=keys.iterator();
		int n=(int)(Math.random()*map.size());
		while(n>=0) {key=iter.next();--n;};
		tv=(TextView)findViewById(R.id.textView1);
		et=(EditText)findViewById(R.id.editText1);
		bn=(Button)findViewById(R.id.button1);
		tv.setText(key);		
		bn.setOnClickListener(this);
		PlayAlarmRing();
	}
	@Override
	public void onClick(View v) {
		if (et.getText().toString().equalsIgnoreCase(map.get(key))){
			mMediaPlayer.stop();
			finish();
		}		
	}
}
