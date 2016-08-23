package com.mermakov.testgithubclient.login;

import android.widget.EditText;

import rx.Observable;

public interface LoginContract {
    interface View{
        void showNameError(String message);
        void showPasswordError(String message);
        void setLoginButtonEnable(boolean enable);
        Observable<Void> loginButtonAction();
        Observable<CharSequence> loginTextChangeAction();
        Observable<CharSequence> passwordTextChangeAction();
        EditText getLogin();
        EditText getPassword();
        void clearFields();
    }
    interface UserActions{
        void loginAction();
    }
}
