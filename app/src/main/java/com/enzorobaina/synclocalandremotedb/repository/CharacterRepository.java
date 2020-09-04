package com.enzorobaina.synclocalandremotedb.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.enzorobaina.synclocalandremotedb.api.IntCallback;
import com.enzorobaina.synclocalandremotedb.api.ListCallback;
import com.enzorobaina.synclocalandremotedb.api.LongCallback;
import com.enzorobaina.synclocalandremotedb.database.CharacterDAO;
import com.enzorobaina.synclocalandremotedb.database.CharacterRoomDatabase;
import com.enzorobaina.synclocalandremotedb.model.Character;

import java.util.List;

public class CharacterRepository {
    private CharacterDAO characterDAO;
    private LiveData<List<Character>> allCharactersLiveData;
    private LiveData<List<Character>> allUnsyncedCharactersLiveData;
    private static CharacterRepository instance;

    public static synchronized CharacterRepository getInstance(Application application){
        if (instance == null){
            instance = new CharacterRepository(application);
        }
        return instance;
    }

    private CharacterRepository(Application application){
        CharacterRoomDatabase database = CharacterRoomDatabase.getInstance(application);
        characterDAO = database.characterDAO();
        allCharactersLiveData = characterDAO.getAllCharactersLiveData();
        allUnsyncedCharactersLiveData = characterDAO.getAllCharactersLiveDataWithSync(Character.UNSYNCED);
    }

    public LiveData<List<Character>> getAllCharactersLiveData(){
        return allCharactersLiveData;
    }

    public LiveData<List<Character>> getAllUnsyncedCharactersLiveData(){
        return allUnsyncedCharactersLiveData;
    }

    public List<Character> getAllCharacters(){
        return characterDAO.getAllCharacters();
    }

    public List<Character> getAllUnsyncedCharacters(){
        return characterDAO.getAllCharactersWithSync(Character.UNSYNCED);
    }

    public void insert(Character character, LongCallback callback) {
        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            long value = characterDAO.insert(character);
            if (value > 0){
                callback.onSuccess(value);
            }
            else {
                callback.onFail();
            }
        });
    }

    public void update(Character character, IntCallback callback){
        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            int value = characterDAO.update(character);
            if (value > 0){
                callback.onSuccess(value);
            }
            else {
                callback.onFail();
            }
        });
    }

    public void updateSync(long id, int syncStatus, IntCallback callback){
        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            int value = characterDAO.updateSync(id, syncStatus);
            if (value > 0){
                callback.onSuccess(value);
            }
            else {
                callback.onFail();
            }
        });
    }

    public void insertMultiple(List<Character> characters, ListCallback callback){
        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Long> values = characterDAO.insertMultiple(characters);
            if (values != null && values.size() > 0){
                callback.onSuccess(values);
            }
            else {
                callback.onFail();
            }
        });
    }
}
