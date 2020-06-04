package com.example.androidlabs;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Switch sw;
    private CheckBox check;
    private Button button;
    private Snackbar snack;
    private static boolean locale = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);

        button = findViewById(R.id.button);
        button.setOnClickListener(v -> Toast.makeText(this, getResources().getString(R.string.toast_message), Toast.LENGTH_LONG).show());

        check = findViewById(R.id.checkBox);
        check.setOnCheckedChangeListener((view, isChecked) -> snackbarDisplay());

        sw = findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener((view, isChecked) -> updateSnackbarText());

        snack = makeSnackbar();

        ImageButton flag = findViewById(R.id.imageView);
        flag.setOnClickListener((v -> setLocale()));

    }

    private String widgetStatus(boolean bool) {
        return (bool) ? getResources().getString(R.string.status_on) : getResources().getString(R.string.status_off);
    }

    private void updateSnackbarText() {
        snack.setText(getResources().getString(R.string.snackbar_message) + widgetStatus(sw.isChecked()));
    }

    private void snackbarDisplay() {
        if (check.isChecked()) snack.show();
        else snack.dismiss();
    }

    private Snackbar makeSnackbar() {
        return Snackbar.make(button, getResources().getString(R.string.snackbar_message) + widgetStatus(sw.isChecked()), Snackbar.LENGTH_INDEFINITE).setAction(getResources().getString(R.string.button_undo), (view -> check.toggle()));
    }

    private void setLocale() {
        Locale myLocale;
        if (locale) {
            myLocale = new Locale("fr");
        } else {
            myLocale = new Locale("en");
        }
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        finish();
        startActivity(refresh);
        locale = !locale;
    }

}

