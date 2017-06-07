package io.lingwa.android.Builders;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.Locale;

/**
 * Created by Christian on 13-May-17.
 */

public class LingwaConfiguration {

    private String projectCode;
    private String languageCode;
    private int expiryMinutes = 30;
    private boolean isDebug = false;

    public LingwaConfiguration(Context context){
        projectCode = getProjectKey(context);
        languageCode = Locale.getDefault().toString().replace("_", "-");
    }

    public String getProjectCode() {
        return projectCode;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public LingwaConfiguration setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
        return this;
    }

    public int getExpiryMinutes() {
        return expiryMinutes;
    }

    public LingwaConfiguration setExpiryMinutes(int expiryMinutes) {
        this.expiryMinutes = expiryMinutes;
        return this;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    private static String getProjectKey(Context context) {
        String TAG = "LingwaConfigure";
        String errorMessage = "Lingwa project code was not found in manifest";

        try{
            ApplicationInfo ai = context.getApplicationContext().getPackageManager().getApplicationInfo(
                    context.getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
            return (String) ai.metaData.get("LingwaProjectCode");
        } catch (NullPointerException e){
            Log.e(TAG, errorMessage);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, errorMessage);
        }
        return null;
    }
}
