package com.mermakov.testgithubclient.rest;

import com.mermakov.testgithubclient.rest.dto.AccessTokenDTO;
import com.mermakov.testgithubclient.rest.dto.DataDto;
import com.mermakov.testgithubclient.rest.dto.RepoDto;
import com.mermakov.testgithubclient.rest.dto.RequestTokenDTO;
import com.mermakov.testgithubclient.rest.dto.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface GithubApi {

    /**
     * See https://developer.github.com/v3/repos/#list-contributors
     */
    @GET("/user/repos")
    Observable<DataDto> repositories();

    @GET("/user/repos")
    DataDto getRepositories();

    /**
     * See https://developer.github.com/v3/users/
     */
    @GET("/users/{user}")
    Observable<User> user(@Path("user") String user);

    /**
     * See https://developer.github.com/v3/users/
     */
    @GET("/users/{user}")
    User getUser(@Path("user") String user);

    @POST("/login/oauth/access_token")
    AccessTokenDTO getRequestToken(@Body RequestTokenDTO requestTokenDTO);

    @POST("/login/oauth/access_token")
    Observable<AccessTokenDTO> requestToken(@Body RequestTokenDTO requestTokenDTO);

}