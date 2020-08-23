package com.enzorobaina.synclocalandremotedb.api;

import com.enzorobaina.synclocalandremotedb.api.service.CharacterService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private static RetrofitConfig instance = null;
    private Retrofit retrofit;

    public static synchronized RetrofitConfig getInstance(){
        if (instance == null){
            instance = new RetrofitConfig();
        }
        return instance;
    }

    private RetrofitConfig(){
        this.retrofit = new Retrofit.Builder()
            .baseUrl("https://d-and-d-django-api.herokuapp.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    public CharacterService getCharacterService() {
        return this.retrofit.create(CharacterService.class);
    }
}
