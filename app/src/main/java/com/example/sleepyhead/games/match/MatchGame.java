package com.example.sleepyhead.games.match;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sleepyhead.R;
import com.example.sleepyhead.games.math.Game;
import com.example.sleepyhead.games.math.MathGame;
import com.example.sleepyhead.games.touch.result;
import com.example.sleepyhead.ui.MainActivity;

import java.util.Arrays;
import java.util.Collections;

public class MatchGame extends AppCompatActivity {
    //game points for players
    TextView tv_p1,tv_bottommessage, go, score_txt;
    Game game = new Game();

    //all images
    ImageView iv_11, iv_12, iv_13, iv_14, iv_21, iv_22, iv_23, iv_24, iv_31, iv_32, iv_33, iv_34;
    //array for images
    Integer[] cardsArray = {101, 102, 103, 104, 105, 106, 201, 202, 203, 204, 205, 206};
    Button tryAgain;
    //images
    int image101, image102, image103, image104, image105, image106,
            image201, image202, image203, image204, image205, image206;
    //cards
    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    //selected card turn
    int cardNumber =1 ;
    //game points
    int playedPoints = 0;
    int times = 0;
    int turn = 1;
    int score=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_game);

        //find all subjects by id
        score_txt = (TextView) findViewById(R.id.tv_score);
        tv_p1 = (TextView) findViewById(R.id.tv_p1);
        tv_bottommessage = (TextView) findViewById(R.id.tv_bottom_message);

        iv_11 =(ImageView) findViewById(R.id.iv11);
        iv_12 =(ImageView) findViewById(R.id.iv12);
        iv_13 =(ImageView) findViewById(R.id.iv13);
        iv_14 =(ImageView) findViewById(R.id.iv14);
        iv_21 =(ImageView) findViewById(R.id.iv21);
        iv_22 =(ImageView) findViewById(R.id.iv22);
        iv_23 =(ImageView) findViewById(R.id.iv23);
        iv_24 =(ImageView) findViewById(R.id.iv24);
        iv_31 =(ImageView) findViewById(R.id.iv31);
        iv_32 =(ImageView) findViewById(R.id.iv32);
        iv_33 =(ImageView) findViewById(R.id.iv33);
        iv_34 =(ImageView) findViewById(R.id.iv34);
        tryAgain =(Button) findViewById(R.id.btn_tryAgain);

        go = (Button) findViewById(R.id.btn_start);

        iv_11.setEnabled(false);
        iv_12.setEnabled(false);
        iv_13.setEnabled(false);
        iv_14.setEnabled(false);
        iv_21.setEnabled(false);
        iv_22.setEnabled(false);
        iv_23.setEnabled(false);
        iv_24.setEnabled(false);
        iv_32.setEnabled(false);
        iv_33.setEnabled(false);
        iv_34.setEnabled(false);

        tryAgain.setVisibility(View.INVISIBLE);

        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");
        iv_21.setTag("4");
        iv_22.setTag("5");
        iv_23.setTag("6");
        iv_24.setTag("7");
        iv_31.setTag("8");
        iv_32.setTag("9");
        iv_33.setTag("10");
        iv_34.setTag("11");

        //load the card images
        frontOfCardsResources();
        //shuffle the images
        Collections.shuffle(Arrays.asList(cardsArray));
        //changing the color of the second player (inactive)

        View.OnClickListener goButtonOnClickListiner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go.setVisibility(View.INVISIBLE);
                iv_11.setEnabled(true);
                iv_12.setEnabled(true);
                iv_13.setEnabled(true);
                iv_14.setEnabled(true);
                iv_21.setEnabled(true);
                iv_22.setEnabled(true);
                iv_23.setEnabled(true);
                iv_24.setEnabled(true);
                iv_32.setEnabled(true);
                iv_33.setEnabled(true);
                iv_34.setEnabled(true);
            }
        };

        View.OnClickListener tryAgainButtonOnClickListiner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
                overridePendingTransition(R.xml.slide_in_right, R.xml.slide_out_left);
            }
        };
        go.setOnClickListener(goButtonOnClickListiner);
        tryAgain.setOnClickListener(tryAgainButtonOnClickListiner);


        iv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                matchCards(iv_11, theCard);
            }
        });
        iv_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                matchCards(iv_12, theCard);
            }
        });
        iv_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                matchCards(iv_13, theCard);
            }
        });
        iv_14 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                matchCards(iv_14, theCard);
            }
        });
        iv_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                matchCards(iv_21, theCard);
            }
        });
        iv_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                matchCards(iv_22, theCard);
            }
        });
        iv_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                matchCards(iv_23, theCard);
            }
        });
        iv_24 .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                matchCards(iv_24, theCard);
            }
        });
        iv_31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                matchCards(iv_31, theCard);
            }
        });
        iv_32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                matchCards(iv_32, theCard);
            }
        });
        iv_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                matchCards(iv_33, theCard);
            }
        });
        iv_34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                matchCards(iv_34, theCard);
            }
        });
    }
    private void frontOfCardsResources() {
        //set card image
        image101 = R.color.accent;
        image102 = R.color.yellow;
        image103 = R.color.green;
        image104 = R.color.purple;
        image105 = R.color.baby_blue;
        image106 = R.color.black;
        image201 = R.color.accent;
        image202 = R.color.yellow;
        image203 = R.color.green;
        image204 = R.color.purple;
        image205 = R.color.baby_blue;
        image206 = R.color.black;

    }

    private void matchCards(ImageView iv, int card) {
        //set the correct image
        if (cardsArray[card] == 101) {
            iv.setImageResource(image101);
        } else if (cardsArray[card] == 102) {
            iv.setImageResource(image102);
        } else if (cardsArray[card] == 103) {
            iv.setImageResource(image103);
        } else if (cardsArray[card] == 104) {
            iv.setImageResource(image104);
        } else if (cardsArray[card] == 105) {
            iv.setImageResource(image105);
        } else if (cardsArray[card] == 106) {
            iv.setImageResource(image106);
        } else if (cardsArray[card] == 201) {
            iv.setImageResource(image201);
        } else if (cardsArray[card] == 202) {
            iv.setImageResource(image202);
        } else if (cardsArray[card] == 203) {
            iv.setImageResource(image203);
        } else if (cardsArray[card] == 204) {
            iv.setImageResource(image204);
        } else if (cardsArray[card] == 205) {
            iv.setImageResource(image205);
        } else if (cardsArray[card] == 206) {
            iv.setImageResource(image206);
        }
        //check which image is selected and save it to temporary variable
        if(cardNumber==1) {
            firstCard = cardsArray[card];
            if (firstCard > 200) {
                firstCard = firstCard - 100;
            }
            cardNumber = 2;
            clickedFirst = card;
            iv.setEnabled(false);
        }else if(cardNumber ==2){
            secondCard = cardsArray[card];
            if (secondCard > 200) {
                secondCard = secondCard - 100;
            }
            cardNumber = 1;
            clickedSecond = card;

            iv_11.setEnabled(false);
            iv_12.setEnabled(false);
            iv_13.setEnabled(false);
            iv_14.setEnabled(false);
            iv_21.setEnabled(false);
            iv_22.setEnabled(false);
            iv_23.setEnabled(false);
            iv_24.setEnabled(false);
            iv_31.setEnabled(false);
            iv_32.setEnabled(false);
            iv_33.setEnabled(false);
            iv_34.setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //check if the selected images are equal
                    calculate();
                }
            },  800);
        }
    }

    private void calculate() {
        //if the images are equal remove them and add point
        if (firstCard == secondCard) {
            if (clickedFirst == 0) {
                iv_11.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 1) {
                iv_12.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 2) {
                iv_13.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 3) {
                iv_14.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 4) {
                iv_21.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 5) {
                iv_22.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 6) {
                iv_23.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 7) {
                iv_24.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 8) {
                iv_31.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 9) {
                iv_32.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 10) {
                iv_33.setVisibility(View.INVISIBLE);
            } else if (clickedFirst == 11) {
                iv_34.setVisibility(View.INVISIBLE);
            }
            if (clickedSecond == 0) {
                iv_11.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 1) {
                iv_12.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 2) {
                iv_13.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 3) {
                iv_14.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 4) {
                iv_21.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 5) {
                iv_22.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 6) {
                iv_23.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 7) {
                iv_24.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 8) {
                iv_31.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 9) {
                iv_32.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 10) {
                iv_33.setVisibility(View.INVISIBLE);
            } else if (clickedSecond == 11) {
                iv_34.setVisibility(View.INVISIBLE);
            }

            playedPoints++;

        } else {
            iv_11.setImageResource(R.drawable.button_border);
            iv_12.setImageResource(R.drawable.button_border);
            iv_13.setImageResource(R.drawable.button_border);
            iv_14.setImageResource(R.drawable.button_border);
            iv_21.setImageResource(R.drawable.button_border);
            iv_22.setImageResource(R.drawable.button_border);
            iv_23.setImageResource(R.drawable.button_border);
            iv_24.setImageResource(R.drawable.button_border);
            iv_31.setImageResource(R.drawable.button_border);
            iv_32.setImageResource(R.drawable.button_border);
            iv_33.setImageResource(R.drawable.button_border);
            iv_34.setImageResource(R.drawable.button_border);

        }
        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);
        iv_21.setEnabled(true);
        iv_22.setEnabled(true);
        iv_23.setEnabled(true);
        iv_24.setEnabled(true);
        iv_31.setEnabled(true);
        iv_32.setEnabled(true);
        iv_33.setEnabled(true);
        iv_34.setEnabled(true);
        //check if the game is over
        checkEnd();
        times++;
        tv_p1.setText( playedPoints+" / "+ times);
        score = (playedPoints*30-times*10);
        score_txt.setText(score+" ");
    }
    private void checkEnd () {
        if (iv_11.getVisibility() == View.INVISIBLE &&
                iv_12.getVisibility() == View.INVISIBLE &&
                iv_13.getVisibility() == View.INVISIBLE &&
                iv_14.getVisibility() == View.INVISIBLE &&
                iv_21.getVisibility() == View.INVISIBLE &&
                iv_22.getVisibility() == View.INVISIBLE &&
                iv_23.getVisibility() == View.INVISIBLE &&
                iv_24.getVisibility() == View.INVISIBLE &&
                iv_31.getVisibility() == View.INVISIBLE &&
                iv_32.getVisibility() == View.INVISIBLE &&
                iv_33.getVisibility() == View.INVISIBLE &&
                iv_34.getVisibility() == View.INVISIBLE) {

            if(times<=15)
            {
                game.setPoints(score);
                Intent intent = new Intent (getApplicationContext(), result.class);
                intent.putExtra("SCORE", score);
                startActivity(intent);
                overridePendingTransition(R.xml.slide_in_right, R.xml.slide_out_left);

            }
            else
            {
                tryAgain.setVisibility(View.VISIBLE);
                tv_bottommessage.setText("You failed!\nToo many tries!");
            }
        }
    }
}