package com.example.game_backlog.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.game_backlog.Model.Game;

@Database(entities = {Game.class}, version = 1, exportSchema = false)
public abstract class GameRoomDatabase extends RoomDatabase {

    private final static String NAME_DATABASE = "gameDatabase";
    public abstract DAO dao();
    private static volatile GameRoomDatabase INSTANCE;

    public static GameRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GameRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GameRoomDatabase.class,NAME_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
