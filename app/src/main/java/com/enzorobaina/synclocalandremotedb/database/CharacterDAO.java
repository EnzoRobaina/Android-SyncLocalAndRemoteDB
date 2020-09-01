package com.enzorobaina.synclocalandremotedb.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.enzorobaina.synclocalandremotedb.model.Character;

import java.util.List;

@Dao
public interface CharacterDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Character character);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertMultiple(List<Character> characters);

    @Update
    int update(Character character);

    @Query("SELECT * FROM " + Character.tableName + " ORDER BY id ASC")
    public List<Character> getAllCharacters();

    @Query("SELECT * FROM " + Character.tableName + " WHERE isSynced = :syncStatus ORDER BY id ASC")
    public List<Character> getAllCharactersWithSync(int syncStatus);

    @Query("SELECT * FROM " + Character.tableName + " ORDER BY id ASC")
    public LiveData<List<Character>> getAllCharactersLiveData();

    @Query("SELECT * FROM " + Character.tableName + " WHERE isSynced = :syncStatus ORDER BY id ASC")
    public LiveData<List<Character>> getAllCharactersLiveDataWithSync(int syncStatus);

    @Query("SELECT * FROM " + Character.tableName + " WHERE id = :id")
    public Character get(int id);
}
