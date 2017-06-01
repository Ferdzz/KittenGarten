package me.ferdz.kittengarten.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frosquivel.magicalcamera.MagicalCamera;

import java.io.ByteArrayOutputStream;

import me.ferdz.kittengarten.R;
import me.ferdz.kittengarten.data.Values;
import me.ferdz.kittengarten.model.User;
import me.ferdz.kittengarten.util.ImplCallback;
import me.ferdz.kittengarten.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.ferdz.kittengarten.util.Utils.*;

public class KittenDetailActivity extends LoggedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitten_detail);

        initDrawer(R.id.detail_drawer, R.id.detail_navigation_view);

        getTextView(R.id.detail_txtBirthDate).setText(Values.DATE_FORMAT.format(activeKitten.getBirthDate()));
        getTextView(R.id.detail_txtName).setText(activeKitten.getName());
        getTextView(R.id.detail_txtWeight).setText(activeKitten.getWeight() + " lbs");
        getTextView(R.id.detail_txtRenter).setText(activeKitten.getRenter() == null ? "N/A" : activeKitten.getRenter().getUsername());

        if(activeKitten.getImage() == null)
            ((ImageView)findViewById(R.id.detail_image)).setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.garfielf));
        else
            ((ImageView)findViewById(R.id.detail_image)).setImageBitmap(activeKitten.getImage());

        ImageView imageView = (ImageView)findViewById(R.id.detail_image);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Values.cam = new MagicalCamera(KittenDetailActivity.this, 2000);
                Values.cam.takePhoto();
                return true;
            }
        });

        findViewById(R.id.detail_btnRent).setVisibility(View.VISIBLE);
        if(activeKitten.getRenterId() == -1) { // kitten rentable
            ((Button)findViewById(R.id.detail_btnRent)).setText("Rent");
            findViewById(R.id.detail_btnRent).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Values.SERVICE.rentKitten(activeKitten.getId()).enqueue(new ImplCallback<Void>(KittenDetailActivity.this, ProgressDialog.show(KittenDetailActivity.this, "", "Please wait..", true)) {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            super.onResponse(call, response);
                            if(response.isSuccessful()) {
                                Toast.makeText(KittenDetailActivity.this, "Kitten was rented, congratulations!", Toast.LENGTH_SHORT).show();
                                activeKitten.setRenterId(loggedUser.getId());
//                                activeKitten.setRenter(loggedUser.);
                                finish();
                            } else {
                                Toast.makeText(KittenDetailActivity.this, Utils.readError(response.errorBody()), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } else if (activeKitten.getRenterId() == loggedUser.getId())  { // kitten is yours
            ((Button)findViewById(R.id.detail_btnRent)).setText("Return");
            findViewById(R.id.detail_btnRent).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Values.SERVICE.returnKitten(activeKitten.getId()).enqueue(new ImplCallback<Void>(KittenDetailActivity.this, ProgressDialog.show(KittenDetailActivity.this, "", "Please wait..", true)) {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            super.onResponse(call, response);
                            if(response.isSuccessful()) {
                                Toast.makeText(KittenDetailActivity.this, "Kitten was returned!", Toast.LENGTH_SHORT).show();
                                activeKitten.setRenterId(-1);
                                finish();
//                                reload();
                            } else {
                                Toast.makeText(KittenDetailActivity.this, Utils.readError(response.errorBody()), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } else { // kitten is not yours and is rented
            findViewById(R.id.detail_btnRent).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Values.cam.resultPhoto(requestCode, resultCode, data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Values.cam.getMyPhoto().compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        activeKitten.setEncodedImage(outputStream.toByteArray()); // TODO: UPDATE PIC ON SERVER

        ((ImageView)findViewById(R.id.detail_image)).setImageBitmap(activeKitten.getImage());
    }

    private TextView getTextView(int id) {
        return (TextView)findViewById(id);
    }

//    private void reload() {
//        overridePendingTransition(0, 0);
//        finish();
//        overridePendingTransition(0, 0);
//        startActivity(getIntent());
//    }
}
