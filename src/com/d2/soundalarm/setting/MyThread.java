package com.d2.soundalarm.setting;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.TextView;
import com.d2.soundalarm.R;

/**
 * Created by XJX on 13-5-25.
 */
public class MyThread extends Thread {
    private SurfaceHolder sfh;
    private Boolean isRun;

    private Resources res;
    private Bitmap b1,b2;
    private int cx,cy;
    private int b1x,b1y,b2x,b2y,b1w,b1h,b2w,b2h;
    private int b1r,b2r;
    private int textX,textY;
    private int mode,ap;
    public static int hour,minute;
    private int rotate;
    private Typeface mFace;

//    private int hour;
//    private int minute;
//    private float cx;
//    private float cy;
//    private float r;
//    private float len_hour;
//    private float len_min;

    public MyThread(SurfaceHolder sfh, Resources res) {
        this.sfh = sfh;
        isRun = true;
        this.res = res;
        //mFace = Typeface.createFromAsset(res.getAssets(),"impact.ttf");
        b1 = BitmapFactory.decodeResource(res, R.drawable.b1_);
        b2 = BitmapFactory.decodeResource(res, R.drawable.b2_);
        rotate = 0;
        hour = 3;
        minute = 15;
        mode = 0;
        ap = 0;
    }

    @Override
    public void run() {
        Canvas c = null;
        while (isRun()) {
            try {
                c = sfh.lockCanvas();
                cy = cx = c.getWidth()/2;
                b1h = b1w = c.getWidth()*2/3;
                b1y = b1x = (c.getWidth()-b1w)/2;
                b2h = b2w = b1w*2/3;
                b2y = b2x = cx-b2w/2;
                b1r = b1w/2;
                b2r = b1w/6;
                Paint p = new Paint();
                c.drawColor(Color.argb(255,113,85,99));
                c.drawBitmap(b1, null, new Rect(b1x,b1y,b1x+b1w,b1y+b1h), p);
                Matrix m = new Matrix();
                m.postScale((float)b2w/b2.getWidth(),(float)b2h/b2.getHeight());
                Bitmap bb2 = Bitmap.createBitmap(b2,0,0,b2.getWidth(),b2.getHeight(),m,true);
                //c.drawBitmap(bb2, b2x, b2y, p);
                Matrix m1 = new Matrix();
                m1.postRotate(rotate);
               // Log.i("Rotate",String.valueOf(rotate));
                bb2 = Bitmap.createBitmap(bb2,0,0,bb2.getWidth(),bb2.getHeight(),m1,true);
                c.drawBitmap(bb2, cx-bb2.getWidth()/2, cy-bb2.getHeight()/2, p);

                p.setColor(Color.argb(255,113,85,99));
                p.setTextSize(b1w / 8);
                //p.setTypeface(Typeface.create("impact", Typeface.BOLD));
                p.setTypeface(mFace);
                p.setTextAlign(Paint.Align.CENTER);
                Rect textBounds = new Rect();
                String time = getTimeString();
                p.getTextBounds(time, 0, time.length(), textBounds);//get text bounds, that can get the text width and height
                int textHeight = textBounds.bottom - textBounds.top;
                //Log.i("TAG","bounds: left = "+textBounds.left + ", right = "+textBounds.right+", top = "+textBounds.top+", bottom = "+textBounds.bottom);
                c.drawText(time, cx, cy + textHeight/2, p);

                Thread.sleep(0);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (c != null) {
                    sfh.unlockCanvasAndPost(c);
                }
            }
        }
    }

    private void setTime() {
        if (mode==0) {
            minute = ((rotate+90)%360)*60/360;
        } else if (mode==1) {
            int t = ((rotate+90)%360)*12/360;
//            if (t<hour) {
//                hour = t+12*ap;
//                ap = 1 - ap;
//                Log.i("!!!!!",""+t+"  "+ap);
//                return;
//            }
            hour = t;
        }
    }

    private String getTimeString() {
        String t;
        if (hour<10) t = "0"+hour;
        else t = String.valueOf(hour);
        if (minute<10) return t+":"+"0"+minute;
        else return t+":"+minute;
    }

    public boolean isRun() {
        boolean ret;
        synchronized (isRun) {
            ret = isRun;
        }
        return ret;
    }

    public void setRun(boolean run) {
        synchronized (isRun) {
            isRun = run;
        }
    }

    public int getRotate() {
        return rotate;
    }

    public void setRotate(int rotate) {
        this.rotate = rotate;
    }

    public void keyDown(float x, float y) {
        if ((x-cx)*(x-cx)+(y-cy)*(y-cy)<=b2r*b2r) {
            mode = 1-mode;
            return;
        }
        if ((x-cx)*(x-cx)+(y-cy)*(y-cy)>b1r*b1r) return;
        if (x>cx && y>=cy) {
            setRotate((int)Math.round(Math.atan((y-cy)/(x-cx))/Math.PI*180));
        } else if (x<=cx && y>cy) {
            setRotate((int)Math.round(Math.atan((cx-x)/(y-cy))/Math.PI*180)+90);
        } else if (y<=cy && x<cx) {
            setRotate((int)Math.round(Math.atan((cy-y)/(cx-x))/Math.PI*180)+180);
        } else if (x>=cx && y<cy) {
            setRotate((int)Math.round(Math.atan((x-cx)/(cy-y))/Math.PI*180)+270);
        }
    }

    public void keyMove(float x, float  y) {
        if ((x-cx)*(x-cx)+(y-cy)*(y-cy)<=b2r*b2r) return;
        if ((x-cx)*(x-cx)+(y-cy)*(y-cy)>b1r*b1r) return;
        if (x>cx && y>=cy) {
            setRotate((int)Math.round(Math.atan((y-cy)/(x-cx))/Math.PI*180));
        } else if (x<=cx && y>cy) {
            setRotate((int)Math.round(Math.atan((cx-x)/(y-cy))/Math.PI*180)+90);
        } else if (y<=cy && x<cx) {
            setRotate((int)Math.round(Math.atan((cy-y)/(cx-x))/Math.PI*180)+180);
        } else if (x>=cx && y<cy) {
            setRotate((int)Math.round(Math.atan((x-cx)/(cy-y))/Math.PI*180)+270);
        }
        setTime();
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
