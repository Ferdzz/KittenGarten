package me.ferdz.kittengarten.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DialogTitle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.Exchanger;

import me.ferdz.kittengarten.R;
import me.ferdz.kittengarten.data.Values;
import me.ferdz.kittengarten.model.Kitten;
import me.ferdz.kittengarten.util.ImplCallback;
import me.ferdz.kittengarten.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KittenAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitten_add);

        findViewById(R.id.add_btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = String.valueOf(((TextView)findViewById(R.id.add_txtName)).getText());
                    int weight = Integer.valueOf(String.valueOf(((TextView)findViewById(R.id.add_txtWeight)).getText()));
                    DatePicker dp = ((DatePicker)findViewById(R.id.add_date));
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(dp.getYear(), dp.getMonth(), dp.getDayOfMonth());

                    Kitten kitten = new Kitten(name);
                    kitten.setWeight(weight);
                    kitten.setBirthDate(calendar.getTime());

                    Values.SERVICE.addKitten(kitten).enqueue(new ImplCallback<Kitten>(KittenAddActivity.this, ProgressDialog.show(KittenAddActivity.this, "", "Please wait..", true)) {
                        @Override
                        public void onResponse(Call<Kitten> call, Response<Kitten> response) {
                            super.onResponse(call, response);
                            if(response.isSuccessful()) {
                                Toast.makeText(KittenAddActivity.this, "Kitten " + response.body().getName() + " was added", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(KittenAddActivity.this, Utils.readError(response.errorBody()), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(KittenAddActivity.this, "Data invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
