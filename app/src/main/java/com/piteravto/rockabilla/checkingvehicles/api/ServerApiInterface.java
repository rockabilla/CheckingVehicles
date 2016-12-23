package com.piteravto.rockabilla.checkingvehicles.api;

import com.piteravto.rockabilla.checkingvehicles.structure.MenuItem;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by MishustinAI on 19.12.2016.
 * Описываем команды разговора с серваком
 */

public interface ServerApiInterface {

    @GET("{directory}/{command}")
    Call<List<MenuItem>> getMenuesItems(@Path("directory") String directory, @Path("command") String command, @Query("vehicleid") String vehicleId);

    @Multipart
    @POST("{directory}/{command}")
    Call<ResponseBody> uploadImage(@Path("directory") String directory, @Path("command") String command,
                              @Part("description") RequestBody description,
                              @Part MultipartBody.Part file);

    @POST("{directory}/{command}")
    Call<ResponseBody> uploadChosen(@Path("directory") String directory, @Path("command") String command,
                                     @Body ArrayList<MenuItem> body);
}