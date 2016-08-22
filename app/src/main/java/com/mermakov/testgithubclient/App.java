package com.mermakov.testgithubclient;

import android.support.multidex.MultiDexApplication;

/**
 * Created by max_ermakov on 8/21/16.
 */
public class App extends MultiDexApplication {
    private static App instance;
    private PrefManager prefManager;

    public static App getInstance() {
        if (instance == null) {
            throw new RuntimeException("Application initialization error!");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        prefManager = new PrefManager(this);
    }

    public PrefManager getPrefManager() {
        return prefManager;
    }
}