package io.lingwa.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import io.lingwa.android.Lingwa;
import io.lingwaio.sample.R;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvWelcome = (TextView) findViewById(R.id.tv_welcome);
        Button btnChangeLang = (Button) findViewById(R.id.btn_change_lang);

        Lingwa.with(this)
                .label("welcome")
                .into(tvWelcome);

        Lingwa.getConfiguration(this)
                .setLanguageCode("fr-FR");

        btnChangeLang.setText(Lingwa.with(this).label("change_language").getText());
    }
}
