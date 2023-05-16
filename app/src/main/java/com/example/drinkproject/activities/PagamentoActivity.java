package com.example.drinkproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkproject.R;
import com.example.drinkproject.classiDiSupporto.CreditCardTextWatcher;

import controller.Controller;

public class PagamentoActivity extends AppCompatActivity {
    private Controller controller = Controller.getInstance();
    private EditText numeroCarta, nomeProprietarioCarta, cognomeProprietarioCarta, cvvCarta;
    private Spinner mesiSpinner, anniSpinner;
    private View pagamentoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
        effettuaIlCollegamentoDelleViews();
        numeroCarta.addTextChangedListener(new CreditCardTextWatcher(numeroCarta));
        settaGliSpinner();
    }


    private void settaGliSpinner() {
        String[] months = new String[]{"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};

        ArrayAdapter<String> mesiAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        mesiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mesiSpinner.setAdapter(mesiAdapter);

        String[] anni = new String[]{"2024", "2025", "2026", "2027", "2028", "2029", "2030"};

        ArrayAdapter<String> anniAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, anni);
        anniAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        anniSpinner.setAdapter(anniAdapter);
    }


    private void effettuaIlCollegamentoDelleViews() {
        nomeProprietarioCarta = findViewById(R.id.nomeProprietarioCarta);
        cognomeProprietarioCarta = findViewById(R.id.cognomeProprietarioCarta);
        cvvCarta = findViewById(R.id.cvvCarta);
        numeroCarta = findViewById(R.id.numeroCarta);
        mesiSpinner = findViewById(R.id.meseScadenza);
        anniSpinner = findViewById(R.id.annoScadenza);
        pagamentoButton = findViewById(R.id.confermaDati);
    }


    @Override
    protected void onResume() {
        super.onResume();
        RelativeLayout pagamentoLayout = findViewById(R.id.pagamentoLayout);
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(ImpostazioniActivity.CHIAVE_STATO_SWITCH, false)) {
            settaIColori(pagamentoLayout, android.R.color.white, android.R.color.black);
        }
        else {
            settaIColori(pagamentoLayout, R.color.brick_red, android.R.color.white);
        }

        settaIListner();
    }


    private void settaIListner() {
        View pagamentoButton = findViewById(R.id.confermaDati);
        pagamentoButton.setOnClickListener(new View.OnClickListener() {
            final String nome = ((EditText) findViewById(R.id.nomeProprietarioCarta)).getText().toString();
            final String cognome = ((EditText) findViewById(R.id.cognomeProprietarioCarta)).getText().toString();
            final String numeroCarta = ((EditText) findViewById(R.id.numeroCarta)).getText().toString().replaceAll(" ", "");
            final String cvv = ((EditText) findViewById(R.id.cvvCarta)).getText().toString();
            final String dataScadenza = ((Spinner) findViewById(R.id.meseScadenza)).getSelectedItem().toString() + "/" + ((Spinner) findViewById(R.id.annoScadenza)).getSelectedItem().toString();

            @Override
            public void onClick(View v) {
                if (controller.effettuaPagamento(nome, cognome, numeroCarta, dataScadenza, cvv)) {
                    controller.svuotaCarrello();
                    Intent intent = new Intent(getApplicationContext(), DrinkActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Pagamento non effettuato", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void settaIColori(RelativeLayout pagamentoLayout, int brick_red, int white) {
        pagamentoLayout.setBackgroundColor(getResources().getColor(brick_red));
        cambiaIlColoreDiTutteLeViews(pagamentoLayout, getResources().getColor(white));
        numeroCarta.setBackgroundColor(getResources().getColor(brick_red));
        nomeProprietarioCarta.setBackgroundColor(getResources().getColor(brick_red));
        cognomeProprietarioCarta.setBackgroundColor(getResources().getColor(brick_red));
        cvvCarta.setBackgroundColor(getResources().getColor(brick_red));
        numeroCarta.setHintTextColor(getResources().getColor(white));
        nomeProprietarioCarta.setHintTextColor(getResources().getColor(white));
        cognomeProprietarioCarta.setHintTextColor(getResources().getColor(white));
        cvvCarta.setHintTextColor(getResources().getColor(white));
    }


    private void cambiaIlColoreDiTutteLeViews(ViewGroup viewGroup, int color) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);

            if (view instanceof ViewGroup) {
                cambiaIlColoreDiTutteLeViews((ViewGroup) view, color);
            } else if (view instanceof TextView) {
                ((TextView) view).setTextColor(color);
            }
        }
    }
}