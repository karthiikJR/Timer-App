package com.example.timer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //all the variable declaration maxVal for maximum time, minVal for minimum time of the timer
    // min sec is used to set the timer in the TextView
    //progressTime is used to keep track of progress
    //Btn Click and alphaStrt and alphaEnd is for changing the button visibility of START and END button
    private int maxVal = 1200;
    private int minVal = 10;
    private int defaultVal = 60;
    private String min="00",sec="00";
    private long progressTime = defaultVal,timeRemaining = defaultVal;
    private boolean btnClick = true;
    private int alphaStrt = 1,alphaEnd = 0;

    //declaration of components
    SeekBar timerBar;
    TextView timerView;
    Button startBtn;
    Button endBtn;
    CountDownTimer timerToDisplay;
    MediaPlayer mediaPlayer;
    MediaPlayer mediaPlayer1;



    //button visibility function
    public void btnClicked() {
        if(btnClick == false) {
            btnClick = true;
            alphaStrt = 1;
            alphaEnd = 0;
        }
        else {
            btnClick = false;
            alphaStrt = 0;
            alphaEnd = 1;
        }
        startBtn.animate().alpha(alphaStrt);
        startBtn.setClickable(btnClick);
        endBtn.animate().alpha(alphaEnd);
        endBtn.setClickable(!btnClick);
        timerBar.setEnabled(btnClick);
    }

    public void startTimerBtn(View view) {
        btnClicked();
        timerToDisplay = new CountDownTimer(timeRemaining*1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mediaPlayer1 = MediaPlayer.create(MainActivity.this, R.raw.tick_sound);
                    mediaPlayer1.start();
                    timeRemaining--;
                    calculateTime(timeRemaining);
                    timerView.setText(min + ":" + sec);
                    timerBar.setProgress((int) timeRemaining);
                }

                @Override
                public void onFinish() {
                    Log.i("Timer Done", "Finished");
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.rick_roll);
                    mediaPlayer.start();
                    mediaPlayer1.stop();

                }
            }.start();

    }

    public void endTimer(View view) {
        btnClicked();
        if(timerToDisplay!=null){
            timerToDisplay.cancel();
        }
        if(mediaPlayer!=null) {
            mediaPlayer.stop();
        }
        if (mediaPlayer1!=null)
            mediaPlayer1.stop();
        timerBar.setProgress(defaultVal);
        calculateTime(defaultVal);
        timerView.setText(min+":"+sec);
        timeRemaining = defaultVal;
    }

    //to calculate the time where the seekbar has time in the form of seconds and we convert it to min:sec
    public void calculateTime(long time) {
        long localMin = time/60;
        long localSec = time%60;

        min = String.valueOf(localMin);

        if(localSec<10) {
            sec = "0"+String.valueOf(localSec);
        }else
            sec = String.valueOf(localSec);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerView = findViewById(R.id.tvTime);
        timerBar = findViewById(R.id.seekBar);
        startBtn = findViewById(R.id.btnStart);
        endBtn = findViewById(R.id.btnEnd);

        timerBar.setMax(maxVal);
        timerBar.setMin(minVal);

        timerBar.setProgress(defaultVal);
        calculateTime((long) defaultVal);
        timerView.setText(min+":"+sec);

        timerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //progressTime = progress;
                timeRemaining = progress;
                calculateTime(progress);
                timerView.setText(min+":"+sec);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}