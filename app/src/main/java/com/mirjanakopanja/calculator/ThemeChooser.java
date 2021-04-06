package com.mirjanakopanja.calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class ThemeChooser extends AppCompatActivity {

    private SwitchMaterial themeToggleSwitch;
    private static final String KEY = "options";
    private static final String DARK_THEME = "dark_theme";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences(KEY, MODE_PRIVATE);
        boolean useDarkTheme = preferences.getBoolean(DARK_THEME, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme_chooser_layout);

        themeToggleSwitch = findViewById(R.id.switchTheme);
        themeToggleSwitch.setChecked(useDarkTheme);

        themeToggleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            switchTheme(isChecked);

        });
    }

    private void switchTheme(boolean isChecked) {
        SharedPreferences.Editor editor = getSharedPreferences(KEY, MODE_PRIVATE).edit();
        editor.putBoolean(DARK_THEME, isChecked);
        editor.apply();

        Intent intent = new Intent();
        intent.putExtra(KEY, isChecked);
        setResult(RESULT_OK, intent);
        finish();
    }
}
