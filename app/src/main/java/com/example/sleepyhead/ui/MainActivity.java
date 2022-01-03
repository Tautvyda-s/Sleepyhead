package com.example.sleepyhead.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;

import com.example.sleepyhead.R;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String KEY_NIGHT_MODE="key_night_mode";
    private boolean isNightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(this.getString(R.string.app_name),MODE_PRIVATE);
        isNightMode = sharedPreferences.getBoolean(KEY_NIGHT_MODE,false);
        setTheme(isNightMode);
    }

    @Override
    public void recreate() {
        super.recreate();
        isNightMode = sharedPreferences.getBoolean(KEY_NIGHT_MODE,false);
        setTheme(isNightMode);
    }

    private void setTheme(boolean isChecked) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //getDelegate().applyDayNight();
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    // Integrate Menu XML with Activity Class
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }


    //When menu item clicked this method will called
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
