package com.example.game_backlog.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.game_backlog.Model.Game;

import java.util.List;
/**Separate different components of database architecture.
 define database interactions**/
@Dao
/**interface or abstract**/
public interface DAO {

    /**Room generates an implementation that inserts all parameters into the database**/
    @Insert
    void insert(Game game);

    /**removes a set of entities, given as parameters. Uses primary keys to find the entities to delete **/
    @Delete
    void delete(Game game);

    @Delete
    void delete(List<Game> games);

    /**modifies a set of entities, given as parameters. Uses a query that matches against the primarey key of each entity**/
    @Update
    void update(Game game);

    /**read/write operations on database.**/
    @Query("SELECT * from gameTable")
    LiveData<List<Game>> getAllGames();

}
