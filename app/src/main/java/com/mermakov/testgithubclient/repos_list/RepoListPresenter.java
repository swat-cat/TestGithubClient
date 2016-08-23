package com.mermakov.testgithubclient.repos_list;

import android.util.Log;

import com.mermakov.testgithubclient.App;
import com.mermakov.testgithubclient.Tools;
import com.mermakov.testgithubclient.data.RepoModel;
import com.mermakov.testgithubclient.data.rest.dto.RepoDataDto;

import rx.Subscriber;

/**
 * Created by max_ermakov on 8/23/16.
 */
public class RepoListPresenter implements RepoListContract.ActionEvents {
    private static final String TAG = RepoListPresenter.class.getSimpleName();

    private RepoListContract.View view;
    private RepoModel repoModel;

    public RepoListPresenter(RepoListContract.View view) {
        this.view = view;
        repoModel = App.getInstance().getModelService().getRepoModel(App.getInstance().getPrefManager().getToken());
        resetRepoList();
    }

    @Override
    public void resetRepoList() {
        view.showEmpty(false);
        view.showList(false);
        view.showProgress(true);
        if (repoModel.getRepoDataDto()!=null && !Tools.isNullOrEmpty(repoModel.getRepoDataDto().getRepos())){
            view.setupUI(repoModel.getRepoDataDto().getRepos());
            view.showProgress(false);
            view.showList(true);
        }
        else {
            repoModel.resetRepoData().subscribe(new Subscriber<RepoDataDto>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, e.getLocalizedMessage());
                    e.getStackTrace();
                }

                @Override
                public void onNext(RepoDataDto repoDataDto) {
                    repoModel.setRepoDataDto(repoDataDto);
                    view.setupUI(repoModel.getRepoDataDto().getRepos());
                    view.showProgress(false);
                    view.showList(true);
                }
            });
        }
    }
}
