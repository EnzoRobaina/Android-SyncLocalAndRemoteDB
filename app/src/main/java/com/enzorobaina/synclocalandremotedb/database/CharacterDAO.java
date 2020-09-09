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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Character character);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertMultiple(List<Character> characters);

    @Update
    int update(Character character);

    @Query("UPDATE " + Character.tableName + " SET isSynced = :syncStatus WHERE id = :id")
    int updateSync(long id, int syncStatus);

    @Query("UPDATE " + Character.tableName + " SET isSynced = 1, name = :name, lastModifiedAt = :lastModifiedAt, strength = :strength, dexterity = :dexterity, constitution = :constitution, intelligence = :intelligence, wisdom = :wisdom, charisma = :charisma WHERE uuid = :uuid")
    int updateByUUID(String uuid, String name, String lastModifiedAt, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma);

    @Update
    int batchSetUUIDs(List<Character> characters);

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
