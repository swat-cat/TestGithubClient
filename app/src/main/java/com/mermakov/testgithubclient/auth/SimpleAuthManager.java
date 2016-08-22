package com.mermakov.testgithubclient.auth;

import android.app.Activity;

import com.mermakov.testgithubclient.rest.GithubApi;
import com.mermakov.testgithubclient.rest.GithubService;

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

    public SimpleAuthManager(Activity activity, String credentials) {
        this.activity = activity;
        githubApi = GithubService.createGithubService(credentials);
    }

}
