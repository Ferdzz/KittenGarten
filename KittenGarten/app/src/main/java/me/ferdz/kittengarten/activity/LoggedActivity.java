package me.ferdz.kittengarten.activity;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import me.ferdz.kittengarten.R;
import me.ferdz.kittengarten.util.Utils;

/**
 * Created by 1452284 on 2016-09-01.
 */
public class LoggedActivity extends AppCompatActivity {

    private ActionBarDrawerToggle toggle;

    protected void initDrawer(int drawer, int navigationView) {
        final DrawerLayout drawerLayout = ((DrawerLayout)findViewById(drawer));
        final NavigationView navView = ((NavigationView)findViewById(navigationView));
//        ((TextView)getSupportActionBar().R.id.header_username)).setText(Utils.loggedUser.getUsername());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent;
                switch(item.getItemId()) {
                    case R.id.menu_logout:
                        Utils.logout(LoggedActivity.this, ProgressDialog.show(LoggedActivity.this, "", "Please wait..", true));
                        break;
                    case R.id.menu_add_kitten:
                        intent = new Intent(LoggedActivity.this, KittenAddActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_home:
                        intent = new Intent(LoggedActivity.this, KittenListActivity.class);
                        startActivity(intent);
                        break;
               }
                drawerLayout.closeDrawers();
                return true;
            }
        });
        ((TextView)navView.getHeaderView(0).findViewById(R.id.header_username)).setText(Utils.loggedUser.getUsername());

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        toggle.onConfigurationChanged(newConfig);
        super.onConfigurationChanged(newConfig);
    }
}
