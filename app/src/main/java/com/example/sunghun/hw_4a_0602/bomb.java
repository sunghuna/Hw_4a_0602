package com.example.sunghun.hw_4a_0602;

/**
 * Created by sunghun on 2016. 6. 3..
 */
import android.content.Context;
import android.content.res.Resources;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.util.AttributeSet;
import android.util.Log;

import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import android.widget.Toast;

public class bomb extends View {
    //public int rows=5, cols=4;
    private Bitmap before,after;
    private Bitmap before2, after2;
    private int width=0, height=0, x=0, y=0;
    private int count=0;
    private boolean[][] check = new boolean[5][4];

    //initialize the canvas
    public void init() {
        for(int i=0; i<5; i++)
            for(int j=0; j<4; j++)
                check[i][j] = true;
        Resources res = getResources();
        before = BitmapFactory.decodeResource(res, R.drawable.bomb1);
        after = BitmapFactory.decodeResource(res, R.drawable.bomb2);
    }

    //change the img size according to the display size
    public bomb(Context c) {
        super(c);
        init();
        Display display = ((WindowManager)c.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        before2 =  Bitmap.createScaledBitmap(before, width/5, height/5, false);
        after2 = Bitmap.createScaledBitmap(after, width/5, height/5, false);
    }

    //change the img size according to the display size
    public bomb(Context c, AttributeSet a) {
        super(c, a);
        init();

        Display display = ((WindowManager)c.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        before2 =  Bitmap.createScaledBitmap(before, width/5, height/5, false);
        after2 = Bitmap.createScaledBitmap(after, width/5, height/5, false);
    }

    //when touch event is occur get the postion of x and y and do the action(call invalidate fuction)
    public boolean onTouchEvent(MotionEvent event) {
        x = (int)event.getX();
        y = (int)event.getY();

        if (event.getAction() == MotionEvent.ACTION_UP)
            invalidate();
        return true;
    }


    protected void onDraw(Canvas canvas) {
        for(int i=0; i<5; i++){
            for(int j=0; j<4; j++){
                if((x < width/5 + i*before2.getWidth() && x > i*before2.getWidth()) &&
                        (y < height/5 + j*before2.getHeight() && y > j*before2.getHeight())){

                    count++;
                    Toast.makeText(getContext(),""+count,Toast.LENGTH_LONG).show();
                    //when bomb that clicked already is clicked again submission the count
                    if(check[i][j]==false)
                        count--;

                    check[i][j] = false;
                    //change the img into after bomb clicked img
                    canvas.drawBitmap(after2, i*before2.getWidth(), j*before2.getHeight(),null);
                    canvas.save();
                }
                else{
                    if(check[i][j] == true)
                        canvas.drawBitmap(before2, i*before2.getWidth(), j*before2.getHeight(),null);
                    else
                        canvas.drawBitmap(after2, i*before2.getWidth(), j*before2.getHeight(),null);
                }
            }
        }
        if(count==20){
            Toast.makeText(getContext(), "Game Finish, Start New Game", Toast.LENGTH_LONG).show();
            //when game finished new game will be started
            for(int i=0; i<5; i++){
                for(int j=0; j<4; j++){
                    check[i][j] = true;
                    canvas.drawBitmap(before2, i*before2.getWidth(), j*before2.getHeight(),null);
                    count=0;
                }
            }
        }
    }
}
