package com.mermakov.testgithubclient.repos_list;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mermakov.testgithubclient.R;
import com.mermakov.testgithubclient.data.rest.dto.RepoDto;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by max_ermakov on 8/23/16.
 */
public class RepoListView implements RepoListContract.View{
    private static final String TAG = RepoListView.class.getSimpleName();

    private Activity activity;
    private View root;
    private RecyclerView list;
    private ProgressBar progressBar;
    private TextView emptyMessage;
    private UserRepositoriesAdapter adapter;

    public RepoListView(Activity activity) {
        this.activity = activity;
        root = activity.findViewById(R.id.repos_list_root);
        init();
    }

    private void init(){
        list = (RecyclerView)root.findViewById(R.id.repos_list);
        progressBar = (ProgressBar)root.findViewById(R.id.list_progress);
        emptyMessage = (TextView)root.findViewById(R.id.repo_list_empty_message);
    }

    @Override
    public void showList(boolean show) {
        if(show){
            list.setVisibility(View.VISIBLE);
        }
        else {
            list.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgress(boolean show) {
        if(show){
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showEmpty(boolean show) {
        if(show){
            emptyMessage.setVisibility(View.VISIBLE);
        }
        else {
            emptyMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public void setupUI(List<RepoDto> repoDtos) {
        setupAdapter(repoDtos);
    }

    public void setupAdapter(List<RepoDto> repoDtos){
        LinearLayoutManager llm = new LinearLayoutManager(activity);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(llm);
        if(repoDtos.isEmpty()){
            showEmpty(true);
        }
        adapter = new UserRepositoriesAdapter(repoDtos);
        list.setAdapter(adapter);
    }
}
