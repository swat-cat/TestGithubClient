package com.mermakov.testgithubclient.repos_list.search;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.mermakov.testgithubclient.R;
import com.mermakov.testgithubclient.data.rest.dto.RepoDto;
import com.mermakov.testgithubclient.data.rest.dto.SerachResultItemDTO;
import com.mermakov.testgithubclient.repos_list.UserRepositoriesAdapter;

import java.util.List;

import rx.Observable;

/**
 * Created by max_ermakov on 8/23/16.
 */
public class SearchView implements SearchContract.View {
    private static final String TAG = SearchView.class.getSimpleName();

    private Activity activity;
    private View root;
    private View serchRoot;
    private AutoCompleteTextView searchField;
    private ImageView searchIcon;
    private FrameLayout searchResultContainer;
    private RecyclerView searchResultList;
    private TextView searchEmptyMessage;
    private ProgressBar searchProgress;
    private SearchRepoAdapter adapter;
    private int containerTop;
    private int containerBottom;

    public enum IconState{
        SEARCH,CLEAR
    }

    private IconState state;

    public SearchView(Activity activity) {
        this.activity = activity;
        root = activity.findViewById(R.id.repos_list_root);
        serchRoot = activity.findViewById(R.id.search_view);
        init();
    }

    private void init(){
        searchField = (AutoCompleteTextView)activity.findViewById(R.id.search_field);
        searchIcon = (ImageView)activity.findViewById(R.id.search_icon);
        searchResultContainer = (FrameLayout)root.findViewById(R.id.search_result_container);
        searchResultList = (RecyclerView)root.findViewById(R.id.search_result_list);
        searchEmptyMessage = (TextView)root.findViewById(R.id.search_empty_message);
        searchProgress = (ProgressBar)root.findViewById(R.id.search_progress);
        showSearch();
        hideAll();
    }

    public void setupAdapter(List<SerachResultItemDTO> repoDtos){
        LinearLayoutManager llm = new LinearLayoutManager(activity);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        searchResultList.setLayoutManager(llm);
        if(repoDtos.isEmpty()){
            showEmptyMessage(true);
        }
        adapter = new SearchRepoAdapter(repoDtos);
        searchResultList.setAdapter(adapter);
    }

    @Override
    public void setupSearchResult(List<SerachResultItemDTO> repoDtos) {
        setupAdapter(repoDtos);
    }

    @Override
    public void showList(boolean show) {
        containerTop = searchResultContainer.getTop();
        containerBottom = searchResultContainer.getHeight();
        if (show){
            searchResultContainer.setVisibility(View.VISIBLE);
            expandCollapse(containerTop-containerBottom,containerTop);
        }
        else {
            expandCollapse(containerTop,containerTop-containerBottom);
        }
    }

    @Override
    public void showSearchResult(boolean show) {
        if (show){
            searchResultList.setVisibility(View.VISIBLE);
        }
        else {
            searchResultList.setVisibility(View.GONE);
        }
    }

    @Override
    public void showEmptyMessage(boolean show) {
        if (show){
            searchEmptyMessage.setVisibility(View.VISIBLE);
        }
        else {
            searchEmptyMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgress(boolean show) {
        if (show){
            searchProgress.setVisibility(View.VISIBLE);
        }
        else {
            searchProgress.setVisibility(View.GONE);
        }
    }

    @Override
    public void showClear() {
        searchIcon.setImageDrawable(searchIcon.getResources().getDrawable(R.mipmap.delete));
        state = IconState.CLEAR;
    }

    @Override
    public void showSearch() {
        searchIcon.setImageDrawable(searchIcon.getResources().getDrawable(R.mipmap.search));
        state = IconState.SEARCH;
    }

    @Override
    public Observable<CharSequence> searchChanges() {
        return RxTextView.textChanges(searchField);
    }

    @Override
    public Observable<Void> iconAction() {
        return RxView.clicks(searchIcon);
    }

    private void expandCollapse(int start, int end){
        ObjectAnimator expandCollapse = ObjectAnimator.ofFloat(searchResultContainer,"y",start,end).setDuration(500);
        expandCollapse.start();
    }

    private void hideAll(){
        showProgress(false);
        showEmptyMessage(false);
    }

    @Override
    public void clear() {
        if (searchField.getText().length()>0){
            searchField.setText("");
        }
        hideAll();
        showSearch();
    }

    public IconState getState() {
        return state;
    }
}
