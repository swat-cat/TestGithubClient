package com.mermakov.testgithubclient.repos.repo_list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mermakov.testgithubclient.R;
import com.mermakov.testgithubclient.services.rest.dto.RepoDto;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by max_ermakov on 8/22/16.
 */
public class UserRepositoriesAdapter extends RecyclerView.Adapter<UserRepositoriesAdapter.ViewHolder> {
    private static final String TAG = UserRepositoriesAdapter.class.getSimpleName();

    private List<RepoDto> repositories;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView name;
        private TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView)itemView.findViewById(R.id.avatar);
            name = (TextView)itemView.findViewById(R.id.repo_name);
            description = (TextView)itemView.findViewById(R.id.repo_description);
        }
    }

    public UserRepositoriesAdapter(List<RepoDto> repositories) {
        this.repositories = repositories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_list_ittem,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        final RepoDto repoDto = repositories.get(position);
        if(repoDto!=null){
            Picasso.with(holder.avatar.getContext())
                    .load(repoDto.getOwner().getAvatarUrl())
                    .placeholder(R.mipmap.github)
                    .into(holder.avatar);
            holder.name.setText(repoDto.getName());
            holder.description.setText(repoDto.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }
}
