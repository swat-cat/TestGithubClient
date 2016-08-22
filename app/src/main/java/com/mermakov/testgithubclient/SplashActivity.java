package com.mermakov.testgithubclient;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mermakov.testgithubclient.auth.SimpleAuthManager;
import com.mermakov.testgithubclient.rest.GithubApi;
import com.mermakov.testgithubclient.rest.GithubService;
import com.mermakov.testgithubclient.rest.dto.AccessTokenDTO;
import com.mermakov.testgithubclient.rest.dto.RepoDto;
import com.mermakov.testgithubclient.rest.dto.User;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity{
    public static final String TAG = SplashActivity.class.getSimpleName();

    private final Handler handler = new Handler();
    private ImageView logo;
    private ObjectAnimator scaleDown;

    private SimpleAuthManager authManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        authManager = new SimpleAuthManager(this);
        logo = (ImageView) findViewById(R.id.logo);
        preparePulse();
        scaleDown.start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                authManager.openLoginInBrowser();
            }
        }, 3000);
    }

    private void preparePulse(){
        scaleDown = ObjectAnimator.ofPropertyValuesHolder(logo,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(700);

        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SimpleAuthManager.WEBVIEW_REQUEST_CODE && resultCode == RESULT_OK)
            authManager.getAccessToken(data.getData()).subscribe(new Subscriber<AccessTokenDTO>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG,e.getLocalizedMessage());
                }

                @Override
                public void onNext(AccessTokenDTO accessTokenDTO) {
                    Log.d(TAG,accessTokenDTO.access_token);
                }
            });
    }
}
