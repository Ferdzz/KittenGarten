package me.ferdz.kittengarten.service.client;

import java.util.ArrayList;
import java.util.List;

import me.ferdz.kittengarten.model.Kitten;
import me.ferdz.kittengarten.model.MinKitten;
import me.ferdz.kittengarten.model.MinUser;
import me.ferdz.kittengarten.model.User;
import me.ferdz.kittengarten.util.RetrofitUtil;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by 1452284 on 2016-09-15.
 */
public interface IService {

    //region Kittens
    @POST("kitten/add")
    Call<Kitten> addKitten(@Body Kitten kitten);

    @POST("kitten/remove/{kitten}")
    Call<Void> removeKitten(@Path("kitten") int kittenId);

    @GET("kitten/get/{kitten}")
    Call<Kitten> getKitten(@Path("kitten") int kittenId);

    @GET("kitten/getall")
    Call<List<MinKitten>> getKittens();

    @POST("kitten/rent/{kitten}/{user}")
    Call<Void> rentKitten(@Path("kitten") int kittenId);

    @POST("kitten/return/{kitten}/{user}")
    Call<Void> returnKitten(@Path("kitten") int kittenId);
    //endregion

    //region User
    @POST("user/add")
    Call<Void> registerUser(@Body User user);

    @POST("user/log")
    Call<MinUser> logUser(@Body User user);

    @POST("user/logout")
    Call<Void> logoutUser();
    //endregion
}
