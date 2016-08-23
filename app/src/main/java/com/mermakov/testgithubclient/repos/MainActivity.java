package com.mermakov.testgithubclient.repos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mermakov.testgithubclient.R;
import com.mermakov.testgithubclient.repos.repo_list.RepoListContract;
import com.mermakov.testgithubclient.repos.repo_list.RepoListPresenter;
import com.mermakov.testgithubclient.repos.repo_list.RepoListView;
import com.mermakov.testgithubclient.repos.search.SearchContract;
import com.mermakov.testgithubclient.repos.search.SearchPresenter;
import com.mermakov.testgithubclient.repos.search.SearchView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RepoListContract.View view;
    private RepoListContract.ActionEvents presenter;

    private SearchContract.View searchView;
    private SearchContract.ActionEvents searchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = new RepoListView(this);
        presenter = new RepoListPresenter(view);
        searchView = new SearchView(this);
        searchPresenter = new SearchPresenter(searchView);
    }
}
