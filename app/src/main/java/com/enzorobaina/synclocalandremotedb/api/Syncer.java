package com.enzorobaina.synclocalandremotedb.api;

import android.content.Context;
import android.util.Log;

import com.enzorobaina.synclocalandremotedb.api.service.CharacterService;
import com.enzorobaina.synclocalandremotedb.database.DatabaseHelper;
import com.enzorobaina.synclocalandremotedb.model.Character;

import java.io.IOException;
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

    public void syncOne(Character character){
        Call<Character> call = characterService.createCharacter(character);
        call.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {

            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {

            }
        });
    }

    public void syncOne(Character character, VoidCallback voidCallback){
        Call<Character> call = characterService.createCharacter(character);
        call.enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {

                Log.d("syncOne", response.toString());

                if (response.isSuccessful()){
                    databaseHelper.updateSync(character.getId(), true);
                    voidCallback.onSuccess();
                }
                else {
                    try { Log.d("syncOne",  response.errorBody().string()); }
                    catch (IOException e) { e.printStackTrace(); }

                    voidCallback.onFail();
                }
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                t.printStackTrace();
                voidCallback.onFail();
            }
        });
    }
}
