package com.d2.soundalarm.setting;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by XJX on 13-5-25.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder sfh;
    private MyThread myThread;

    public MySurfaceView(Context context) {
        super(context);
    }

    public MySurfaceView(Context context,AttributeSet attrs){
        super(context, attrs);
        sfh = getHolder();
        sfh.addCallback(this);
        setFocusableInTouchMode(true);
        myThread = new MyThread(sfh, getResources());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            float x=event.getX();
            float y=event.getY();
            myThread.keyDown(x,y);
//            float cx = myThread.getCx();
//            float cy = myThread.getCy();
//            //int minute =(int) Math.round(Math.atan(Math.abs(x-cx)/Math.abs(cy-y))/(2*Math.PI)*60);
//            //double angle = Math.atan(Math.abs(x-cx)/Math.abs(cy-y));
//            if (x>=cx && y<cy) {
//                int minute =(int) Math.round(Math.atan(Math.abs(x-cx)/Math.abs(cy-y))/(2*Math.PI)*60);
//            } else if (x>cx && y>=cy) {
//
//            } else if (x<=cx && y>cy) {
//
//            } else if (x<cx && y<=cy) {
//
//            }
//
//            if (minute<myThread.getMinute()) {
//                myThread.setHour(myThread.getHour() % 12 + 1);
//            }
//            myThread.setMinute(minute);
        }else if(event.getAction()==MotionEvent.ACTION_UP){

        }else if(event.getAction()==MotionEvent.ACTION_MOVE){
            float x=event.getX();
            float y=event.getY();
            myThread.keyMove(x,y);
//            int minute =(int) Math.round(Math.atan((x-myThread.getCx())/(myThread.getCy()-y))/(2*Math.PI)*60);
//            Log.i("minute",String.valueOf(minute));
//            if (minute<myThread.getMinute()) {
//                myThread.setHour(myThread.getHour() % 12 + 1);
//            }
//            myThread.setMinute(minute);
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        myThread.setRun(true);
        myThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        myThread.setRun(false);
    }
}
