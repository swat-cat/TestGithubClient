package com.mermakov.testgithubclient.auth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.mermakov.testgithubclient.R;
import com.mermakov.testgithubclient.rest.GithubApi;
import com.mermakov.testgithubclient.rest.GithubService;
import com.mermakov.testgithubclient.rest.dto.AccessTokenDTO;
import com.mermakov.testgithubclient.rest.dto.RequestTokenDTO;
import com.mermakov.testgithubclient.utils.Constants;

import okhttp3.HttpUrl;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by max_ermakov on 8/20/16.
 */
public class SimpleAuthManager {
    private static final String TAG = SimpleAuthManager.class.getSimpleName();

    public static final String OAUTH_HOST = "www.github.com";
    public static final String INTENT_EXTRA_URL = "url";
    public static int WEBVIEW_REQUEST_CODE = 0;

    private Activity activity;
    private GithubApi githubApi;

    public SimpleAuthManager(Activity activity) {
        this.activity = activity;
        githubApi = GithubService.createGithubService();
    }

}
