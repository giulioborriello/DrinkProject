package com.example.drinkproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.drinkproject.R;

import java.util.Calendar;

public class PagamentoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagamento);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EditText dataScadenza = (EditText) findViewById(R.id.scadenzaCarta);
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
               //    dataScadenza.setText(String.format(());
            }
        };
    }
}