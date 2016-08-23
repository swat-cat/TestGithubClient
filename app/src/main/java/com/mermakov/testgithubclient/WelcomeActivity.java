package com.mermakov.testgithubclient;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.mermakov.testgithubclient.login.LoginActivity;
import com.mermakov.testgithubclient.repos.MainActivity;

public class WelcomeActivity extends AppCompatActivity{
    public static final String TAG = WelcomeActivity.class.getSimpleName();

    private final Handler handler = new Handler();
    private ImageView logo;
    private ObjectAnimator scaleDown;
    private boolean nextActivityStarted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            Log.i(TAG,"Illegal splash call. Splash is not at the root of task!");
            finish();
            return;
        }
        setContentView(R.layout.splash_activity);
        logo = (ImageView) findViewById(R.id.logo);
        preparePulse();
        scaleDown.start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(TextUtils.isEmpty(App.getInstance().getPrefManager().getToken())) {
                    Intent loginIntent = new Intent(App.getInstance().getApplicationContext(), LoginActivity.class);
                    nextActivityStarted =true;
                    startActivity(loginIntent);
                }
                else {
                    nextActivityStarted = true;
                    Intent mainIntent = new Intent(App.getInstance().getApplicationContext(),MainActivity.class);
                    startActivity(mainIntent);
                }
            }
        },3000);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(nextActivityStarted){
            finish();
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
