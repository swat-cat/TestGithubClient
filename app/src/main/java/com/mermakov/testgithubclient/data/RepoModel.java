package com.mermakov.testgithubclient.data;

import com.mermakov.testgithubclient.data.rest.GithubApi;
import com.mermakov.testgithubclient.data.rest.GithubService;
import com.mermakov.testgithubclient.data.rest.RetryWithDelay;
import com.mermakov.testgithubclient.data.rest.dto.RepoDataDto;
import com.mermakov.testgithubclient.data.rest.dto.SearchResultDto;

import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by max_ermakov on 8/22/16.
 */
public class RepoModel {
    private static final String TAG = RepoModel.class.getSimpleName();

    private GithubApi githubApi;
    private RepoDataDto repoDataDto;

    public RepoModel(String cridentials) {
        githubApi = GithubService.createGithubService(cridentials);
    }

    public Observable<RepoDataDto> resetRepoData(){
        return githubApi.repositories()
                .retryWhen(new RetryWithDelay(5,1000))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SearchResultDto> searchRepoData(String query){
        return githubApi.search(query)
                .retryWhen(new RetryWithDelay(5,1000))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void setRepoDataDto(RepoDataDto repoDataDto) {
        this.repoDataDto = repoDataDto;
    }

    public RepoDataDto getRepoDataDto() {
        return repoDataDto;
    }
}
