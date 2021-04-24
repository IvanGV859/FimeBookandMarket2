package com.example.fimebookandmarket2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class splash_screen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(splash_screen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

        Timer tiempo = new Timer();

        tiempo.schedule(tarea, 4000);

    }
}