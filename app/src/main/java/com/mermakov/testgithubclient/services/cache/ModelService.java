package com.mermakov.testgithubclient.services.cache;

import com.mermakov.testgithubclient.repos.RepoModel;

/**
 * Created by max_ermakov on 8/22/16.
 */
public class ModelService {
    private Object parent;

    public ModelService(Object parent) {
        this.parent = parent;
    }

    private MapHolder<String,RepoModel> repoModel = new WeakMapHolder<String, RepoModel>() {
        @Override
        protected RepoModel create(String key) {
            return new RepoModel(key);
        }
    };

    public RepoModel getRepoModel(String key) {
        return repoModel.get(key);
    }
}
