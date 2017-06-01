package me.ferdz.kittengarten.activity;

import android.app.ProgressDialog;
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

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.register_btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = getUser();
                if(user.getUsername().equals("") || user.getPassword().equals("") || user.getEmail().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Invalid login, cannot register", Toast.LENGTH_SHORT).show();
                    return;
                }

                Values.SERVICE.registerUser(user).enqueue(new ImplCallback<Void>(RegisterActivity.this, ProgressDialog.show(RegisterActivity.this, "", "Please wait..", true)) {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        super.onResponse(call, response);
                        if(response.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User registered", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, Utils.readError(response.errorBody()), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    protected User getUser() {
        String username = ((TextView)findViewById(R.id.register_txtUsername)).getText().toString();
        String password = ((TextView)findViewById(R.id.register_txtPassword)).getText().toString();
        String email = ((TextView)findViewById(R.id.register_txtEmail)).getText().toString();
        User user = new User(username);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }
}
