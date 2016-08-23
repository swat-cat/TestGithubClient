package com.mermakov.testgithubclient.repos.repo_list;

import android.util.Log;
import android.widget.Toast;

import com.mermakov.testgithubclient.App;
import com.mermakov.testgithubclient.utils.Tools;
import com.mermakov.testgithubclient.repos.RepoModel;
import com.mermakov.testgithubclient.services.rest.dto.RepoDataDto;

import java.io.IOException;

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
                    if (e instanceof IOException){
                        Toast.makeText(App.getInstance().getApplicationContext(),"Internet connection lost!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(App.getInstance().getApplicationContext(),"Bad credentials!",Toast.LENGTH_LONG).show();
                    }
                    view.showEmpty(true);

                }

                @Override
                public void onNext(RepoDataDto repoDataDto) {
                    if(repoDataDto!=null){
                        if(!Tools.isNullOrEmpty(repoDataDto.getRepos())){
                            repoModel.setRepoDataDto(repoDataDto);
                            view.setupUI(repoModel.getRepoDataDto().getRepos());
                            view.showProgress(false);
                            view.showList(true);
                        }
                        else {
                            view.showEmpty(true);
                        }
                    }
                }
            });
        }
    }
}
