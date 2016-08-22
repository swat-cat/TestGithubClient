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

    public void openLoginInBrowser() {
        String initialScope = "user,public_repo,repo,delete_repo,notifications,gist";
        HttpUrl.Builder url = new HttpUrl.Builder()
                .scheme("https")
                .host(OAUTH_HOST)
                .addPathSegment("login")
                .addPathSegment("oauth")
                .addPathSegment("authorize")
                .addQueryParameter("client_id",Constants.CLIENT_ID)
                .addQueryParameter("scope", initialScope);

        Intent intent = new Intent(activity, LoginWebViewActivity.class);
        intent.putExtra(INTENT_EXTRA_URL, url.toString());
        activity.startActivityForResult(intent, WEBVIEW_REQUEST_CODE);
    }

    public Observable<AccessTokenDTO> getAccessToken(Uri uri){
        String code = uri.getQueryParameter("code");
        RequestTokenDTO tokenDTO = new RequestTokenDTO();
        tokenDTO.client_id = Constants.CLIENT_ID;
        tokenDTO.client_secret = Constants.CLIENT_SECRET;
        tokenDTO.redirect_uri = Constants.REDIRECT_URL;
        tokenDTO.code = code;
        return githubApi.requestToken(tokenDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
