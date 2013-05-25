package com.d2.soundalarm;

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
import android.text.format.Time;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

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
	private int[] beecolors;
	private double cloudwidth;
	
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
	
	public BeeSurface(Context context) {
		super(context);
		holder=getHolder();
		holder.addCallback(this);
		paint=new Paint();
		setFocusable(true);
	}
	
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
			canvas.drawColor(Color.BLACK);
			x+=v*deltatick*Math.cos(dir*Math.PI/180);
			y+=v*deltatick*Math.sin(dir*Math.PI/180);
			//TODO draw bg
			if (x+r>w){ dir=180-dir; x=w-r;}
			if (x-r<0){ dir=180-dir; x=r;}
			if (y+r>h){ dir=-dir; y=h-r;}
			if (y-r<0){ dir=-dir; y=r;}
			int off=((int)tick/100)%(int)r;
			double cr=r+beecolors.length*cloudwidth;
			double ca=((int)r)/beecolors.length;
			double aa=255/(beecolors.length+1);
			for (int i=0;i<beecolors.length;++i){
				paint.setColor(beecolors[i]);
				canvas.drawCircle((float)x, (float)y, (float)(r-cloudwidth*i), paint);
			}
			paint.setColor(Color.RED);
			paint.setAlpha(128);
			canvas.drawCircle((float)x, (float)y, (float)r, paint);
			
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
		v=0.5;
		x=r+1;
		y=r+1;
		beecolors=new int[]{Color.RED,Color.YELLOW,Color.BLUE,Color.GREEN};
		cloudwidth=r/(beecolors.length);
		
		setIsRun(true);
		new Thread(this).start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		setIsRun(false);
		
	}

}
