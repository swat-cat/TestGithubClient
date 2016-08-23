package com.mermakov.testgithubclient.repos_list;

import com.mermakov.testgithubclient.data.rest.dto.RepoDto;

import java.util.List;

/**
 * Created by max_ermakov on 8/23/16.
 */
public interface RepoListContract {
    interface View{
        void setupUI(List<RepoDto> repoDtos);
        void showList(boolean show);
        void showProgress(boolean show);
        void showEmpty(boolean show);
    }
    interface ActionEvents{
        void resetRepoList();
    }
}
