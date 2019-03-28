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
import android.support.v7.widget.Toolbar;
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

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener {

    private RecyclerView recyclerView;
    private FloatingActionButton fb;
    private final int CREATE_REQUEST = 1234;
    private final int EDIT_REQUEST = 4321;
    public static String EDIT_ITEM  = "EDIT_GAME_ITEM";
    private ViewModel mGameViewModel;
    private List<Game> mGameItems;
    private GameAdapter mAdapter;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        initActions();
    }

    private void initComponents() {
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

    }

    private void initActions() {
        //create a game
        fb.setOnClickListener(c -> {
            Intent intent = new Intent(getBaseContext(), AddGameActivity.class);
            startActivityForResult(intent, CREATE_REQUEST);
        });

        //swipe to delete a game
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @SuppressLint("ResourceType")
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        int position = (viewHolder.getAdapterPosition());
                        final Game game = mGameItems.get(position);
                        mGameViewModel.delete(game);
                        Snackbar.make(recyclerView, getString(R.string.delete) + " " + game.getTitle(), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.undo), v -> mGameViewModel.insert(game)).show();
                        mAdapter.notifyItemRemoved(position);
                        updateUI();
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CREATE_REQUEST) {
                Game item = data.getParcelableExtra(AddGameActivity.GAME);
                mGameViewModel.insert(item);
                updateUI();
                //on edit request update object instead of insert
            } else if (requestCode == EDIT_REQUEST) {
                Game item = data.getParcelableExtra(AddGameActivity.GAME);
                mGameViewModel.update(item);
                updateUI();
            }
        }
    }

    private void updateUI() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_game) {
            //no need to send a list if you want to delete all
            final List<Game> tempList = mGameItems;
            mGameViewModel.deleteAll();
            Snackbar.make(recyclerView, getString(R.string.delete_everything), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.undo), v -> {
                        for (Game gameItem : tempList) {
                            mGameViewModel.insert(gameItem);
                        }
                    }).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        int mAdapterPosition = recyclerView.getChildAdapterPosition(child);
        if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
            Intent intent = new Intent(getBaseContext(), AddGameActivity.class);
            Game item = mGameItems.get(mAdapterPosition);
            intent.putExtra(EDIT_ITEM,item);
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
