package com.d2.soundalarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.d2.soundalarm.setting.MyThread;

import java.util.Date;

public class SettingActivity extends Activity implements View.OnClickListener {

    private ImageButton cancelButton;
    private ImageButton saveButton;
    private ImageButton ib1,ib2,ib3,ib4,ib5;
    View clickedview;
    ContextMenu menu;
    AlarmHelper ah;
    AlarmHelper.Alarm alarm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        ah = AlarmHelper.getInstance(this);
        alarm = new AlarmHelper.Alarm();
        alarm.id = getIntent().getIntExtra("id",-1);

        cancelButton =(ImageButton) findViewById(R.id.cancel);
        cancelButton.setImageResource(R.drawable.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        saveButton =(ImageButton) findViewById(R.id.save);
        saveButton.setImageResource(R.drawable.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                date.setHours(MyThread.hour);
                date.setMinutes(MyThread.minute);
                alarm.time = date.getTime();
                ah.openAlarm(alarm);
                Intent intent=new Intent(SettingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        ib1 = (ImageButton) findViewById(R.id.iButton1);
        ib2 = (ImageButton) findViewById(R.id.iButton2);
        ib3 = (ImageButton) findViewById(R.id.iButton3);
        ib4 = (ImageButton) findViewById(R.id.iButton4);
        ib5 = (ImageButton) findViewById(R.id.iButton5);

        ib1.setOnClickListener(this);
        ib2.setOnClickListener(this);
        ib3.setOnClickListener(this);
        ib4.setOnClickListener(this);
        ib5.setOnClickListener(this);

        ib1.setOnTouchListener(new myTouchListener());
        ib2.setOnTouchListener(new myTouchListener());
        ib3.setOnTouchListener(new myTouchListener());
        ib4.setOnTouchListener(new myTouchListener());
        ib5.setOnTouchListener(new myTouchListener());

        this.registerForContextMenu(ib1);
        this.registerForContextMenu(ib2);
        this.registerForContextMenu(ib3);
        this.registerForContextMenu(ib4);
        this.registerForContextMenu(ib5);

    }

    private class myTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent e) {
            if(e.getAction() == MotionEvent.ACTION_DOWN){
                switch(v.getId()) {
                    case R.id.iButton1:
                        ((ImageButton)v).setImageResource(R.drawable.b11);
                        break;
                    case R.id.iButton2:
                        ((ImageButton)v).setImageResource(R.drawable.b22);
                        break;
                    case R.id.iButton3:
                        ((ImageButton)v).setImageResource(R.drawable.b33);
                        break;
                    case R.id.iButton4:
                        ((ImageButton)v).setImageResource(R.drawable.b44);
                        break;
                    case R.id.iButton5:
                        ((ImageButton)v).setImageResource(R.drawable.b55);
                        break;
                }
            }else if(e.getAction() == MotionEvent.ACTION_UP){
                switch(v.getId()) {
                    case R.id.iButton1:
                        ((ImageButton)v).setImageResource(R.drawable.button1);
                        break;
                    case R.id.iButton2:
                        ((ImageButton)v).setImageResource(R.drawable.button2);
                        break;
                    case R.id.iButton3:
                        ((ImageButton)v).setImageResource(R.drawable.button3);
                        break;
                    case R.id.iButton4:
                        ((ImageButton)v).setImageResource(R.drawable.button4);
                        break;
                    case R.id.iButton5:
                        ((ImageButton)v).setImageResource(R.drawable.button5);
                        break;
                }
            }
            return false;
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        this.menu=menu;
        switch (v.getId()){
            case R.id.iButton1:
//                menu.add(1,1,1,"鏄熸湡涓�).setChecked((day_selected&(1<<1))!=0);
//                menu.add(1,2,2,"鏄熸湡浜�).setChecked((day_selected&(1<<2))!=0);
//                menu.add(1,3,3,"鏄熸湡涓�).setChecked((day_selected&(1<<3))!=0);
//                menu.add(1,4,4,"鏄熸湡鍥�).setChecked((day_selected&(1<<4))!=0);
//                menu.add(1,5,5,"鏄熸湡浜�).setChecked((day_selected&(1<<5))!=0);
//                menu.add(1,6,6,"鏄熸湡鍏�).setChecked((day_selected&(1<<6))!=0);
//                menu.add(1,7,7,"鏄熸湡鏃�).setChecked((day_selected&(1<<7))!=0);
//                menu.add(0,8,8,"纭畾");
//                menu.setGroupCheckable(1, true, false);
                break;
            case R.id.iButton2:;
//                menu.add(2,1,1,"item1");
//                menu.add(2,2,2,"item2");
                break;
            case R.id.iButton3:break;
            case R.id.iButton4:break;
            case R.id.iButton5:
                menu.add(4,1,1,"小游戏");
                menu.add(4,2,2,"记单词");
                menu.add(4,3,3,"Shout");
                break;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public void onClick(View v) {
        clickedview=v;
        v.showContextMenu();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getGroupId()){
            case 1:
//                if (item.getGroupId()!=8){
//                    item.setChecked(!item.isChecked());
//                    if (item.isChecked())
//                        day_selected|=(1<<item.getItemId());
//                    else
//                        day_selected&=~(1<<item.getItemId());
//                    Message msg=Message.obtain();
//                    handler.sendMessage(msg);
//                }else{
//                    //TODO work with day_selected
//                }
                break;
            case 2:break;
            case 3:break;
            case 4:break;
            case 5:break;
        }
        return true;//super.onContextItemSelected(item);
    }
    @Override
    public void onContextMenuClosed(Menu menu) {
        // TODO Auto-generated method stub
        super.onContextMenuClosed(menu);
    }

    Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            onClick(clickedview);
        };
    };

}
