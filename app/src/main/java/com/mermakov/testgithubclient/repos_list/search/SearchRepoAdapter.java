package com.mermakov.testgithubclient.repos_list.search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mermakov.testgithubclient.R;
import com.mermakov.testgithubclient.data.rest.dto.RepoDto;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by max_ermakov on 8/22/16.
 */
public class SearchRepoAdapter extends RecyclerView.Adapter<SearchRepoAdapter.ViewHolder> {
    private static final String TAG = SearchRepoAdapter.class.getSimpleName();

    private List<RepoDto> repositories;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.search_text);
        }
    }

    public SearchRepoAdapter(List<RepoDto> repositories) {
        this.repositories = repositories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        final RepoDto repoDto = repositories.get(position);
        if(repoDto!=null){
            holder.name.setText(repoDto.getName());
        }
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }
}
