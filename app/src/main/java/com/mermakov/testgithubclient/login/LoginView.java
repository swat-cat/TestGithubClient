package com.mermakov.testgithubclient.login;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.mermakov.testgithubclient.R;

import rx.Observable;

public class LoginView implements LoginContract.View {
    private static final String TAG = LoginView.class.getSimpleName();

    private Activity activity;
    private View root;

    private EditText login;
    private EditText password;
    private Button button;

    private ProgressBar progressBar;

    public LoginView(Activity activity) {
        this.activity = activity;
        root = activity.findViewById(R.id.login_root);
        init();
    }

    private void init(){
        login = (EditText)root.findViewById(R.id.etUserName);
        password = (EditText)root.findViewById(R.id.etPass);
        button = (Button)root.findViewById(R.id.btnSingIn);
        progressBar = (ProgressBar)root.findViewById(R.id.login_progress);
        showProgress(false);
    }

    @Override
    public void showNameError(String message) {
        login.setError(message);
    }

    @Override
    public void showPasswordError(String message) {
        password.setError(message);
    }

    @Override
    public Observable<Void> loginButtonAction() {
        return RxView.clicks(button);
    }

    @Override
    public Observable<CharSequence> loginTextChangeAction() {
        return RxTextView.textChanges(login);
    }

    @Override
    public Observable<CharSequence> passwordTextChangeAction() {
        return RxTextView.textChanges(password);
    }

    @Override
    public void setLoginButtonEnable(boolean enable) {
        button.setEnabled(enable);
    }

    @Override
    public void showUI(boolean show) {
        if(show){
            login.setVisibility(View.VISIBLE);
            password.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }
        else {
            login.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgress(boolean show) {
        if (show){
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public EditText getLogin() {
        return login;
    }

    public EditText getPassword() {
        return password;
    }

    public void clearFields(){
        login.setText("");
        password.setText("");
    }
}
