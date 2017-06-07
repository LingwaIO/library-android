package com.sample.lingwaio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.lingwa.android.Lingwa;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "Splash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Lingwa.init(this, new Lingwa.OnInitialize() {
            @Override
            public void onInitializeSuccess() {
                Intent myIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(myIntent);
            }

            @Override
            public void onInitializeFail(String error) {
                Log.d(TAG, error);
            }
        });
    }
}
