package com.example.fimebookandmarket2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SystemClock.sleep(2000);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.main_join_now_btn:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.main_login_btn:
                Intent intent1 = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
