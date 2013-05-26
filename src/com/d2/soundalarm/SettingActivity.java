package com.d2.soundalarm;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingActivity extends Activity {

    private ImageButton cancelButton;
    private ImageButton saveButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        cancelButton =(ImageButton) findViewById(R.id.cancel);
        cancelButton.setImageResource(R.drawable.cancel);
        saveButton =(ImageButton) findViewById(R.id.save);
        saveButton.setImageResource(R.drawable.save);

    }



}
