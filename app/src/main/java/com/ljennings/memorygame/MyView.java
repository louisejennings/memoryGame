package com.ljennings.memorygame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    public MyView(Context cxt, AttributeSet attrs) {
        super(cxt, attrs);
        setMinimumHeight(100);
        setMinimumWidth(100);
    }


    @Override
    protected void onDraw(Canvas cv) {
        Paint paint;
        paint = new Paint();
        cv.drawColor(Color.parseColor("#000000"));          //set canvas background black
        Bitmap bitmapReindeer;
        bitmapReindeer = BitmapFactory.decodeResource(this.getResources(), R.drawable.rudolph);
        cv.drawBitmap(bitmapReindeer, 100, 0, paint);       //add rudolph bitmap image to canvas
        paint.setColor(Color.parseColor("#FF0000"));          //change colour to red
        cv.drawCircle(300, 280, 40, paint);                   //draw circle for nose
        cv.drawRect(0, 450, 830, 800, paint);                 //draw rectangle
        paint.setColor(Color.parseColor("#FFFFFF"));        //change colour to white
        paint.setTextSize(30);                              //set font size
        cv.drawText("Merry Christmas", 200, 520, paint);    //write Merry Christmas in rectangle

    }
}