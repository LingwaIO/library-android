package com.sample.lingwaio;

import android.app.Application;

import io.lingwa.android.Lingwa;

/**
 * Created by ccastaldi on 30-May-17.
 */

public class MyCustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Lingwa.getConfiguration(this)
                .setExpiryMinutes(1)
                .setDebug(true);
    }
}
