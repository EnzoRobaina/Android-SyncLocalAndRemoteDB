package com.enzorobaina.synclocalandremotedb.api.service;

import com.enzorobaina.synclocalandremotedb.model.Character;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CharacterService {
    @GET("characters/{id}/")
    Call<Character> getCharacter(@Path("id") int id);

    @GET("characters/")
    Call<List<Character>> getCharacters();

    @POST("characters/")
    Call<Character> createCharacter(@Body Character character);

    @POST("characters/")
    Call<Response<ResponseBody>> createMultipleCharacters(@Body List<Character> characters);

    @POST("characters/")
    Observable<Response<ResponseBody>> createCharacterWithObservable(@Body Character character);
}
