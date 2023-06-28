package com.example.drinkproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

import java.util.concurrent.Executor;

import controller.Controller;

public class PagamentoActivity extends AppCompatActivity {
    private Controller controller;
    private Executor executor;
    private EditText numeroCarta, nomeProprietarioCarta, cognomeProprietarioCarta, cvvCarta;
    private Spinner mesiSpinner, anniSpinner;
    private View pagamentoButton, impostazioniPulsante;
    private TextView titoloPagamento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ottieniLaConnessione();
        setContentView(R.layout.activity_pagamento);
        effettuaIlCollegamentoDelleViews();
        numeroCarta.addTextChangedListener(new CreditCardTextWatcher(numeroCarta));
        settaGliSpinner();
    }


    private void ottieniLaConnessione() {
        executor = ContextCompat.getMainExecutor(this);
        Thread controllerThread = new Thread(() -> {
            try {
                controller = Controller.getInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        attivaIlThreadEAttendi(controllerThread);
    }


    private static void attivaIlThreadEAttendi(Thread thread) {
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        pagamentoButton = findViewById(R.id.pulsanteConfermaDati);
        impostazioniPulsante = findViewById(R.id.impostazionePulsantePagamento);
        titoloPagamento = findViewById(R.id.titoloPagamento);
    }


    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        settaIColori();
        settaIListner();
    }

    private void settaIColori() {
        RelativeLayout pagamentoLayout = findViewById(R.id.pagamentoLayout);
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(ImpostazioniActivity.CHIAVE_STATO_SWITCH, false)) {
            pagamentoLayout.setBackgroundColor(getResources().getColor(R.color.black));
            cambiaIlColoreDiTutteLeViews(pagamentoLayout, getResources().getColor(android.R.color.white));
            numeroCarta.setBackground(getResources().getDrawable(R.drawable.rectangle_edit_text_alto_contrasto));
            nomeProprietarioCarta.setBackground(getResources().getDrawable(R.drawable.rectangle_edit_text_alto_contrasto));
            cognomeProprietarioCarta.setBackground(getResources().getDrawable(R.drawable.rectangle_edit_text_alto_contrasto));
            cvvCarta.setBackground(getResources().getDrawable(R.drawable.rectangle_edit_text_alto_contrasto));

            mesiSpinner.setBackground(getResources().getDrawable(R.drawable.rectangle_spinner));
            anniSpinner.setBackground(getResources().getDrawable(R.drawable.rectangle_spinner));


        }
        else {
            pagamentoLayout.setBackgroundColor(getResources().getColor(R.color.brick_red));
            cambiaIlColoreDiTutteLeViews(pagamentoLayout, getResources().getColor(android.R.color.white));
            numeroCarta.setBackground(getResources().getDrawable(R.drawable.rectangle_edit_text));
            nomeProprietarioCarta.setBackground(getResources().getDrawable(R.drawable.rectangle_edit_text));
            cognomeProprietarioCarta.setBackground(getResources().getDrawable(R.drawable.rectangle_edit_text));
            cvvCarta.setBackground(getResources().getDrawable(R.drawable.rectangle_edit_text));
            mesiSpinner.setPrompt(getResources().getString(R.string.mesi_spinner));
            anniSpinner.setPrompt(getResources().getString(R.string.anni_spinner));
            anniSpinner.setPrompt("Seleziona l'anno");
        }
    }


    private void settaIListner() {
        View impostazioniPulsante = findViewById(R.id.impostazionePulsantePagamento);
        impostazioniPulsante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImpostazioniActivity.class);
                startActivity(intent);
            }
        });

        View pagamentoButton = findViewById(R.id.pulsanteConfermaDati);
        pagamentoButton.setOnClickListener(new View.OnClickListener() {
            final String nome = ((EditText) findViewById(R.id.nomeProprietarioCarta)).getText().toString();
            final String cognome = ((EditText) findViewById(R.id.cognomeProprietarioCarta)).getText().toString();
            final String numeroCarta = ((EditText) findViewById(R.id.numeroCarta)).getText().toString().replaceAll(" ", "");
            final String cvv = ((EditText) findViewById(R.id.cvvCarta)).getText().toString();
            final String dataScadenza = ((Spinner) findViewById(R.id.meseScadenza)).getSelectedItem().toString() + "/" + ((Spinner) findViewById(R.id.annoScadenza)).getSelectedItem().toString();

            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        controller.effettuaPagamento(nome, cognome, numeroCarta, dataScadenza, cvv);
                    }
                });
                attivaIlThreadEAttendi(thread);
                if (Controller.pagamentoEffettuatoConSuccesso) {
                    controller.svuotaCarrello();
                    Intent intent = new Intent(getApplicationContext(), DrinkActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Pagamento non effettuato", Toast.LENGTH_SHORT).show();
                }
            }
        });
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