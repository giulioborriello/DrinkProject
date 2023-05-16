package com.example.drinkproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.drinkproject.R;
import com.example.drinkproject.classiDiSupporto.ImpostazioniAttributi;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class ImpostazioniActivity extends AppCompatActivity {
    public final static String CHIAVE_STATO_SWITCH = "stato_switch";
    private SharedPreferences sharedPreferences;

    private ConstraintLayout layoutImpostazioni;
    private SwitchCompat accessibilitaSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_impostazioni);
        accessibilitaSwitch = findViewById(R.id.accessibilitaSwitch);
        layoutImpostazioni = findViewById(R.id.layoutImpostazioni);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        impostaAccessibilita();
    }

    private void impostaAccessibilita() {
        boolean statoSwitch = sharedPreferences.getBoolean(CHIAVE_STATO_SWITCH, false);

        accessibilitaSwitch.setChecked(statoSwitch);

        if(accessibilitaSwitch.isChecked()) {
            layoutImpostazioni.setBackgroundColor(getResources().getColor(android.R.color.white));
            setTextColorForAllViews(layoutImpostazioni, getResources().getColor(android.R.color.black));
        }

        accessibilitaSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
        if(isChecked){
            layoutImpostazioni.setBackgroundColor(getResources().getColor(android.R.color.white));
            setTextColorForAllViews(layoutImpostazioni, getResources().getColor(android.R.color.black));
        }
        else {
            layoutImpostazioni.setBackgroundColor(getResources().getColor(R.color.brick_red));
            setTextColorForAllViews(layoutImpostazioni, getResources().getColor(android.R.color.white));
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CHIAVE_STATO_SWITCH, isChecked);
        editor.apply();
    });
    }

    private void setTextColorForAllViews(ViewGroup viewGroup, int color) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);

            if (view instanceof ViewGroup) {
                setTextColorForAllViews((ViewGroup) view, color);
            } else if (view instanceof TextView) {
                ((TextView) view).setTextColor(color);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}