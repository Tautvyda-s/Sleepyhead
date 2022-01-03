package com.example.sleepyhead.games.math;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sleepyhead.R;
import com.example.sleepyhead.games.touch.result;
import com.example.sleepyhead.ui.MainActivity;

public class MathGame extends AppCompatActivity {
    Button btn_start, btn_answer0, btn_answer1, btn_answer2, btn_answer3;
    TextView tv_score, tv_timer, tv_question, tv_bottommessage, tv_tryAgain;
    ProgressBar progress_timer;
    int secondsRemaining = 10;
    Game game = new Game();

    CountDownTimer timer = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long l) {
            secondsRemaining--;
            tv_timer.setText(Integer.toString(secondsRemaining) + "s");
            progress_timer.setProgress(10 - secondsRemaining);
        }

        @Override
        public void onFinish() {
            tv_bottommessage.setGravity(Gravity.CENTER);
            btn_answer0.setEnabled(false);
            btn_answer1.setEnabled(false);
            btn_answer2.setEnabled(false);
            btn_answer3.setEnabled(false);

            if (game.getNumberCorrect() >= 5 && (game.getTotalQuestions() - 1) < 9) {
                game.setPoints(game.getScore());
                Intent intent = new Intent (getApplicationContext(), result.class);
                intent.putExtra("SCORE", game.getScore());
                startActivity(intent);
                overridePendingTransition(R.xml.slide_in_right, R.xml.slide_out_left);

            } else if ((game.getTotalQuestions() - 1) >= 9) {
                tv_tryAgain.setVisibility(View.VISIBLE);
                tv_bottommessage.setText("Time is up! You lose!\nPlease, try not to make mistakes\n\n" + game.getNumberCorrect() + "/" + (game.getTotalQuestions() - 1));
            } else {
                tv_tryAgain.setVisibility(View.VISIBLE);
                tv_bottommessage.setText("Time is up! You lose!\nPlease, try faster\n\n" + game.getNumberCorrect() + "/" + (game.getTotalQuestions() - 1));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_game);

        btn_start = findViewById(R.id.btn_start);
        btn_answer0 = findViewById(R.id.btn_answer0);
        btn_answer1 = findViewById(R.id.btn_answer1);
        btn_answer2 = findViewById(R.id.btn_answer2);
        btn_answer3 = findViewById(R.id.btn_answer3);


        tv_tryAgain = findViewById(R.id.btn_tryAgain);
        tv_tryAgain.setVisibility(View.INVISIBLE);
        tv_score = findViewById(R.id.tv_score);
        tv_bottommessage = findViewById(R.id.tv_bottom_message);
        tv_timer = findViewById(R.id.tv_timer);
        tv_question = findViewById(R.id.tv_questions);

        progress_timer = findViewById(R.id.progressBar);

        tv_timer.setText("10s");
        tv_question.setText("");
        tv_score.setText("0");

        tv_bottommessage.setGravity(Gravity.CENTER);
        tv_bottommessage.setText("Get 5 correct answers\nYou have only 8 tries");

        View.OnClickListener statButtonOnClickListiner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button start_button = (Button) view;
                start_button.setVisibility(View.INVISIBLE);
                secondsRemaining = 10;
                game = new Game();
                nextTurn();
                timer.start();
            }
        };

        View.OnClickListener tryAgainButtonOnClickListiner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        };

        View.OnClickListener answeButtonClickListiner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button buttonClicked = (Button) view;
                int answerSelected = Integer.parseInt(buttonClicked.getText().toString());
                //Toast.makeText(MainActivity.this, "Answer Selected="+answerSelected, Toast.LENGTH_SHORT).show();
                game.checkAnswer(answerSelected);
                tv_score.setText(Integer.toString(game.getScore()));
                nextTurn();

            }
        };
        btn_start.setOnClickListener(statButtonOnClickListiner);

        tv_tryAgain.setOnClickListener(tryAgainButtonOnClickListiner);

        btn_answer0.setOnClickListener(answeButtonClickListiner);
        btn_answer1.setOnClickListener(answeButtonClickListiner);
        btn_answer2.setOnClickListener(answeButtonClickListiner);
        btn_answer3.setOnClickListener(answeButtonClickListiner);
    }

    int i = 0;

    private void nextTurn() {
        game.makeNewQuestion();
        int[] answers = game.getCurrentQuestion().getAnswerArray();
        btn_answer0.setText(Integer.toString(answers[0]));
        btn_answer1.setText(Integer.toString(answers[1]));
        btn_answer2.setText(Integer.toString(answers[2]));
        btn_answer3.setText(Integer.toString(answers[3]));

        btn_answer0.setEnabled(true);
        btn_answer1.setEnabled(true);
        btn_answer2.setEnabled(true);
        btn_answer3.setEnabled(true);

        tv_question.setText(game.getCurrentQuestion().getQuestionPhrase());
        tv_bottommessage.setText(game.getNumberCorrect() + "/" + (game.getTotalQuestions() - 1));
    }
}