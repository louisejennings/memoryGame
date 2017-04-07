package com.ljennings.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class CanvasActivity extends Activity {
    private TextView textTime;
    int score=0;
    View c,b,m,w, playAgain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        new CountDownTimer(20000, 1000) {               //20sec countdown timer per second

            public void onTick(long millisUntilFinished) {
                textTime = (TextView) findViewById(R.id.textTime);
                textTime.setText("seconds remaining: " + millisUntilFinished / 1000);
                View playAgain = findViewById(R.id.playagain);
                playAgain.setVisibility(View.GONE);     //hide playagain button
            }

            public void onFinish() {                    //when timer finishes remove image buttons
                c = findViewById(R.id.carrot);
                b = findViewById(R.id.brussels);
                m = findViewById(R.id.mincepie);
                w = findViewById(R.id.water);
                c.setVisibility(View.GONE);             //hide all food buttons
                b.setVisibility(View.GONE);
                m.setVisibility(View.GONE);
                w.setVisibility(View.GONE);
                textTime.setText("Thank You I'm Full!\nYou Scored:" + score);
                playAgain = findViewById(R.id.playagain);
                playAgain.setVisibility(View.VISIBLE); //display playagain button
            }
        }.start();
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.carrot:
                Toast.makeText(getApplicationContext(), "MY FAVOURITE", Toast.LENGTH_SHORT).show();
                score++;                                //add 1 to score
                break;
            case R.id.brussels:
                Toast.makeText(getApplicationContext(), "YUCKY", Toast.LENGTH_SHORT).show();
                score--;                                //minus 1 from score
                break;
            case R.id.mincepie:
                Toast.makeText(getApplicationContext(), "YUM", Toast.LENGTH_SHORT).show();
                score++;
                break;
            case R.id.water:
                Toast.makeText(getApplicationContext(), "THANK YOU", Toast.LENGTH_SHORT).show();
                score++;
                break;
            case R.id.playagain:
                Intent canvasIntent = new Intent(this, CanvasActivity.class);
                startActivity(canvasIntent);            //restarts CanvasActivity
            default:
                break;
        }

    }


}