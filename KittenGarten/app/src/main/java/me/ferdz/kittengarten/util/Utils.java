package me.ferdz.kittengarten.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;

import me.ferdz.kittengarten.activity.LoginActivity;
import me.ferdz.kittengarten.data.Values;
import me.ferdz.kittengarten.model.Kitten;
import me.ferdz.kittengarten.model.MinUser;
import me.ferdz.kittengarten.model.User;
import me.ferdz.kittengarten.service.client.IService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 1452284 on 2016-08-26.
 */
public class Utils {
    public static Kitten activeKitten;
    public static MinUser loggedUser;

    public static void logout(final Context context, ProgressDialog progressDialog) {
        Values.SERVICE.logoutUser().enqueue(new ImplCallback<Void>(context, progressDialog) {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()) {
                    loggedUser = null;
                    context.startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
    }

    public static String readError(ResponseBody errorBody) {
        try {
            return errorBody.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
