package com.example.game_backlog.View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.game_backlog.Model.Game;
import com.example.game_backlog.R;

import java.util.ArrayList;
import java.util.List;

/**onItemTouchListener allows the application to intercept touch
 * events in progress at the view hierarchy level of the
 * RecyclerView before those touch events are considered for
 * RecyclerView's own scrolling behaviour**/
public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {
    /**Declare variables**/
    private RecyclerView recyclerView;
    private FloatingActionButton fb;
    private final int CREATE_REQUEST = 1234;
    private final int EDIT_REQUEST = 4321;
    public static String EDIT_GAME  = "EDIT_GAME";
    private ViewModel mGameViewModel;
    private List<Game> mGameItems;
    private GameAdapter mAdapter;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        /**instantiate the recycleview with given layout and refer to OnTIemTouchListener**/
        recyclerView = findViewById(R.id.recyclerViewGames);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnItemTouchListener(this);
        fb = findViewById(R.id.fab);
        mGameItems = new ArrayList<>();

        mGameViewModel = ViewModelProviders.of(this).get(ViewModel.class);
        mGameViewModel.getmGames().observe(this, gameItems -> {
            mGameItems = gameItems;
            mAdapter = new GameAdapter(mGameItems);
            recyclerView.setAdapter(mAdapter);
            updateUI();
        });
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        /**press floating action button to navigate to the AddGameActivity class and create a request**/
        fb.setOnClickListener(c -> {
            Intent intent = new Intent(getBaseContext(), AddGameActivity.class);
            startActivityForResult(intent, CREATE_REQUEST);
        });

        /**Swipe left or right to delete a game from the recycleview**/
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        int position = (viewHolder.getAdapterPosition());
                        final Game game = mGameItems.get(position);
                        mGameViewModel.delete(game);
                        Snackbar.make(recyclerView, getString(R.string.delete), Snackbar.LENGTH_LONG).show();
                        mAdapter.notifyItemRemoved(position);
                        updateUI();
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**Recieve the Intent object from AddGameActivity in the onActivityResult method**/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            /**when creaing a game, insert the item in the viewmodel**/
            if (requestCode == CREATE_REQUEST) {
                /**get the data from the object in AddGameActivity**/
                Game item = data.getParcelableExtra(AddGameActivity.GAME);
                mGameViewModel.insert(item);
                updateUI();
            /**when editing a game, update the item in the viewmodel**/
            } else if (requestCode == EDIT_REQUEST) {
                Game item = data.getParcelableExtra(AddGameActivity.GAME);
                mGameViewModel.update(item);
                updateUI();
            }
        }
    }
    /**Notifies that the underlying data has been changed and any
     * View reflecting the data set should refresh itself**/
    private void updateUI() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    /**When the trashcan is pressed, all the games will be deleted
     * from the ViewModel**/
    @Override
    public boolean onOptionsItemSelected(MenuItem trashcan) {
        /**get the id of the trashcan**/
        if (trashcan.getItemId() == R.id.delete_game) {
            mGameViewModel.deleteAll();
            Snackbar.make(recyclerView, getString(R.string.delete_everything), Snackbar.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(trashcan);
    }

    @Override
    /**When a child of the recycleview is not null and can be pressed.
     * The AddGameActivity classs will be opened where the child can be adjusted **/
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        int mAdapterPosition = recyclerView.getChildAdapterPosition(child);
        if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
            Intent intent = new Intent(getBaseContext(), AddGameActivity.class);
            Game item = mGameItems.get(mAdapterPosition);
            intent.putExtra(EDIT_GAME,item);
            startActivityForResult(intent, EDIT_REQUEST);
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
