package com.enzorobaina.synclocalandremotedb.api.service;

import com.enzorobaina.synclocalandremotedb.model.Character;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CharacterService {
    @GET("characters/{id}/")
    Call<Character> getCharacter(@Path("id") int id);

    @GET("characters/")
    Call<List<Character>> getCharacters();
}
