package io.lingwa.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import io.lingwa.android.Lingwa;
import io.lingwaio.sample.R;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "Splash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final TextView textView = (TextView) findViewById(R.id.tv_splash);

        Lingwa.init(this, new Lingwa.OnInitialize() {
            @Override
            public void onInitializeSuccess() {
                Intent myIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(myIntent);
            }

            @Override
            public void onInitializeFail(String error) {
                textView.setText("Error: " + error);
                Log.d(TAG, error);
            }
        });
    }
}
