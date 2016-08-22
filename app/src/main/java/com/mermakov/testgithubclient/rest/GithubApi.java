package com.mermakov.testgithubclient.rest;

import com.mermakov.testgithubclient.rest.dto.DataDto;

import retrofit2.http.GET;
import rx.Observable;

public interface GithubApi {

    /**
     * See https://developer.github.com/v3/repos/#list-contributors
     */
    @GET("/user/repos")
    Observable<DataDto> repositories();

    @GET("/user/repos")
    DataDto getRepositories();

}