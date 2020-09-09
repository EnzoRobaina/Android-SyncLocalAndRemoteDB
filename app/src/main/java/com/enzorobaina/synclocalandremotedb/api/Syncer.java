package com.enzorobaina.synclocalandremotedb.api;

import android.app.Application;
import android.util.Log;
import com.enzorobaina.synclocalandremotedb.api.service.CharacterService;
import com.enzorobaina.synclocalandremotedb.callbacks.VoidCallback1;
import com.enzorobaina.synclocalandremotedb.model.Character;
import com.enzorobaina.synclocalandremotedb.model.PerformSyncResponse;
import com.enzorobaina.synclocalandremotedb.repository.CharacterRepository;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Syncer {
    private static Syncer instance = null;
    private CharacterService characterService;
    private CharacterRepository repository;

    public static synchronized Syncer getInstance(Application application){
        if (instance == null){
            instance = new Syncer(application);
        }
        return instance;
    }

    private Syncer(Application application){
        this.characterService = RetrofitConfig.getInstance().getCharacterService();
        this.repository = CharacterRepository.getInstance(application);
    }

    public void syncLocalFromRemote(VoidCallback1 voidCallback){
        Log.d("syncLocalFromRemote", "starting syncLocalFromRemote");
        repository.getAllCharactersWithStatus(characters -> {
            Call<PerformSyncResponse> call = characterService.performSync(characters);

            call.enqueue(new Callback<PerformSyncResponse>() {
                @Override
                public void onResponse(Call<PerformSyncResponse> call, Response<PerformSyncResponse> response) {
                    if (response.body() == null){ return; }

                    Log.d("performSyncRes", "\nto insert >>>>>\n" + response.body().toInsert.toString());
                    Log.d("performSyncRes", "\nto update >>>>>\n" + response.body().toUpdate.toString());

                    repository.insertMultiple(response.body().toInsert, () -> {
                        repository.batchUpdateByUUID(response.body().toUpdate, () -> {
                            Log.d("batchByUuid", "Done updating by uuids");
                            voidCallback.done(); // Entire process has been successful.
                        });
                    });
                }
                @Override
                public void onFailure(Call<PerformSyncResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }, Character.SYNCED);
    }

    public void syncRemoteFromLocal(VoidCallback1 voidCallback){
        Log.d("syncRemoteFromLocal", "starting syncRemoteFromLocal");
        repository.getAllCharactersWithStatus(unsyncedLocalCharacters -> {
            Call<List<Character>> call = characterService.createMultipleCharactersAndGetResult(unsyncedLocalCharacters);
            call.enqueue(new Callback<List<Character>>() {
                 @Override
                 public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {
                       if (response.body() == null){ return; }
                             List<Character> returnedCharacters = response.body();

                             if ((returnedCharacters.size() > 0 && unsyncedLocalCharacters.size() > 0)){

                                 for (int i = 0; i < unsyncedLocalCharacters.size(); i++){
                                     Character local = unsyncedLocalCharacters.get(i);
                                     Character returned = returnedCharacters.get(i);

                                     local.setUuid(returned.getUuid());
                                     local.setSynced(Character.SYNCED);
                                 }

                                 repository.batchUpdateUUIDs(unsyncedLocalCharacters, voidCallback::done);
                             }
                             else {
                                 Log.d("syncRemoteFromLocal", "nothing to send to remote!");
                                 voidCallback.done();
                             }
                 }
                 @Override public void onFailure(Call<List<Character>> call, Throwable t) { t.printStackTrace(); }
                }
            );
        }, Character.UNSYNCED);
    }
}
