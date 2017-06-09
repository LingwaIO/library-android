package io.lingwa.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.lingwa.android.AsyncTasks.DownloadTask;
import io.lingwa.android.Builders.LingwaConfiguration;
import io.lingwa.android.Models.Label;
import io.lingwa.android.Models.Translation;

/**
 * Created by Christian on 13-May-17.
 */

public class LingwaInstance {

    private static final String PREF_LABELS = "lingwa_labels";
    private static final String PREF_LAST_UPDATE_TIME = "lingwa_last_update_time";

    private Context context;
    private Lingwa.OnInitialize onInitialize;
    private LingwaConfiguration configuration;
    private List<Label> labels;
    private boolean isDownloading = false;

    public LingwaInstance(final Context context, final Lingwa.OnInitialize onInitialize){
        this.context = context;
        this.onInitialize = onInitialize;
        configuration = Lingwa.getConfiguration(context); //load preferences

        checkCacheAndLabels();
    }

    private void checkCacheAndLabels(){
        if(isCacheExpired() && !isDownloading){
            isDownloading = true;
            DownloadTask downloadTask = new DownloadTask(configuration.getProjectCode(), new DownloadTask.OnDownloadRequestCompleted() {
                @Override
                public void onRequestSuccessful(String result) {
                    isDownloading = false;

                    SharedPreferences sharedPref = Lingwa.getSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putLong(PREF_LAST_UPDATE_TIME, (new Date(System.currentTimeMillis())).getTime());
                    editor.putString(PREF_LABELS, result);
                    editor.commit();
                    logDebug("saved json response to storage");
                    getLabelsFromCache();
                }

                @Override
                public void onRequestError(String error) {
                    isDownloading = false;

                    if(onInitialize!=null){
                        onInitialize.onInitializeFail(error);
                    }
                }
            });
            downloadTask.execute();
        } else if(labels==null) {
            getLabelsFromCache();
        }
    }

    private boolean isCacheExpired() {
        Date now = new Date(System.currentTimeMillis());

        final long ONE_MINUTE_IN_MILLIS = 60000; //milliseconds
        Date nextCheck = new Date(Lingwa.getSharedPreferences(context).getLong(PREF_LAST_UPDATE_TIME, 0) + (configuration.getExpiryMinutes() * ONE_MINUTE_IN_MILLIS));

        if(nextCheck.before(now)){
            return true;
        }
        return false;
    }

    private void getLabelsFromCache() {
        labels = new ArrayList<>();
        try {
            SharedPreferences sharedPref = Lingwa.getSharedPreferences(context);
            String labelsString = sharedPref.getString(PREF_LABELS, "");

            if(labelsString.isEmpty()){
                throw new Exception("JSON format invalid or empty");
            }

            JSONArray array = new JSONArray(labelsString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jLabel = array.getJSONObject(i);
                JSONArray jTranslations = jLabel.getJSONArray("Translations");

                List<Translation> translations = new ArrayList<>();
                for (int j = 0; j < jTranslations.length(); j++) {
                    JSONObject jTranslation = jTranslations.getJSONObject(j);
                    Translation translation = new Translation(jTranslation.getString("Text"), jTranslation.getString("LanguageCode"));
                    translations.add(translation);
                }

                Label label = new Label(jLabel.getString("LabelName"), translations);
                labels.add(label);
            }
            logDebug("converted from json to objects");
            if(onInitialize != null){
                onInitialize.onInitializeSuccess();
            }
        } catch (Exception e) {
            if(onInitialize != null){
                onInitialize.onInitializeFail(e.getMessage());
            }
        }

        onInitialize = null;
    }

    public String getTranslation(String labelName, String languageCode){
        checkCacheAndLabels();

        if(languageCode==null){
            languageCode = configuration.getLanguageCode(); //use the one set on init
        }

        for(Label label : (labels!=null? labels : new ArrayList<Label>())){
            if(label.getLabelName().equals(labelName)){
                String defaultTrans = null;
                for(Translation translation : label.getTranslations()){
                    if(translation.getLanguageCode().equals("DEFAULT")){
                        defaultTrans = translation.getText();
                    }else if(translation.getLanguageCode().equals(languageCode)){
                        return translation.getText();
                    }
                }
                if(defaultTrans!=null){
                    logDebug("returning default translation");
                    return defaultTrans;
                }
            }
        }

        //try looking in local resources
        logDebug("looking in local");
        return getStringResourceByName(labelName);
    }

    private String getStringResourceByName(String labelName) {
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(labelName, "string", packageName);
        String ans = null;
        try{
            ans = context.getString(resId);
        }catch (Resources.NotFoundException e){
        }finally {
            return ans;
        }
    }

    private void logDebug(String log){
        if(configuration.isDebug()){
            Log.d("LingwaInstance", log);
        }
    }
}
