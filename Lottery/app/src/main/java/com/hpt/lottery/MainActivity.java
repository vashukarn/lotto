package com.hpt.lottery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_WEEK);
//        final int day = Calendar.SUNDAY;

            int SPLASH_TIME_OUT = 3000;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (day == Calendar.SUNDAY) {
                        startActivity(new Intent(MainActivity.this, SundayClosed.class));
                    }
                    else{
                        startActivity(new Intent(MainActivity.this, UserLogin.class));
                    }
//                    startActivity(new Intent(MainActivity.this, UserLogin.class));
                }
            }, SPLASH_TIME_OUT);
        }
}