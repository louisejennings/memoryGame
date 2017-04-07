package com.ljennings.memorygame;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;


public class SliderActivity extends Activity {


    private int seekR, seekG, seekB;
    SeekBar redSeekBar, greenSeekBar, blueSeekBar;
    LinearLayout mScreen,paint;

   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        mScreen = (LinearLayout) findViewById(R.id.myScreen);       //top layout for the sliders
        paint=(LinearLayout) findViewById(R.id.canvas);             //underneath layout for the image of house
        redSeekBar = (SeekBar) findViewById(R.id.mySeekingBar_R);
        greenSeekBar = (SeekBar) findViewById(R.id.mySeekingBar_G);
        blueSeekBar = (SeekBar) findViewById(R.id.mySeekingBar_B);
        updateBackground();

        redSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        greenSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        blueSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);

    }
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener
            = new SeekBar.OnSeekBarChangeListener()
    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
            updateBackground();
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    private void updateBackground()
    {
        seekR = redSeekBar.getProgress();
        seekG = greenSeekBar.getProgress();
        seekB = blueSeekBar.getProgress();
        mScreen.setBackgroundColor(                 //0xAARRGGBB AA-transparency RGBvalue
                0xff000000
                        + seekR * 0x10000           //set the colours in accordance to position on slider
                        + seekG * 0x100
                        + seekB
        );
        paint.setBackgroundColor(0xff000000);       //set background black
    }

}
