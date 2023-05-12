package com.example.drinkproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.drinkproject.R;
import com.example.drinkproject.otherClasses.CreditCardTextWatcher;

import controller.Controller;

public class PagamentoActivity extends AppCompatActivity {
    private Controller controller = Controller.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);

        EditText numeroCarta = findViewById(R.id.numeroCarta);
        numeroCarta.addTextChangedListener(new CreditCardTextWatcher(numeroCarta));

        String[] months = new String[]{"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};

        ArrayAdapter<String> mesiAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, months);
        mesiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner mesiSpinner = findViewById(R.id.meseScadenza);
        mesiSpinner.setAdapter(mesiAdapter);

        String[] anni = new String[]{"2024", "2025", "2026", "2027", "2028", "2029", "2030"};

        ArrayAdapter<String> anniAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, anni);
        anniAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner anniSpinner = findViewById(R.id.annoScadenza);
        anniSpinner.setAdapter(anniAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
}