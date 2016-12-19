package com.piteravto.rockabilla.checkingvehicles.api;

import com.piteravto.rockabilla.checkingvehicles.structure.MenuItem;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by MishustinAI on 19.12.2016.
 */

public interface ServerApiInterface {

    @GET("{directory}/{command}")
    Call<List<MenuItem>> getMenuesItems(@Path("directory") String directory, @Path("command") String command, @Query("vehicleid") String vehicleId);

    @POST("{directory}/{command}")
    Call<ResponseBody> getMenuesItems2(@Path("directory") String directory, @Path("command") String command, @Body RequestBody body);
}