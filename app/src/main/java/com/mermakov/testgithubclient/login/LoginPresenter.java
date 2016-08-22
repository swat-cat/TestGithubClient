package com.mermakov.testgithubclient.login;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.mermakov.testgithubclient.App;
import com.mermakov.testgithubclient.MainActivity;
import com.mermakov.testgithubclient.Tools;
import com.mermakov.testgithubclient.data.RepoModel;
import com.mermakov.testgithubclient.data.rest.dto.RepoDataDto;

import okhttp3.Credentials;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func2;

import static android.text.TextUtils.isEmpty;

public class LoginPresenter implements LoginContract.UserActions{
    private static final String TAG = LoginPresenter.class.getSimpleName();

    private LoginContract.View view;
    private Activity activity;
    private Observable<CharSequence> nameChangeObservable;
    private Observable<CharSequence> passwordChangeObservable;
    private Subscription loginSubscription = null;

    public LoginPresenter(LoginContract.View view, Activity activity) {
        this.view = view;
        this.activity = activity;
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
                final String login = view.getLogin().getText().toString().trim();
                String password = view.getPassword().getText().toString().trim();
                final String credentials = Credentials.basic(login, password);
                App.getInstance().getModelService().getRepoModel(credentials)
                    .resetRepoData().subscribe(new Subscriber<RepoDataDto>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RepoDataDto repoDataDto) {
                        if (repoDataDto!=null){
                            if (!Tools.isNullOrEmpty(repoDataDto.getRepos())){
                                App.getInstance().getModelService().getRepoModel(credentials).setRepoDataDto(repoDataDto);
                                App.getInstance().getPrefManager().setToken(credentials);
                                App.getInstance().getPrefManager().setUserName(login);
                                loginAction();
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void loginAction() {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }
}
