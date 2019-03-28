package com.example.game_backlog.Database;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.game_backlog.Model.Game;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Repository {

    private GameRoomDatabase db;
    private DAO dao;
    private LiveData<List<Game>> mGames;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public Repository(Context context){
        this.db = GameRoomDatabase.getDatabase(context);
        this.dao = db.dao();
        this.mGames = dao.getAllGames();
    }
    public LiveData<List<Game>> getAllGames() {
        return mGames;
    }

    public void insert(final Game game){
        mExecutor.execute(() -> dao.insert(game));
    }

    public void update(final Game game){
        mExecutor.execute(() -> dao.update(game));
    }

    public void delete(final Game game){
        mExecutor.execute(()->dao.delete(game));
    }

    public void deleteAll(List<Game> game){
        mExecutor.execute(()->dao.delete(game));
    }

}
