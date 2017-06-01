package me.ferdz.kittengarten.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.ferdz.kittengarten.R;
import me.ferdz.kittengarten.adapter.KittenAdapter;
import me.ferdz.kittengarten.data.Values;
import me.ferdz.kittengarten.model.Kitten;
import me.ferdz.kittengarten.model.MinKitten;
import me.ferdz.kittengarten.util.ImplCallback;
import me.ferdz.kittengarten.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KittenListActivity extends LoggedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitten_list);

        initDrawer(R.id.list_drawer, R.id.list_navigation_view);

        final ListView listView = (ListView)findViewById(R.id.list_list);
        listView.setAdapter(new KittenAdapter(KittenListActivity.this, R.layout.list_item, R.id.list_item_name, new ArrayList<MinKitten>()));
        final ArrayAdapter adapter = (ArrayAdapter)listView.getAdapter();

//        loadKittens(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get the full kitten from the server
                Values.SERVICE.getKitten(((MinKitten)adapter.getItem(position)).getId()).enqueue(new ImplCallback<Kitten>(KittenListActivity.this, ProgressDialog.show(KittenListActivity.this, "", "Please wait..", true)) {
                    @Override
                    public void onResponse(Call<Kitten> call, Response<Kitten> response) {
                        super.onResponse(call, response);
                        if(response.isSuccessful()) {
                            Utils.activeKitten = response.body();
                            Intent intent = new Intent(KittenListActivity.this, KittenDetailActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(KittenListActivity.this, Utils.readError(response.errorBody()), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final MinKitten kitten = (MinKitten) listView.getItemAtPosition(position);

                AlertDialog.Builder adb = new AlertDialog.Builder(KittenListActivity.this);
                adb.setTitle("Confirm");
                adb.setMessage("Do you really want to delete " + kitten.getName() + "?");
                adb.setIcon(android.R.drawable.ic_dialog_alert);

                adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Values.SERVICE.removeKitten(kitten.getId()).enqueue(new ImplCallback<Void>(KittenListActivity.this, ProgressDialog.show(KittenListActivity.this, "", "Please wait..", true)) {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                super.onResponse(call, response);
                                if(response.isSuccessful()) {
                                    loadKittens(adapter);
                                    Toast.makeText(KittenListActivity.this, "Removed kitten", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(KittenListActivity.this, Utils.readError(response.errorBody()), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                adb.setNegativeButton("No", new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which) {} });
                adb.show();
                return true;
            }
        });
    }

    private void loadKittens(final ArrayAdapter<MinKitten> adapter) {
        Values.SERVICE.getKittens().enqueue(new Callback<List<MinKitten>>() {
            @Override
            public void onResponse(Call<List<MinKitten>> call, Response<List<MinKitten>> response) {
                adapter.clear();
                adapter.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<MinKitten>> call, Throwable t) {
                Toast.makeText(KittenListActivity.this, "Unable to retrieve kittens", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadKittens((ArrayAdapter)((ListView)findViewById(R.id.list_list)).getAdapter());
    }
}
