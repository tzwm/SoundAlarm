package com.d2.soundalarm;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceView;

public class BeeActivity extends Activity {
	
	SurfaceView beeSurface;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		beeSurface=new BeeSurface(this);
		setContentView(beeSurface);	
	}

}
