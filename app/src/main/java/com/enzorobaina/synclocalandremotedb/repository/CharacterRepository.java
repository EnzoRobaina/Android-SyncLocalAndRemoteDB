package com.enzorobaina.synclocalandremotedb.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.enzorobaina.synclocalandremotedb.callbacks.CharacterListCallback;
import com.enzorobaina.synclocalandremotedb.callbacks.VoidCallback1;
import com.enzorobaina.synclocalandremotedb.converters.DateConverter;
import com.enzorobaina.synclocalandremotedb.database.CharacterDAO;
import com.enzorobaina.synclocalandremotedb.database.CharacterRoomDatabase;
import com.enzorobaina.synclocalandremotedb.model.Character;
import java.util.Calendar;
import java.util.Date;
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

    private void setCreatedAt(Character character, Date now){
        if (character.getCreatedAt() == null){
            character.setCreatedAt(now);
        }
    }

    private void setModifiedAt(Character character, Date now){
        if (character.getLastModifiedAt() == null){
            character.setLastModifiedAt(now);
        }
    }

    private void setCreatedAndModifiedAt(Character character, Date now){
        setCreatedAt(character, now);
        setModifiedAt(character, now);
    }

    public LiveData<List<Character>> getAllCharactersLiveData(){
        return allCharactersLiveData;
    }

    public LiveData<List<Character>> getAllUnsyncedCharactersLiveData(){
        return allUnsyncedCharactersLiveData;
    }

    public void getAllCharacters(CharacterListCallback callback){
        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            callback.onSuccess(characterDAO.getAllCharacters());
        });
    }

    public void getAllCharactersWithStatus(CharacterListCallback callback, int syncStatus){
        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            callback.onSuccess(characterDAO.getAllCharactersWithSync(syncStatus));
        });
    }

    public void batchUpdateByUUID(List<Character> characters, VoidCallback1 callback){
        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            // String uuid, Date lastModifiedAt, String name, int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma
            for (Character character : characters){
                characterDAO.updateByUUID(
                    character.getUuid(),
                    character.getName(),
                    DateConverter.fromDate(character.getLastModifiedAt()),
                    character.getStrength(),
                    character.getDexterity(),
                    character.getConstitution(),
                    character.getIntelligence(),
                    character.getWisdom(),
                    character.getCharisma()
                );
            }
        callback.done();
        });
    }

    public void insert(Character character, VoidCallback1 callback) {
        Date now = Calendar.getInstance().getTime();
        setCreatedAndModifiedAt(character, now);

        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            long value = characterDAO.insert(character);
            callback.done();
        });
    }

    public void insert(Character character) {
        Date now = Calendar.getInstance().getTime();
        setCreatedAndModifiedAt(character, now);

        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            characterDAO.insert(character);
        });
    }

    public void batchUpdateUUIDs(List<Character> characters, VoidCallback1 callback){
        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            int affectedRows = characterDAO.batchSetUUIDs(characters);
            callback.done();
        });
    }

    public void batchUpdateUUIDs(List<Character> characters){
        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            characterDAO.batchSetUUIDs(characters);
        });
    }

    public void update(Character character, VoidCallback1 callback){
        Date now = Calendar.getInstance().getTime();
        setModifiedAt(character, now);
        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            int value = characterDAO.update(character);
            callback.done();
        });
    }

    public void update(Character character){
        Date now = Calendar.getInstance().getTime();
        setModifiedAt(character, now);
        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            characterDAO.update(character);
        });
    }

    public void updateSync(long id, int syncStatus, VoidCallback1 callback){
        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            int value = characterDAO.updateSync(id, syncStatus);
            callback.done();
        });
    }

    public void updateSync(long id, int syncStatus){
        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            characterDAO.updateSync(id, syncStatus);
        });
    }

    public void insertMultiple(List<Character> characters, VoidCallback1 callback){
        Date now = Calendar.getInstance().getTime();
        for (Character character : characters){
            setCreatedAndModifiedAt(character, now);
            character.setSynced(Character.SYNCED);
        } // Todo: Refator me!

        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Long> values = characterDAO.insertMultiple(characters);
            callback.done();
        });
    }

    public void insertMultiple(List<Character> characters){
        Date now = Calendar.getInstance().getTime();
        for (Character character : characters){
            setCreatedAndModifiedAt(character, now);
            character.setSynced(Character.SYNCED);
        } // Todo: Refator me!

        CharacterRoomDatabase.databaseWriteExecutor.execute(() -> {
            characterDAO.insertMultiple(characters);
        });
    }
}
