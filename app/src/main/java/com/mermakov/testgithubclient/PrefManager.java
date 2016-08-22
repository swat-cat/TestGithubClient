package com.mermakov.testgithubclient;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by max_ermakov on 8/21/16.
 */
public class PrefManager {

    private SharedPreferences prefs;
    private Context context;

    private static final String TOKEN = "test_github_client_token";
    private static final String USER_NAME = "test_github_client_username";

    public PrefManager(Context context) {
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public String getToken(){
        return prefs.getString(TOKEN,"");
    }

    public void setToken(String token){
        prefs.edit().putString(TOKEN,token).apply();
    }

    public String getUserName(){
        return prefs.getString(USER_NAME,"");
    }

    public void setUserName(String userName){
        prefs.edit().putString(USER_NAME,userName).apply();
    }
}
