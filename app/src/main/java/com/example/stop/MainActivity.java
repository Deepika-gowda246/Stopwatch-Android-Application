package com.example.stop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button startbtn,pausebtn,resetbtn,lapbtn;
    TextView laps_show,timerTxt,laphead;
    Handler handler=new Handler();
    long startTime=0L,timeMilli=0L,timeSwapbuff=0L,updateTime=0L;
    LinearLayout container;
    int count=1;

    Runnable updateTimeThread=new Runnable() {
        @Override
        public void run() {
            timeMilli= SystemClock.uptimeMillis()-startTime;
            updateTime=timeSwapbuff+timeMilli;
            int secs=(int)(updateTime/1000);
            int mins=secs/60;
            secs%=60;
            int milliseconds=(int)(updateTime%1000);
            timerTxt.setText(String.format("%02d",mins)+":"+String.format("%02d",secs)+":"+ String.format("%03d",milliseconds));
            handler.postDelayed(this,10);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startbtn=(Button)findViewById(R.id.startBtn);
        pausebtn=(Button)findViewById(R.id.pauseBtn);
        resetbtn=(Button)findViewById(R.id.reset_btn);
        lapbtn=(Button)findViewById(R.id.lap_btn);
       // laps_show=(TextView) findViewById(R.id.tv_lap);
        timerTxt=(TextView) findViewById(R.id.timertxt);
        laphead=(TextView)findViewById(R.id.lapheading) ;
        container=(LinearLayout) findViewById(R.id.container);

        //chronometer=(Chronometer)findViewById(R.id.Chronometer);

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // chronometer.setBase(SystemClock.elapsedRealtime()+stopTime);
               // chronometer.start();
                startTime=SystemClock.uptimeMillis();
                handler.postDelayed(updateTimeThread,10);
                startbtn.setVisibility(View.GONE);
                pausebtn.setVisibility(View.VISIBLE);
            }
        });
        pausebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stopTime=chronometer.getBase()-SystemClock.elapsedRealtime();
                //chronometer.stop();
                timeSwapbuff+=timeMilli;
                handler.removeCallbacks(updateTimeThread);
                timeMilli=0L;
                startbtn.setVisibility(View.VISIBLE);
                pausebtn.setVisibility(View.GONE);
            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=1;
//                chronometer.setBase(SystemClock.elapsedRealtime());
//                stopTime=0;
//                chronometer.stop();
                startbtn.setVisibility(View.VISIBLE);
                pausebtn.setVisibility(View.GONE);
                timerTxt.setText(String.valueOf(""));

                startTime=0L;timeMilli=0L;timeSwapbuff=0L;updateTime=0L;
                startTime=SystemClock.uptimeMillis();
                handler.postDelayed(updateTimeThread,0);
                handler.removeCallbacks(updateTimeThread);
                container.removeAllViews();


            }
        });

        lapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               // String strlap = chronometer.getText().toString();
               // laps_show.setText(strlap);
                laphead.isShown();
                LayoutInflater inflater=(LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView=inflater.inflate(R.layout.row,null);
                    if( timerTxt.getText()!="") {
                        TextView txtval = (TextView) addView.findViewById(R.id.txtContent);

                        txtval.setText("Lap" + count + ":   " + timerTxt.getText());
                        container.addView(addView);
                        count = count + 1;
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Start the timer!!!", Toast.LENGTH_SHORT).show();
                    }




            }
        });


    }
}