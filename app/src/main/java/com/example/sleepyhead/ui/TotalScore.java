package com.example.sleepyhead.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.sleepyhead.R;
import com.example.sleepyhead.games.math.Game;

import java.util.List;

public class TotalScore extends AppCompatActivity {
    Game game = new Game();
    private int highScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_score);
        TextView highScoreLabel = (TextView) findViewById(R.id.highScoreLabel);

        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        highScore = settings.getInt("HIGH_SCORE", 0);
        highScoreLabel.setText(" " + highScore);
    }

    //Sharing score on social media
    public void shareOnSocialMedia(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name)+"\nHigh Score: " + highScore);
        startActivity(Intent.createChooser(intent, "Share Score"));
    }


}