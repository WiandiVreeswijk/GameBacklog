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

/**The GameAdapter populates the data in the RecyclerView.
 * convert an object at a position into a list row item to be inserted.
 * requires existence of viewholder object which desribes and provides access to
 * all the views within each item row.
 *
 * **/

/**specify the custom ViewHolder which gives access to views**/
public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {

    /**store a member variable for the contacts**/
    private List<Game> gameList;

    /**pass in the gameList array into the constructor**/
    public GameAdapter(List<Game> gameList){
        this.gameList = gameList;
    }
    /**inflating a layout from XML and returning the holder**/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        /**inflate custom layout**/
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid,viewGroup,false);
        /**return new holder instance**/
        return new ViewHolder(v);
    }

    /**Involves populating data into the item through holder**/
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Game game = gameList.get(i);
        viewHolder.title.setText(game.getTitle());
        viewHolder.platform.setText(game.getPlatform());
        viewHolder.status.setText(game.getStatus());
        viewHolder.date.setText(game.getDate());

    }
    /**return the total count of items in the gameList**/
    @Override
    public int getItemCount() {
        return gameList.size();
    }
    /**provide direct reference to each of the views
     * within a data item.
     * Used to cache the views within the item layout for fast access**/
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView platform;
        private TextView status;
        private TextView date;

        /**constructor that accepts the item row and does
         * the view lookups to find each subview**/
        public ViewHolder(@NonNull View itemView) {
            /**stores the itemView in a public final member variable
             * that can be used to access the context from any ViewHolder
             * instance**/
            super(itemView);
            title = itemView.findViewById(R.id.titleText);
            platform = itemView.findViewById(R.id.platformText);
            status = itemView.findViewById(R.id.dateText);
            date = itemView.findViewById(R.id.statusText);


        }
    }
}
