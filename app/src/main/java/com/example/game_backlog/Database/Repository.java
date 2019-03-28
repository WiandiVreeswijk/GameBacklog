package com.example.game_backlog.Database;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.game_backlog.Model.Game;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**interacting with the Room database on behalf of the ViewModel
 * Needs to provide methods that use the DAO to insert, delete and query product records.
 * Exception of the getAllGames() DAO method
 * **/
public class Repository {

    private GameRoomDatabase db;
    private DAO dao;
    private LiveData<List<Game>> mGames;
    /**creating pool with exactly 1 thread**/
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public Repository(Context context){
        this.db = GameRoomDatabase.getDatabase(context);
        this.dao = db.dao();
        this.mGames = dao.getAllGames();
    }
    public LiveData<List<Game>> getAllGames() {
        return mGames;
    }
    /**dont have to communicate with the UI thread
     * Dont execute queries on main thread
     * **/
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
