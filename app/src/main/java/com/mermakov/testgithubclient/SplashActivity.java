package com.mermakov.testgithubclient;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity{
    public static final String TAG = SplashActivity.class.getSimpleName();

    private final Handler handler = new Handler();
    private ImageView logo;
    private ObjectAnimator scaleDown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        logo = (ImageView) findViewById(R.id.logo);
        preparePulse();
        scaleDown.start();
        if(TextUtils.isEmpty(App.getInstance().getPrefManager().getToken())){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(App.getInstance().getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            },3000);
        }
        else {

        }

    }

    private void preparePulse(){
        scaleDown = ObjectAnimator.ofPropertyValuesHolder(logo,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(700);

        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
    }
}
