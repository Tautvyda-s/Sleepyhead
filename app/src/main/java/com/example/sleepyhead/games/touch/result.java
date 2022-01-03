package com.example.sleepyhead.games.touch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.support.v7.app.AppCompatActivity;

import com.example.sleepyhead.R;
import com.example.sleepyhead.ui.MainActivity;

public class result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        TextView highScoreLabel =(TextView) findViewById(R.id.highScoreLabel);
        int score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel. setText (score + "");
        SharedPreferences settings = getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE", 0);
        int finalScore = highScore+score;
        highScoreLabel.setText("Total Score: "+finalScore);

        // Save
        SharedPreferences. Editor editor =settings.edit();
        editor.putInt("HIGH_SCORE", finalScore);
        editor.commit();

    }
    public void goToMain(View view){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}