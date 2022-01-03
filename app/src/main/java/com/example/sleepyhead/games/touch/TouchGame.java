package com.example.sleepyhead.games.touch;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.sleepyhead.R;

import java.util.Timer;
import java.util.TimerTask;

public class TouchGame extends AppCompatActivity {


    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView box;
    private ImageView could;
    private ImageView pink;
    private ImageView black;

    Button tryAgain;
    ProgressBar progress_timer;
    TextView tv_tryAgain, tv_timer, tv_rules;

    //screen size
    private int frameHeight;
    private int boxSize;
    private int screenWidth;
    private int screenHeight;

    //position
    private int boxY;
    private int cloudX;
    private int cloudY;
    private int pinkX;
    private int pinkY;
    private int blackX;
    private int blackY;
    // Speed
    private int boxSpeed;
    private int orangeSpeed;
    private int pinkSpeed;
    private int blackSpeed;

    private int score = 0;

    //initialize classs
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private SoundPlayer sound;

    //status check
    private boolean action_flg = false;
    private boolean start_flg = false;
    private boolean finsh_flg = false;

    int secondsRemaining = 10;

    CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long l) {
            secondsRemaining--;
            tv_timer.setText(Integer.toString(secondsRemaining) + "s");
            progress_timer.setProgress(10 - secondsRemaining);
        }
        @Override
        public void onFinish() {
            onPause();
            finsh_flg = true;
            Intent intent = new Intent (getApplicationContext(), result.class);
            intent.putExtra("SCORE", score);
            startActivity(intent);
            overridePendingTransition(R.xml.slide_in_right, R.xml.slide_out_left);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);

        sound = new SoundPlayer(this);
            tryAgain = (Button) findViewById(R.id.btn_tryAgain);
            tryAgain.setVisibility(View.INVISIBLE);

            tv_rules = findViewById(R.id.rulesLabel);
            tv_timer = findViewById(R.id.tv_timer);
            tv_timer.setText("10s");
            tv_tryAgain = findViewById(R.id.btn_tryAgain);
            tv_tryAgain.setVisibility(View.INVISIBLE);

            progress_timer = findViewById(R.id.progressBar1);
            scoreLabel = (TextView) findViewById(R.id.scoreLabel);
            startLabel = (TextView) findViewById(R.id.startLabel);
            box = (ImageView) findViewById(R.id.box);
            could = (ImageView) findViewById(R.id.orange);
            pink =
                    (ImageView) findViewById(R.id.pink);
            black = (ImageView) findViewById(R.id.black);
            //Get screen size
            WindowManager wm = getWindowManager();
            Display displ = wm.getDefaultDisplay();
            Point size = new Point();
            displ.getSize(size);


            screenWidth = size.x;
            screenHeight = size.y;
            boxSpeed = Math.round(screenHeight / 60F);// 1184 / 6019.733... - 20
            orangeSpeed = Math.round(screenWidth / 60F); // 768 / 6012.8 => 13
            pinkSpeed = Math.round(screenWidth / 36F);// 768 / 3621.333 - 21
            blackSpeed = Math.round(screenWidth / 45F);// 768 / 45 - 17.06.. => 17
        /*Log.v("SPEED_BOX", boxSpeed + "");
        Log.v("SPEED_ORANGE", orangeSpeed+"");
        Log.v("SPEED_PINK", pinkSpeed+"");
        Log.v("SPEED_BLACK", blackSpeed+"");*/

    // Move to out of screen.
            could.setX(-80);
            could.setY(-80);
            pink.setX(-80);
            pink.setY(-80);
            black.setX(-80);
            black.setY(-80);

            scoreLabel.setText("Score: 0");

            View.OnClickListener tryAgainButtonOnClickListiner = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    startActivity(getIntent());
                }
            };
            tryAgain.setOnClickListener(tryAgainButtonOnClickListiner);
        }

        public void changePos() {

            hitCheck();

            // Orange
            cloudX -= orangeSpeed;
            if (cloudX < 0) {
                cloudX = screenWidth + 20;
                cloudY = (int) Math.floor(Math.random() * (frameHeight - could.getHeight()));
            }
            could.setX(cloudX);
            could.setY(cloudY);

            // Blue
            blackX -= blackSpeed;
            if (blackX < 0) {
                blackX = screenWidth + 20;
                blackY = (int) Math.floor(Math.random() * (frameHeight - black.getHeight()));
            }
            black.setX(blackX);
            black.setY(blackY);

            // Pink
            pinkX -= pinkSpeed;
            if (pinkX < 0) {
                pinkX = screenWidth + 20;
                pinkY = (int) Math.floor(Math.random() * (frameHeight - pink.getHeight()));
            }
            pink.setX(pinkX);
            pink.setY(pinkY);

            //Move box
            if (action_flg == true) {
                // Touching
                boxY -= boxSpeed;
            } else {
                // Releasing
                boxY += boxSpeed;
            }
            //Check box position
            if (boxY < 0) boxY = 0;
            if (boxY > frameHeight - boxSize) boxY = frameHeight - boxSize;

            box.setY(boxY);

            scoreLabel.setText("Score: " + score);
        }
        public void endCheck(){
        onPause();
        onStop();
    }

    public void hitCheck() {
            //if the center of the cloud is in the plane, it counts as a hit.

        if(finsh_flg==true){
            onPause();
        }
        else{
            int cloudCenterX = cloudX + could.getHeight() / 2;
            int cloudCenterY = cloudY + could.getHeight() / 2;

            if (0 <= cloudCenterX && cloudCenterX <= boxSize &&
                    boxY <= cloudCenterY && cloudCenterY <= boxY + boxSize) {
                score += 10;
                cloudX = -10;
                sound.playHitSound();

            }
            int cloudFullCenterX = pinkX + pink.getWidth() / 2;
            int cloudfFullCenterY = pinkY + pink.getHeight() / 2;
            if (0 <= cloudFullCenterX && cloudFullCenterX <= boxSize &&
                    boxY <= cloudfFullCenterY && cloudfFullCenterY <= boxY + boxSize) {
                score += 30;
                pinkX = -10;
                sound.playHitSound();
            }

            // Black
            int blackCenterX = blackX + black.getWidth() / 2;
            int blackCenterY = blackY + black.getHeight() / 2;
            if (0 <= blackCenterX && blackCenterX <= boxSize &&
                    boxY <= blackCenterY && blackCenterY < boxY + boxSize) {
            // Stop Timer!!
                countDownTimer.cancel();
                timer.cancel();
                timer = null;
                sound.play0verSound();

                // Show Result
                onPause();
                tv_tryAgain.setVisibility(View.VISIBLE);
            }
            }

        }

        public boolean onTouchEvent(MotionEvent me) {

            if (start_flg == false) {
                start_flg = true;

                FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
                frameHeight = frame.getHeight();

                boxY = (int) box.getY();

                //the box is a square. (height and width are the same.
                boxSize = box.getHeight();

                tv_rules.setVisibility(View.GONE);
                startLabel.setVisibility(View.GONE);
                countDownTimer.start();

                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                changePos();
                            }
                        });
                    }
                }, 0, 20);
            } else {
                if (me.getAction() == MotionEvent.ACTION_DOWN) {
                    action_flg = true;
                } else if (me.getAction() == MotionEvent.ACTION_UP) {
                    action_flg = false;
                }
            }
            return true;
        }
    }
