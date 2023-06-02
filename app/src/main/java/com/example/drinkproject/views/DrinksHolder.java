package com.example.drinkproject.views;

import android.content.Context;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;
import com.example.drinkproject.classiDiSupporto.FiltroQuantitaMassima;
import com.example.drinkproject.classiDiSupporto.FiltroSoloNumeri;

public class DrinksHolder extends RecyclerView.ViewHolder {
    public ImageView immagine;
    public TextView nome, descrizione, prezzo;
    public Context context;
    public EditText quantita;
    public Button aggiungiUnDrink, rimuoviUnDrink;
    public String id;

    public DrinksHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        prezzo = itemView.findViewById(R.id.prezzoDrink);
        immagine = itemView.findViewById(R.id.immagineDrink);
        nome = itemView.findViewById(R.id.nomeDrink);
        descrizione = itemView.findViewById(R.id.descrizioneDrink);

        quantita = itemView.findViewById(R.id.quantitaDrinkCarrello);
        impostaFiltri();

        aggiungiUnDrink = itemView.findViewById(R.id.pulsantePiu);
        rimuoviUnDrink = itemView.findViewById(R.id.pulsanteMeno);
    }


    private void impostaFiltri() {
        InputFilter filtroQuantitaMassima = new FiltroQuantitaMassima();
        InputFilter filtroSoloNumeri = new FiltroSoloNumeri();
        InputFilter[] filters = new InputFilter[]{filtroQuantitaMassima, filtroSoloNumeri};
        quantita.setFilters(filters);
    }
}
