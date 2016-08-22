package com.mermakov.testgithubclient.login;

import android.text.TextUtils;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func2;

import static android.text.TextUtils.isEmpty;

public class LoginPresenter implements LoginContract.UserActions{
    private static final String TAG = LoginPresenter.class.getSimpleName();

    private LoginContract.View view;
    private Observable<CharSequence> nameChangeObservable;
    private Observable<CharSequence> passwordChangeObservable;
    private Subscription loginSubscription = null;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        passwordChangeObservable = view.passwordTextChangeAction().skip(1);
        nameChangeObservable = view.loginTextChangeAction().skip(1);
        setupLoginEnable();
        setupLoginAction();
    }

    private void setupLoginEnable(){
        loginSubscription = Observable.combineLatest(nameChangeObservable, passwordChangeObservable, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence name, CharSequence password) {
                boolean nameValid = !isEmpty(name)&&name.length()>2;
                if (!nameValid){
                    view.showNameError("Name is too short");
                }
                else {
                }
                boolean passValid = !isEmpty(password) && password.length() > 8;
                if (!passValid){
                    view.showPasswordError("Password is invalid");
                }
                return nameValid && passValid;
            }
        }).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                Log.d(TAG,"Completed");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean valid) {
                view.setLoginButtonEnable(valid);
            }
        });
    }

    private void setupLoginAction(){
        view.loginButtonAction().subscribe(new Subscriber<Void>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.getLocalizedMessage());
            }

            @Override
            public void onNext(Void aVoid) {
                Log.d(TAG, "Click");
            }
        });
    }

    @Override
    public void loginAction() {

    }
}
