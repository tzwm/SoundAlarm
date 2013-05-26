package com.d2.soundalarm;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

public class BeeSurface extends SurfaceView implements SurfaceHolder.Callback,Runnable{
	private Boolean run=false;
	private Paint paint;
	private SurfaceHolder holder;
	private Canvas canvas;
	private long tick;
	private long deltatick;
	private long lasttick = 0;
	//private Date date=new Date();
	private int w;
	private int h; 
	private double x;
	private double y;
	private double r;
	private double dir;
	private double v;
	private int[] colors;
	static Handler handler;
	private Context con; 
	
	private boolean getIsRun(){
		boolean ret;
		synchronized (run) {
			ret= run;
		}
		return ret;
	}
	
	private void setIsRun(boolean b){
		synchronized (run) {
			run=b;
		}
	}
	
	public BeeSurface(Context context, Handler handler) {
		super(context);
		holder=getHolder();
		holder.addCallback(this);
		paint=new Paint();
		con=context;
		this.handler=handler;
		setFocusable(true);
		setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				double cx=x-event.getX();
				double cy=x-event.getY();
				double dis=Math.sqrt(cx*cx+cy*cy);
				if (dis<r*1.5){
					Message msg=Message.obtain();
					BeeSurface.handler.sendMessage(msg);
				}
				return false;
			}
		});
	}
	
	
	int cl;
	double off;
	int cnt;
	private void draw()
	{
		/*
		Log.d("deltatick",""+deltatick);
		Log.d("v",""+v);
		Log.d("dir",""+dir);
		Log.d("cos",""+Math.cos(dir*Math.PI/180));
		Log.d("sin",""+Math.sin(dir*Math.PI/180));
		Log.d("x+",""+v*deltatick*Math.cos(dir*Math.PI/180));
		Log.d("y+",""+v*deltatick*Math.sin(dir*Math.PI/180));//*/
		try{
			canvas = holder.lockCanvas();
			canvas.drawColor(Color.rgb(113,85,99));
			x+=v*deltatick*Math.cos(dir*Math.PI/180);
			y+=v*deltatick*Math.sin(dir*Math.PI/180);
			//TODO draw bg
			if (x+r>w){ dir=180-dir; x=w-r;}
			if (x-r<0){ dir=180-dir; x=r;}
			if (y+r>h){ dir=-dir; y=h-r;}
			if (y-r<0){ dir=-dir; y=r;}
			double cloudwidth=r/colors.length;
			++cnt;
			if (cnt>1) {off=off-1;cnt=0;}
			if (off<0){cl=(cl+1)%colors.length; off+=cloudwidth;}
			
			paint.setTypeface(Typeface.create("黑体", 0));
			paint.setTextSize((int)(r+1));
			SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");       
			Date curDate=new Date(System.currentTimeMillis());//��ȡ��ǰʱ��       
			String str=formatter.format(curDate);    
			paint.setColor(Color.rgb(252,226,193));
			canvas.drawText(str,50 ,100, paint);
			
			int i=0;
			while((r-off-cloudwidth*(i-1))>0){
				int t=(cl+i)%colors.length;
				paint.setColor(colors[t]);
				//double alpha=Math.min(Math.min(255,64*r-off-cloudwidth*(i-1)),255);
				paint.setAlpha(255);
				if (i==0) canvas.drawCircle((float)x, (float)y, (float)(r), paint);	else
				canvas.drawCircle((float)x, (float)y, (float)(r-off-cloudwidth*(i-1)), paint);
				++i;
			}
			
			//paint.setColor(Color.rgb(252, 226, 193));
			//canvas.drawCircle(x, y, r, paint);
			paint.setColor(Color.RED);
			paint.setAlpha(128);
			//canvas.drawCircle((float)x, (float)y, (float)r, paint);
			
			holder.unlockCanvasAndPost(canvas);			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(getIsRun()){
			try {
				lasttick=tick;
				tick=new Date().getTime();
				//Log.d("Tick",""+tick);
				deltatick=tick-lasttick;
				if (lasttick!=0) draw();
				Thread.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		w=getWidth();
		h=getHeight();
		r=w/10;
		dir=17;
		v=1;
		x=r+1;
		y=r+1;
		colors=new int[]{
				Color.rgb(252, 226, 193),
				//Color.rgb(255, 255, 255),
				//Color.rgb(219, 214, 208),
				Color.rgb(113, 85, 99)
				};
		
		setIsRun(true);
		new Thread(this).start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		setIsRun(false);
		
	}

}
