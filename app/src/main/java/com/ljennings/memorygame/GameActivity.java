package com.ljennings.memorygame;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Random;
import android.os.Handler;
import android.widget.Toast;


public class GameActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    Animation scale;                        //animation object small to large scale
    Spinner spinner;                        //spinner for easy/hard (speed)

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    String dataName = "MyData";
    String intName = "MyInt";
    int defaultInt = 0;
    int hiScore;

    TextView textScore;
    TextView textDifficulty;
    ImageButton dog;
    ImageButton cat;
    ImageButton pig;
    ImageButton cow;
    Button buttonReplay;
    Button buttonRestart;

    int difficulty = 2;                     //start displaying 2 images
    int level = 1;                          //start level 1
    int speed;                              //speed slow/fast (easy/hard)

    int[] sequenceToCopy = new int[100];    //array for random sequence
    private Handler myHandler;
    boolean playSequence = false;           //if playing a sequence
    int elementToPlay = 0;                  //track element of the sequence

    int playerResponses;
    int playerScore;
    boolean isResponding;


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView myText = (TextView) view;
        Toast.makeText(this, "You selected " + myText.getText(), Toast.LENGTH_SHORT).show();
        //Toast displays the choice from the spinner (easy/hard)
        if (myText.getText().equals("hard")) { //Set speed to 1 if hard was selected
            speed = 1;
        } else {
            speed = 0;
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        //if nothing selected dont' do anything
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Difficulty, android.R.layout.simple_spinner_dropdown_item);
       //use the array Difficulty stored in strings.xml
        spinner.setAdapter(adapter);

        scale = AnimationUtils.loadAnimation(this, R.anim.scale);   //animation scale on each image

        prefs = getSharedPreferences(dataName, MODE_PRIVATE);
        editor = prefs.edit();
        hiScore = prefs.getInt(intName, defaultInt);

        textScore = (TextView) findViewById(R.id.textScore);
        textScore.setText(getString(R.string.Score) + playerScore);                 //set Score
        textDifficulty = (TextView) findViewById(R.id.textDifficulty);
        textDifficulty.setText(getString(R.string.Level) + level);                  //set Level

        dog = (ImageButton) findViewById(R.id.dog);                 //dog
        cat = (ImageButton) findViewById(R.id.cat);                 //cat
        pig = (ImageButton) findViewById(R.id.pig);                 //cow
        cow = (ImageButton) findViewById(R.id.cow);                 //pig
        buttonReplay = (Button) findViewById(R.id.buttonReplay);
        buttonRestart = (Button) findViewById(R.id.Restart);
        dog.setOnClickListener(this);
        cat.setOnClickListener(this);
        pig.setOnClickListener(this);
        cow.setOnClickListener(this);
        buttonReplay.setOnClickListener(this);
        buttonRestart.setOnClickListener(this);

        spinner.setOnItemSelectedListener(this);

        //  buttonReplay.setTag(1);                             //set for replay/play status

        myHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (playSequence) {
                    switch (sequenceToCopy[elementToPlay]) {
                        case 1:
                            dog.startAnimation(scale);      //start animation
                            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.dog);
                            mp.start();                         //play sound
                            break;

                        case 2:
                            cat.startAnimation(scale);
                            mp = MediaPlayer.create(getApplicationContext(), R.raw.cat);
                            mp.start();
                            break;

                        case 3:
                            pig.startAnimation(scale);
                            mp = MediaPlayer.create(getApplicationContext(), R.raw.pig);
                            mp.start();
                            break;

                        case 4:
                            cow.startAnimation(scale);
                            mp = MediaPlayer.create(getApplicationContext(), R.raw.cow);
                            mp.start();
                            break;
                    }
                    elementToPlay++;
                    if (elementToPlay == difficulty) {
                        sequenceFinished();
                    }
                }
                if (speed == 1) {
                    myHandler.sendEmptyMessageDelayed(0, 1000); //hard faster
                } else if (speed == 0) {
                    myHandler.sendEmptyMessageDelayed(0, 2000); //easy slower
                }
            }

        };
        myHandler.sendEmptyMessage(0);

    }


    public void onClick(View view) {
        // final int status = (Integer) view.getTag();   //set status for button play/replay

        if (!playSequence) {                            //sequence accepted once everything finished
            switch (view.getId()) {
                case R.id.dog:
                    checkElement(1);
                    break;

                case R.id.cat:
                    checkElement(2);
                    break;

                case R.id.pig:
                    checkElement(3);
                    break;

                case R.id.cow:
                    checkElement(4);
                    break;

                case R.id.buttonReplay:
                                       /* if (status == 1) {
                                            buttonReplay.setText("Replay");     //change play button to replay
                                            view.setTag(0); //
                                        } else {
                                            buttonReplay.setText("Play");
                                            view.setTag(1); //
                                        }*/

                    difficulty = 2;
                    playerScore = 0;
                    textScore.setText("Score: " + playerScore);
                    playASequence();
                    break;

                case R.id.Restart:      //wipe all details and start game over
                    difficulty = 2;
                    level = 1;
                    textDifficulty.setText(getString(R.string.Level) + level);
                    playerScore = 0;
                    textScore.setText("Score: " + 0);
                    break;
            }
        }

    }

    public void createSequence() {
        Random randInt = new Random();
        int ourRandom;
        for (int i = 0; i < difficulty; i++) {                  //random number that is not 0 saved to array
            ourRandom = randInt.nextInt(4);
            ourRandom++;
            sequenceToCopy[i] = ourRandom;
        }
    }

    public void playASequence() {
        createSequence();
        isResponding = false;
        elementToPlay = 0;
        playerResponses = 0;
        playSequence = true;

    }

    public void sequenceFinished() {
        playSequence = false;
        isResponding = true;
    }

    public void checkElement(int thisElement) {

        if (isResponding) {
            playerResponses++;
            if (sequenceToCopy[playerResponses - 1] == thisElement) {        //Correct sequence
                playerScore = (playerScore + 1);                             //1pt per correct image
                textScore.setText("Score: " + playerScore);
                if (playerResponses == difficulty) {
                    isResponding = false;
                    difficulty++;                                           //increase level and images +1
                    level++;
                    textDifficulty.setText(getString(R.string.Level) + level);
                    playASequence();
                }

            } else {                                                        //Incorrect sequence
                isResponding = false;
                if (playerScore > hiScore) {                                 //check for hiScore
                    hiScore = playerScore;
                    editor.putInt(intName, hiScore);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "New Hi-score", Toast.LENGTH_LONG).show();
                }

            }

        }

    }


}
