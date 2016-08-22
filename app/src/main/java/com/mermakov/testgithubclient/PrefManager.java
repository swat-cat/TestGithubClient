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
}
