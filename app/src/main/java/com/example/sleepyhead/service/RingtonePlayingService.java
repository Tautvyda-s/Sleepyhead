package com.example.sleepyhead.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import com.example.sleepyhead.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RingtonePlayingService  extends Service {
    private static final String TAG = RingtonePlayingService.class.getSimpleName();
    private static final String URI_BASE = RingtonePlayingService.class.getName() + ".";
    public static final String ACTION_DISMISS = URI_BASE + "ACTION_DISMISS";
    private String KEY_RINGTONE = "key_ringtone";

    private Ringtone ringtone;
    private SharedPreferences sharedPreferences;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        if (intent == null) {
            Log.d(TAG, "The intent is null.");
            return START_REDELIVER_INTENT;
        }

        Uri ringtoneUri = Uri.parse(getSelectedRingtone(this));
        ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
        ringtone.play();


        String action = intent.getAction();

        if (ACTION_DISMISS.equals(action))
            dismissRingtone();


        return START_NOT_STICKY;
    }

    public void dismissRingtone() {
        Intent i = new Intent(this, RingtonePlayingService.class);
        stopService(i);
    }

    @Override
    public void onDestroy() {
        ringtone.stop();
    }

    private String getSelectedRingtone(Context context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), MODE_PRIVATE);
        int selectedRingtone = sharedPreferences.getInt(KEY_RINGTONE, 1);
        //get all ringtone list
        RingtoneManager manager = new RingtoneManager(context);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();

        Map<String, String> list = new HashMap<>();
        while (cursor.moveToNext()) {
            String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
            String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX) + "/" + cursor.getString(RingtoneManager.ID_COLUMN_INDEX);

            list.put(notificationTitle, notificationUri);
        }
        // retrieve selected ringtone location
        String value = (new ArrayList<String>(list.values())).get(selectedRingtone);
        return value;
    }
}