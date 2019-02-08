package com.hackathon.offlinemaps.FrontEnd;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.hackathon.offlinemaps.MainActivity;
import com.hackathon.offlinemaps.R;

public class SplashScreenActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    ImageView line3;
    ImageView line1;
    Animation uptodown,downtoup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        //line1 =findViewById(R.id.line1);
        line3 =findViewById(R.id.line3);
        //uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        //downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        //line1.setAnimation(uptodown);
        //line3.setAnimation(downtoup);
        AlphaAnimation anim1 = new AlphaAnimation(0.0f, 1.0f);
        anim1.setStartOffset(200);
        anim1.setDuration(1000);
        //anim1.setRepeatCount(10);
        //anim1.setRepeatMode(Animation.ZORDER_BOTTOM);
        line3.startAnimation(anim1);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // Create an Intent that will start the MainActivity.
                Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                SplashScreenActivity.this.startActivity(mainIntent);
                SplashScreenActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
