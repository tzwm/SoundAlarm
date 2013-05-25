package com.d2.soundalarm;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by tzwm on 5/25/13.
 */

class DrawView extends View{


    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas){
        Paint p = new Paint();
        canvas.drawCircle(60, 20, 10, p);
        p.setAntiAlias(true);

    }
}

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


//        img = (ImageView)findViewById(R.id.turntable);
//        bitImg = ((BitmapDrawable)(img.getDrawable())).getBitmap();
//        img.setOnClickListener(listTime);
//        img.setOnTouchListener(new Button.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                topText.setText(String.valueOf(String.format("%x", bitImg.getPixel((int) (motionEvent.getX()), (int) (motionEvent.getY())))));
//                if(( bitImg.getPixel((int)(motionEvent.getX()), (int)(motionEvent.getY())) & 0xff000000 ) != 0){
//                    return true;
//                }
//                return false;
//            }
//        });

        topText.setMovementMethod(LinkMovementMethod.getInstance());
    }



    private Button btnTime, btnRepeat, btnRing, btnType;
    private TextView topText;
    private ImageView img;
    private Bitmap bitImg;
}