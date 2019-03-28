package com.example.game_backlog.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.game_backlog.Model.Game;
import com.example.game_backlog.R;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    private List<Game> gameList;


    public GameAdapter(List<Game> gameList){
        this.gameList = gameList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Game game = gameList.get(i);
        viewHolder.title.setText(game.getTitle());
        viewHolder.platform.setText(game.getPlatform());
        viewHolder.status.setText(game.getStatus());
        viewHolder.date.setText(game.getDate());

    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView platform;
        private TextView status;
        private TextView date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            platform = itemView.findViewById(R.id.platformText);
            status = itemView.findViewById(R.id.dateText);
            date = itemView.findViewById(R.id.statusText);


        }
    }
}
