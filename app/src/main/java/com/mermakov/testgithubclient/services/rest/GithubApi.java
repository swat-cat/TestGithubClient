package com.mermakov.testgithubclient.services.rest;

import com.mermakov.testgithubclient.services.rest.dto.RepoDataDto;
import com.mermakov.testgithubclient.services.rest.dto.SearchResultDto;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GithubApi {

    /**
     * See https://developer.github.com/v3/repos/#list-contributors
     */
    @GET("/user/repos")
    Observable<RepoDataDto> repositories();

    @GET("/user/repos")
    RepoDataDto getRepositories();

    @GET("/search/repositories")
    Observable<SearchResultDto> search(@Query(value = "q", encoded = true)String query);


    @GET("/search/repositories")
    SearchResultDto getSearch(@Query(value = "q", encoded = true)String query);

}