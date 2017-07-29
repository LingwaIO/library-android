package io.lingwa.android.Models;

/**
 * Created by Christian on 14-Apr-17.
 */

public class Translation {

    private String Text;
    private String LanguageCode;

    public Translation(String text, String languageCode) {
        Text = text;
        LanguageCode = languageCode;
    }

    public String getText() {
        return Text;
    }

    public String getLanguageCode() {
        return LanguageCode;
    }
}
