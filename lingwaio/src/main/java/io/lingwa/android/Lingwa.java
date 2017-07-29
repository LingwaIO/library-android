package io.lingwa.android;

import android.content.Context;
import android.content.SharedPreferences;

import io.lingwa.android.Builders.LingwaConfiguration;
import io.lingwa.android.Builders.LingwaRequestBuilder;

/**
 * Created by Christian on 13-May-17.
 */

public class Lingwa {

    private static LingwaInstance ourInstance;
    private static LingwaConfiguration configurationInstance;

    public static LingwaInstance getInstance(Context context) {
        return getInstance(context, null);
    }

    private static LingwaInstance getInstance(Context context, OnInitialize onInitialize) {
        if(ourInstance == null){
            ourInstance = new LingwaInstance(context, onInitialize);
        }
        return ourInstance;
    }

    public static LingwaConfiguration getConfiguration(Context context){
        if(configurationInstance == null){
            configurationInstance = new LingwaConfiguration(context);
        }
        return configurationInstance;
    }

    public static void init(Context context, OnInitialize onInitialize) {
        getInstance(context.getApplicationContext(), onInitialize);
    }

    public static LingwaRequestBuilder with(Context context) {
        if(ourInstance == null){ //Lingwa was not initialised
            throw new RuntimeException("Lingwa was not initialised");
        }
        return new LingwaRequestBuilder(context.getApplicationContext());
    }

    public interface OnInitialize {
        void onInitializeSuccess();
        void onInitializeFail(String error);
    }
}
