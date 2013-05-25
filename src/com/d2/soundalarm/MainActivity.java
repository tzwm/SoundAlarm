package com.d2.soundalarm;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by tzwm on 5/25/13.
 */

public class MainActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTime = (Button)findViewById(R.id.btnTime);
        btnRepeat = (Button)findViewById(R.id.btnRepeat);
        btnRing = (Button)findViewById(R.id.btnRing);
        btnType = (Button)findViewById(R.id.btnType);

        topText = (TextView)findViewById(R.id.topView);

        topText.setText("aha");

        OnClickListener listTime = new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                topText.setText("Time");
            }
        };
        btnTime.setOnClickListener(listTime);

        topText.setMovementMethod(LinkMovementMethod.getInstance());
    }



    private Button btnTime, btnRepeat, btnRing, btnType;
    private TextView topText;
}