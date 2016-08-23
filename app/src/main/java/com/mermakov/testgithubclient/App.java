package com.mermakov.testgithubclient;

import android.support.multidex.MultiDexApplication;

import com.mermakov.testgithubclient.services.PrefManager;
import com.mermakov.testgithubclient.services.cache.ModelService;


/**
 * Created by max_ermakov on 8/21/16.
 */
public class App extends MultiDexApplication {
    private static App instance;
    private PrefManager prefManager;
    private ModelService modelService;

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
        modelService = new ModelService(this);
    }

    public PrefManager getPrefManager() {
        return prefManager;
    }

    public ModelService getModelService() {
        return modelService;
    }
}