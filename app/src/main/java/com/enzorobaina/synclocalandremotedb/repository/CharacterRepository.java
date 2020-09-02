package com.enzorobaina.synclocalandremotedb.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.enzorobaina.synclocalandremotedb.api.IntCallback;
import com.enzorobaina.synclocalandremotedb.api.LongCallback;
import com.enzorobaina.synclocalandremotedb.database.CharacterDAO;
import com.enzorobaina.synclocalandremotedb.database.CharacterRoomDatabase;
import com.enzorobaina.synclocalandremotedb.model.Character;

import java.util.List;

public class CharacterRepository {
    private CharacterDAO characterDAO;
    private LiveData<List<Character>> allCharacters;
    private LiveData<List<Character>> allUnsyncedCharacters;
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
        allCharacters = characterDAO.getAllCharactersLiveData();
        allUnsyncedCharacters = characterDAO.getAllCharactersLiveDataWithSync(Character.UNSYNCED);
    }

    public LiveData<List<Character>> getAllCharacters(){
        return allCharacters;
    }

    public LiveData<List<Character>> getAllUnsyncedCharacters(){
        return allUnsyncedCharacters;
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
}
