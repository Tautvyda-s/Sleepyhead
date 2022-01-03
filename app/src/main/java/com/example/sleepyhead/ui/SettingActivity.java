package com.example.sleepyhead.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.sleepyhead.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String KEY_NIGHT_MODE = "key_night_mode";
    private String KEY_RINGTONE = "key_ringtone";
    private boolean isNightMode;
    private Switch switchTh;
    private LinearLayout llRingtone;
    int selectedRingtone = 1;
    private TextView tvRingtoneName;
    ArrayList<String> ringtoneNameArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sharedPreferences = getSharedPreferences(this.getString(R.string.app_name), MODE_PRIVATE);
        isNightMode = sharedPreferences.getBoolean(KEY_NIGHT_MODE, false);
        selectedRingtone = sharedPreferences.getInt(KEY_RINGTONE, 1);

        llRingtone = findViewById(R.id.llRingtone);
        tvRingtoneName = findViewById(R.id.tvRingtoneName);

        switchTh = findViewById(R.id.switchTheme);
        switchTh.setChecked(isNightMode);

        switchTh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                sharedPreferences.edit().putBoolean(KEY_NIGHT_MODE, isChecked).apply();
                setTheme(isChecked);
                onBackPressed();
            }
        });

        llRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNotifications();
            }
        });

        //get all ringtone list
        RingtoneManager manager = new RingtoneManager(this);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();

        Map<String, String> list = new HashMap<>();
        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX) + "/" + cursor.getString(RingtoneManager.ID_COLUMN_INDEX);

            list.put(notificationTitle, notificationUri);
            ringtoneNameArrayList.add(notificationTitle);
        }

        //this will set selected alarm ringtone name
        tvRingtoneName.setText(ringtoneNameArrayList.get(selectedRingtone));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SettingActivity.this,MainActivity.class));
        finish();
    }

    public void getNotifications() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Ringtone");

        CharSequence[] cs = ringtoneNameArrayList.toArray(new CharSequence[ringtoneNameArrayList.size()]);
        // add a radio button list
        builder.setSingleChoiceItems(cs, selectedRingtone, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                sharedPreferences.edit().putInt(KEY_RINGTONE, position).apply();
                tvRingtoneName.setText(ringtoneNameArrayList.get(position));
            }
        });

        // add OK and Cancel buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setTheme(boolean isChecked) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }


}