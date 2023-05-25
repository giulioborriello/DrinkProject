package com.example.drinkproject.views;

import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;
import com.example.drinkproject.classiDiSupporto.FiltroQuantitaMassima;

public class CarrelloHolder extends RecyclerView.ViewHolder {
    public ImageView immagine;
    public TextView nome, descrizione, prezzo, quantita, totale;
    public Button aggiungiUnDrink, rimuoviUnDrink;
    public String id;
    public CarrelloHolder(@NonNull View itemView) {
        super(itemView);
        nome = (TextView) itemView.findViewById(R.id.nomeDrink);
        descrizione = (TextView) itemView.findViewById(R.id.descrizioneDrink);

        quantita = (TextView) itemView.findViewById(R.id.quantitaDrinkCarrello);
        impostaFiltroQuantitaMassima();

        immagine = (ImageView) itemView.findViewById(R.id.immagineDrink);
        aggiungiUnDrink = (Button) itemView.findViewById(R.id.pulsantePiu);
        rimuoviUnDrink = (Button) itemView.findViewById(R.id.pulsanteMeno);
        prezzo = (TextView) itemView.findViewById(R.id.prezzoDrink);
        totale = (TextView) itemView.findViewById(R.id.totalCounter);
    }

    private void impostaFiltroQuantitaMassima() {
        InputFilter filtroQuantitaMassima = new FiltroQuantitaMassima();
        quantita.setFilters(new InputFilter[]{filtroQuantitaMassima});
    }
}
