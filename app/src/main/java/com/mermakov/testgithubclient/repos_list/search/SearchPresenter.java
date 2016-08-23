package com.mermakov.testgithubclient.repos_list.search;

import android.util.Log;

import com.mermakov.testgithubclient.App;
import com.mermakov.testgithubclient.Tools;
import com.mermakov.testgithubclient.data.RepoModel;
import com.mermakov.testgithubclient.data.rest.dto.RepoDataDto;
import com.mermakov.testgithubclient.data.rest.dto.SearchResultDto;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by max_ermakov on 8/23/16.
 */
public class SearchPresenter implements SearchContract.ActionEvents {
    private static final String TAG = SearchPresenter.class.getSimpleName();

    private SearchContract.View view;
    private RepoModel model;
    private Subscription subscription;

    public SearchPresenter(SearchContract.View view) {
        this.view = view;
        model = App.getInstance().getModelService().getRepoModel(App.getInstance().getPrefManager().getToken());
        setupSearchAction();
        setupIconAction();
    }

    private void setupIconAction(){
        view.iconAction().subscribe(new Subscriber<Void>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Void aVoid) {
                if(((SearchView)view).getState()== SearchView.IconState.CLEAR){
                    view.clear();
                }
            }
        });
    }

    private void setupSearchAction(){
        subscription = view.searchChanges()
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Subscriber<CharSequence>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(CharSequence charSequence) {
                        view.showProgress(true);
                        view.showList(true);
                        search(charSequence.toString());
                    }
                });

    }

    @Override
    public void search(String query) {
        if (Tools.isNullOrEmpty(query)){
            view.clear();
            view.showList(false);
            return;
        }
        query = query+"+user:"+App.getInstance().getPrefManager().getUserName();
        model.searchRepoData(query)
                .subscribe(new Subscriber<SearchResultDto>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(SearchResultDto repoDataDto) {
                        if (repoDataDto!=null){
                            if (repoDataDto.getItems()!=null){
                                view.showProgress(false);
                                view.showClear();
                                if (Tools.isNullOrEmpty(repoDataDto.getItems())){
                                    view.showEmptyMessage(true);
                                    view.showSearchResult(false);
                                }
                                else {
                                    view.showEmptyMessage(false);
                                    view.showSearchResult(true);
                                    view.setupSearchResult(repoDataDto.getItems());
                                }
                            }
                        }
                    }
                });
    }
}
