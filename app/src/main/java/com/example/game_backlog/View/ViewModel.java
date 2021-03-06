package com.example.game_backlog.View;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.game_backlog.Database.Repository;
import com.example.game_backlog.Model.Game;

import java.util.List;
/**To hold and manage UI-related data in a life-cycle conscious way.**/
public class ViewModel extends AndroidViewModel {

    private Repository mRepository;
    private LiveData<List<Game>> mGames;

    public ViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application.getApplicationContext());
        mGames = mRepository.getAllGames();
    }
    /**Assign responsibility to acquire and keep the list of games to the viewModel**/
    public LiveData<List<Game>> getmGames(){
        return mGames;
    }

    public void insert(Game game){
        mRepository.insert(game);
    }

    public void update(Game game){
        mRepository.update(game);
    }

    public void delete(Game game){
        mRepository.delete(game);
    }

    public void deleteAll(){
        mRepository.deleteAll(mGames.getValue());
    }
}
