package com.mermakov.testgithubclient.data.rest;

import com.mermakov.testgithubclient.data.rest.dto.RepoDataDto;

import retrofit2.http.GET;
import rx.Observable;

public interface GithubApi {

    /**
     * See https://developer.github.com/v3/repos/#list-contributors
     */
    @GET("/user/repos")
    Observable<RepoDataDto> repositories();

    @GET("/user/repos")
    RepoDataDto getRepositories();

}