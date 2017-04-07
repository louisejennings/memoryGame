package com.ljennings.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.ContextMenu.ContextMenuInfo;


public class MainActivity extends Activity implements View.OnClickListener {

    SharedPreferences prefs;            //use to store highscores
    String dataName = "MyData";
    String intName = "MyInt";
    int defaultInt = 0;
    int hiScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout ll = (LinearLayout) findViewById(R.id.LinearLayout);

        final RadioButton radio_red = (RadioButton) findViewById(R.id.radio_red);       //radio buttons change background
        final RadioButton radio_none = (RadioButton) findViewById(R.id.radio_none);
        radio_red.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ll.setBackgroundColor(Color.RED);                                       //change to red
            }
        });
        radio_none.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ll.setBackgroundColor(Color.BLACK);            //change to black
            }
        });

        prefs = getSharedPreferences(dataName, MODE_PRIVATE);   //objects
        hiScore = prefs.getInt(intName, defaultInt);            //load high score or 0
        TextView textHiScore = (TextView) findViewById(R.id.textHiScore);
        textHiScore.setText("Hi Score: " + hiScore);            //display high score


        Button play = (Button) findViewById(R.id.play);
        play.setOnClickListener(this);                          //play button

        Button extraGames = (Button) findViewById(R.id.extra);  //extra games button Context Menu
        registerForContextMenu(extraGames);
    }

    @Override
    public void onClick(View view) {
        Intent gameIntent = new Intent(this, GameActivity.class);       //play button brings you to...
        startActivity(gameIntent);                                      //...Game Activity
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Extra Games");                 //Add Header
        menu.add(0, v.getId(), 0, "Rudolph");               //Add menu option 1
        menu.add(0, v.getId(), 0, "Colour Slider");         //Menu option 2
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent canvasIntent = new Intent(this, CanvasActivity.class);
        Intent sliderIntent = new Intent(this, SliderActivity.class);

        if (item.getTitle() == "Rudolph") {                 //if Rudolph selected go to..
            startActivity(canvasIntent);                    //...CanvasActivity
        } else if (item.getTitle() == "Colour Slider") {    //if Colour Slider selected go to...
            startActivity(sliderIntent);                    //...SliderActivity
        } else {
            return false;
        }
        return true;
    }


}
