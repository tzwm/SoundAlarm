package com.d2.soundalarm;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import java.util.Vector;

/**
 * Created by tzwm on 5/25/13.
 */

//class DrawView extends View{
//
//
//    public DrawView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    @Override
//    public void onDraw(Canvas canvas){
//        //wai 252,226,193
//        //nei 223,216,208
//        Paint wai = new Paint();
//        wai.setColor(Color.rgb(252, 226, 193));
//        canvas.drawCircle(400, 200, 200, wai);
//        wai.setAntiAlias(true);
//
//        Paint nei = new Paint();
//        nei.setColor(Color.rgb(223, 216, 208));
//        canvas.drawCircle(400, 200, 150, nei);
//        nei.setAntiAlias(true);
//    }
//}

public class MainActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topText = (TextView)findViewById(R.id.topView);
        topText.setText("请勿怒摔手机");

        btnTime = (Button)findViewById(R.id.btnTime);
        btnRepeat = (Button)findViewById(R.id.btnRepeat);
        btnDel = (Button)findViewById(R.id.btnDel);
        btnMem = (Button)findViewById(R.id.btnMem);

        OnClickListener list4Button = new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.btnTime)
                    topText.setText("Time");
                if(view.getId() == R.id.btnRepeat)
                    topText.setText("Repeat");
                if(view.getId() == R.id.btnDel)
                    topText.setText("Ring");
                if(view.getId() == R.id.btnMem)
                    topText.setText("Type");
            }
        };

        btnTime.setOnClickListener(list4Button);
        btnRepeat.setOnClickListener(list4Button);
        btnDel.setOnClickListener(list4Button);
        btnMem.setOnClickListener(list4Button);

        img = (ImageView)findViewById(R.id.turntable);
        bitImg = ((BitmapDrawable)(img.getDrawable())).getBitmap();
        img.setOnClickListener(list4Button);
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

        Button btnTmp = (Button)findViewById(R.id.btn0);
        btnTmp.setBackgroundResource(R.drawable.on);

        topText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private Vector<Button> btn;
    private Button btnTime, btnRepeat, btnDel, btnMem;
    private TextView topText;
    private ImageView img;
    private Bitmap bitImg;
}
