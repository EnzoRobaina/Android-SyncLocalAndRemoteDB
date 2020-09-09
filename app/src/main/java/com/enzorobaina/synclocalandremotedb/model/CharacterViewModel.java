package com.enzorobaina.synclocalandremotedb.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.enzorobaina.synclocalandremotedb.callbacks.IntCallback;
import com.enzorobaina.synclocalandremotedb.callbacks.LongCallback;
import com.enzorobaina.synclocalandremotedb.callbacks.VoidCallback1;
import com.enzorobaina.synclocalandremotedb.repository.CharacterRepository;

import java.util.List;

public class CharacterViewModel extends AndroidViewModel {
    private CharacterRepository characterRepository;
    private LiveData<List<Character>> allCharacters;
    private LiveData<List<Character>> allUnsyncedCharacters;

    public CharacterViewModel(@NonNull Application application) {
        super(application);
        characterRepository = CharacterRepository.getInstance(application);
        allCharacters = characterRepository.getAllCharactersLiveData();
        allUnsyncedCharacters = characterRepository.getAllUnsyncedCharactersLiveData();
    }

    public LiveData<List<Character>> getAllCharacters() {
        return allCharacters;
    }

    public LiveData<List<Character>> getAllUnsyncedCharacters() {
        return allUnsyncedCharacters;
    }

    public void insert(Character character, VoidCallback1 callback){
        characterRepository.insert(character, callback);
    }

    public void update(Character character, VoidCallback1 callback){
        characterRepository.update(character, callback);
    }
}
