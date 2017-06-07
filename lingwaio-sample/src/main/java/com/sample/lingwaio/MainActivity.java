package com.sample.lingwaio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import io.lingwa.android.Lingwa;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.main_text);
        Lingwa.with(this)
                .label("welcome")
                .into(textView);

        Log.d(TAG, Lingwa.with(this).label("change_language").getText());
    }
}
