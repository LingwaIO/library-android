package io.lingwa.android.Builders;

import android.content.Context;
import android.widget.TextView;

import io.lingwa.android.Lingwa;
import io.lingwa.android.LingwaInstance;

/**
 * Created by Christian on 13-May-17.
 */

public class LingwaRequestBuilder {

    private LingwaInstance instance;
    private String languageCode;
    private String labelName;

    public LingwaRequestBuilder(Context context){
        this.instance = Lingwa.getInstance(context);
    }

    public LingwaRequestBuilder languageCode(String languageCode) {
        this.languageCode = languageCode;
        return this;
    }

    public LingwaRequestBuilder label(String labelName) {
        this.labelName = labelName;
        return this;
    }

    public void into(TextView textView){
        if(textView != null){
            textView.setText(getText());
        }
    }

    public String getText() {
        String text = instance.getTranslation(labelName, languageCode);
        if(text==null){
            text = "";
        }
        return text;
    }
}
