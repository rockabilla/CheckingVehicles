package com.piteravto.rockabilla.checkingvehicles.api;

import android.app.Application;

import com.piteravto.rockabilla.checkingvehicles.R;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MishustinAI on 19.12.2016.
 */

public class ServerApi extends Application {

    private static ServerApiInterface serverApiInterface;
    private Retrofit retrofit;

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.server_name)) //Базовая часть адрес
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //retrofit.client(httpClient.build()).build();

        serverApiInterface = retrofit.create(ServerApiInterface.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public static ServerApiInterface getApi() {
        return serverApiInterface;
    }

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl("http://tabus.piteravto.ru/")
                        .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
