package com.mermakov.testgithubclient.rest.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by max_ermakov on 8/22/16.
 */
public class DataDto {
    @SerializedName("data")
    private List<RepoDto> repos;

    public List<RepoDto> getRepos() {
        return repos;
    }

    public void setRepos(List<RepoDto> repos) {
        this.repos = repos;
    }
}
