package com.enzorobaina.synclocalandremotedb.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.enzorobaina.synclocalandremotedb.model.Character;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Character.class}, version = 1, exportSchema = false)
public abstract class CharacterRoomDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "character_database";
    public abstract CharacterDAO characterDAO();
    private static volatile CharacterRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static CharacterRoomDatabase getInstance(final Context context){
        if (INSTANCE == null){
            synchronized (CharacterRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context, CharacterRoomDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
