package com.example.reflexless;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/*
Brake statment for stop the game
*/

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout screen;
    private boolean gamestatue,displayed;
    private ImageView lights;
    private TextView start, highestscoretv;
    private Runnable runnable;
    private Handler handler;
    private long resulttime,starttime,endtime,highscore=Long.MAX_VALUE;
    private static final long MIN = 1000,MAX=5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen       = findViewById(R.id.screen);
        lights       = findViewById(R.id.lights);
        start        = findViewById(R.id.start);
        highestscoretv = findViewById(R.id.highestscore);

        screen.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                   if(gamestatue){
                       if(displayed){
                            endtime=System.currentTimeMillis();
                            resulttime=endtime-starttime;
                            handler.removeCallbacks(task);
                            if(resulttime>1000) {
                                start.setText(""+resulttime);
                            }else start.setText("0,"+resulttime);

                            if(resulttime<highscore){
                                highscore=resulttime;
                            }
                            if(highscore>1000){
                                highestscoretv.setText("Highest Score= "+highscore);
                            }else highestscoretv.setText("Highest Score= 0,"+highscore);

                            lights.setColorFilter(getResources().getColor(R.color.kıpkırmızı));
                            gamestatue=false;
                            displayed=false;
                       }else {
                           start.setText("Wait for lights color change");
                           handler.removeCallbacks(task);
                           gamestatue=false;
                           displayed=false;
                       }
                   }else {
                       gamestatue=true;
                       displayed=false;
                       start.setText("Click screen when colors change");
                       handler = new Handler();
                       handler.postDelayed(task,randomnumbergenerator());
                   }
            }
        });
    }

    private Runnable task = new Runnable() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lights.setColorFilter(getResources().getColor(R.color.yeşiliş));
                    displayed=true;
                    starttime=System.currentTimeMillis();
                }
            });
        }
    };

    private long randomnumbergenerator(){
        return (long) ((long)MIN+Math.random()*(MAX-MIN));
    }
}