package com.mermakov.testgithubclient.repos_list.search;

import com.mermakov.testgithubclient.data.rest.dto.RepoDto;
import com.mermakov.testgithubclient.data.rest.dto.SerachResultItemDTO;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by max_ermakov on 8/23/16.
 */
public interface SearchContract {
    interface View{
        void setupSearchResult(List<SerachResultItemDTO> repoDtos);
        void showList(boolean show);
        void showSearchResult(boolean show);
        void showEmptyMessage(boolean show);
        void showProgress(boolean show);
        void showClear();
        void showSearch();
        void clear();
        Observable<CharSequence> searchChanges();
        Observable<Void> iconAction();

    }
    interface ActionEvents{
        void search(String query);
    }
}
