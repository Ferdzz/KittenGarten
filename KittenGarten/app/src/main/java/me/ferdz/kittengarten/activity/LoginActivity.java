package me.ferdz.kittengarten.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import me.ferdz.kittengarten.R;
import me.ferdz.kittengarten.data.Values;
import me.ferdz.kittengarten.model.MinUser;
import me.ferdz.kittengarten.model.User;
import me.ferdz.kittengarten.util.ImplCallback;
import me.ferdz.kittengarten.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.login_btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Values.SERVICE.logUser(getUser()).enqueue(new ImplCallback<MinUser>(LoginActivity.this, ProgressDialog.show(LoginActivity.this, "", "Please wait..", true)) {
                    @Override
                    public void onResponse(Call<MinUser> call, Response<MinUser> response) {
                        super.onResponse(call, response);
                        if(response.isSuccessful()) {
                            Utils.loggedUser = response.body();
                            startActivity(new Intent(LoginActivity.this, KittenListActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid login", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        findViewById(R.id.login_btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    protected User getUser() {
        String username = ((TextView)findViewById(R.id.login_username)).getText().toString();
        String password = ((TextView)findViewById(R.id.login_password)).getText().toString();
        User user = new User(username);
        user.setPassword(password);
        return user;
    }

    /**
     * Cancels going back to the other activities
     */
    @Override
    public void onBackPressed() { }
}
