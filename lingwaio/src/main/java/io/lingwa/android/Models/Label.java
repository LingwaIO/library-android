package io.lingwa.android.Models;

import java.util.List;

/**
 * Created by Christian on 14-Apr-17.
 */

public class Label {

    private String LabelName;
    private List<Translation> Translations;

    public Label(String labelName, List<Translation> translations) {
        LabelName = labelName;
        this.Translations = translations;
    }

    public String getLabelName() {
        return LabelName;
    }

    public List<Translation> getTranslations() {
        return Translations;
    }
}
