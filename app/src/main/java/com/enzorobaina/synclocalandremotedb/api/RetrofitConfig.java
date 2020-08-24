package com.enzorobaina.synclocalandremotedb.api;

import com.enzorobaina.synclocalandremotedb.api.service.CharacterService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private static RetrofitConfig instance = null;

    public static synchronized RetrofitConfig getInstance(){
        if (instance == null){
            instance = new RetrofitConfig();
        }
        return instance;
    }

    public CharacterService getCharacterService() {
        return new Retrofit.Builder()
            .baseUrl("https://d-and-d-django-api.herokuapp.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CharacterService.class);
    }

    public CharacterService getCharacterRxService(){
        return new Retrofit.Builder()
            .baseUrl("https://d-and-d-django-api.herokuapp.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(CharacterService.class);
    }
}
