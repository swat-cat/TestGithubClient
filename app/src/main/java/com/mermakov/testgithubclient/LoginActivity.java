package com.mermakov.testgithubclient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mermakov.testgithubclient.login.LoginContract;
import com.mermakov.testgithubclient.login.LoginPresenter;
import com.mermakov.testgithubclient.login.LoginView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private LoginContract.View view;
    private LoginContract.UserActions presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        view = new LoginView(this);
        presenter = new LoginPresenter(view,this);
    }
}
