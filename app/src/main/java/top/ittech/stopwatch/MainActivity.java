package top.ittech.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int milliseconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState != null) {
            milliseconds = savedInstanceState.getInt("milliseconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(wasRunning)
            running = true;

    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        milliseconds = 0;
        running = false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("milliseconds", milliseconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);

    }

    public void runTimer(){
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int milis = milliseconds %100;
                int seconds = (milliseconds /100)%60;
                int minutes = (milliseconds /6000)%60;
                int hours = milliseconds /360000;



                String time = String.format(Locale.getDefault(), "%d:%02d:%02d.%02d", hours, minutes, seconds, milis);
                timeView.setText(time);
                if(running){
                    milliseconds +=5;
                }
                handler.postDelayed(this, 1);
            }
        });

    }
}