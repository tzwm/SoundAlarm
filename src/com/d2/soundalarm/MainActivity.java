package com.d2.soundalarm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import static java.util.Calendar.DATE;

/**
 * Created by tzwm on 5/25/13.
 */

public class MainActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topText = (TextView)findViewById(R.id.topView);
        topText.setText("����ŭ���ֻ�");

        btnTime = (Button)findViewById(R.id.btnTime);
        btnRepeat = (Button)findViewById(R.id.btnRepeat);
        btnDel = (Button)findViewById(R.id.btnDel);
        btnMem = (Button)findViewById(R.id.btnMem);
        onoff =  (Button)findViewById(R.id.onoff);
        img = (ImageView)findViewById(R.id.turntable);
        bitImg = ((BitmapDrawable)(img.getDrawable())).getBitmap();
        img.setOnTouchListener(new Button.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
//                topText.setText(String.valueOf(String.format("%x", bitImg.getPixel((int) (motionEvent.getX()), (int) (motionEvent.getY())))));
                if(( bitImg.getPixel((int)(motionEvent.getX()), (int)(motionEvent.getY())) ) == 0){
                    return false;
                }
                return true;
            }
        });

        btn = new Vector<Button>();
        btn.add((Button)findViewById(R.id.btn0));
        btn.add((Button)findViewById(R.id.btn1));
        btn.add((Button)findViewById(R.id.btn2));
        btn.add((Button)findViewById(R.id.btn3));
        btn.add((Button)findViewById(R.id.btn4));
        btn.add((Button)findViewById(R.id.btn5));
        btn.add((Button)findViewById(R.id.btn6));
        btn.add((Button)findViewById(R.id.btn7));
        
        for(int i = 0;i<8;i++){
            btn.get(i).setTag("new");
        }

        AlarmHelper alarmHelper = new AlarmHelper(this);
        AlarmHelper.Alarm alarm = new AlarmHelper.Alarm(alarmHelper.generateId(), "test", "test context", Calendar.MINUTE);
        alarmHelper.openAlarm(alarm);

        alarms = alarmHelper.getAlarms();
        for(int i=0;i<8;i++){
            if(i>=alarms.size())
                break;
            if(alarms.get(i).opened){
                btn.get(i).setBackgroundResource(R.drawable.on);
                btn.get(i).setTag("on");
            }else{
                btn.get(i).setBackgroundResource(R.drawable.off);
                btn.get(i).setTag("off");
            }
        }
        
        onoff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//alarm.opened=!alarm.opened;
			}
		});
        
        background = (LinearLayout)findViewById(R.id.back);
        OnClickListener list4Button = new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.btnTime){
                    background.setBackgroundResource(R.drawable.bb1);
                    topText.setText("9:00");
                }
                if(view.getId() == R.id.btnRepeat){
                    background.setBackgroundResource(R.drawable.bb2);
                    topText.setText("No repeat");
                }
                if(view.getId() == R.id.btnDel){
                    background.setBackgroundResource(R.drawable.bb3);
                    topText.setText("Deleted");
                }
                if(view.getId() == R.id.btnMem){
                    background.setBackgroundResource(R.drawable.bb4);
                    topText.setText("Shout");
                }
            }
        };
        btnTime.setOnClickListener(list4Button);
        btnRepeat.setOnClickListener(list4Button);
        btnDel.setOnClickListener(list4Button);
        btnMem.setOnClickListener(list4Button);

        OnClickListener listBtn = new OnClickListener() {
            @Override
            public void onClick(View view) {
            	switch(view.getId()){
            	case R.id.btn5:startActivity(new Intent(MainActivity.this,BeeActivity.class));MainActivity.this.finish();return;
            	case R.id.btn6:startActivity(new Intent(MainActivity.this,MorningShoutActivity.class));MainActivity.this.finish();return;
            	case R.id.btn7:startActivity(new Intent(MainActivity.this,WordActivity.class));MainActivity.this.finish();return;
            	default: callBtn((Button)findViewById(view.getId()), 0);
            	}
            	
            }
        };
        for(int i=0;i<8;i++){
            btn.get(i).setOnClickListener(listBtn);
        }

        topText.setMovementMethod(LinkMovementMethod.getInstance());
    }
    
    int getId(int vid){
    	switch(vid){
    	case R.id.btn0:return 0;
    	case R.id.btn1:return 1;
    	case R.id.btn2:return 2;
    	case R.id.btn3:return 3;
    	case R.id.btn4:return 4;
    	case R.id.btn5:return 5;
    	case R.id.btn6:return 6;
    	case R.id.btn7:return 7;
    	}
    	return -1;
    }

    private void callBtn(Button btn, int x){
        if(btn.getTag().equals("new")){
            Intent itent = new Intent();
            itent.setClass(MainActivity.this, SettingActivity.class);
            startActivity(itent);
            btnid=btn.getId();
            itent.putExtra("id", getId(btn.getId()));
            //MainActivity.this.finish();
        }else{
            topText.setText(String.valueOf(alarms.get(x).time));
            Button btnTmp = (Button)findViewById(R.id.onoff);
            btnTmp.setAlpha((float)100);
            if(btn.getTag().equals("on"))
            	btnTmp.setBackgroundResource(R.drawable.btnon);
            else
            	btnTmp.setBackgroundResource(R.drawable.btnoff);
        }

    }
    
    private int btnid;
    private LinearLayout background;
    private List<AlarmHelper.Alarm> alarms;
    private Vector<Button> btn;
    private Button btnTime, btnRepeat, btnDel, btnMem, onoff;
    private TextView topText;
    private ImageView img;
    private Bitmap bitImg;
}
