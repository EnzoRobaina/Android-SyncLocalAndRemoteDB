package com.enzorobaina.synclocalandremotedb.api;

import android.content.Context;
import com.enzorobaina.synclocalandremotedb.api.service.CharacterService;
import com.enzorobaina.synclocalandremotedb.database.DatabaseHelper;
import com.enzorobaina.synclocalandremotedb.model.Character;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Syncer {
    private static Syncer instance = null;
    private DatabaseHelper databaseHelper = null;
    private CharacterService characterService = null;

    public static synchronized Syncer getInstance(Context context){
        if (instance == null){
            instance = new Syncer(context);
        }
        return instance;
    }

    private Syncer(Context context){
        this.databaseHelper = DatabaseHelper.getInstance(context);
        this.characterService = RetrofitConfig.getInstance().getCharacterService();
    }

    public void syncRemoteWithLocal(){

    }

    public void runFirst(VoidCallback voidCallback){
        if (databaseHelper.isCharacterDatabaseEmpty()){
            this.syncLocalWithRemote(voidCallback);
        }
        else {
            voidCallback.onSuccess();
        }
    }

    public void syncLocalWithRemote(VoidCallback voidCallback){
        Call<List<Character>> call = characterService.getCharacters();
        call.enqueue(new Callback<List<Character>>() {
            @Override
            public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {
                List<Character> characterList = response.body();
                for (Character character : characterList){
                    character.setSynced(true); // TODO: Deserialize and set synced to true automatically
                    databaseHelper.createCharacter(character);
                }
                voidCallback.onSuccess();
            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {
                t.printStackTrace();
                voidCallback.onFail();
            }
        });
    }

    void syncOne(Character character){

    }
}
