package me.ferdz.kittengarten.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import java.util.Calendar;

import me.ferdz.kittengarten.activity.KittenDetailActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by 1452284 on 2016-10-20.
 */
public abstract class ImplCallback<T> implements Callback<T> {

    private Context context;
    private ProgressDialog progressDialog;

    public ImplCallback(Context context, ProgressDialog progressDialog) {
        this.context = context;
        this.progressDialog = progressDialog;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        progressDialog.dismiss();
        if(response.code() == 505) { // if respoonse is cookie ran out
            Utils.logout(context, progressDialog);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        progressDialog.dismiss();
        t.printStackTrace();
        Toast.makeText(context, "Could not reach server", Toast.LENGTH_SHORT).show();
    }
}
