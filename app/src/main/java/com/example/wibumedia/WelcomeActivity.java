package com.example.wibumedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

class Delay {

    public interface DelayCallback {
        void afterDelay();
    }

    public static void delay(int secs, final DelayCallback delayCallback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, secs * 1000); // afterDelay will be executed after (secs*1000) milliseconds.
    }
}

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Delay.delay(2, new Delay.DelayCallback() {
            @Override
            public void afterDelay() {
                Intent intent = new Intent(WelcomeActivity.this, FragmentReplaceActivity.class);
                startActivity(intent);
            }
        });
    }
}