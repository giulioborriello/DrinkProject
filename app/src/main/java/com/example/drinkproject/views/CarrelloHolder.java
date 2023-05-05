package com.example.drinkproject.views;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;

public class CarrelloHolder extends RecyclerView.ViewHolder {
    public ImageView immagine;
    public TextView nome, descrizione, prezzo, quantita, totale;
    public Button aggiungiUnDrink, rimuoviUnDrink;

    public CarrelloHolder(@NonNull View itemView) {
        super(itemView);
        nome = (TextView) itemView.findViewById(R.id.nomeDrinkCarrello);
        descrizione = (TextView) itemView.findViewById(R.id.descrizioneDrinkCarrello);
        quantita = (TextView) itemView.findViewById(R.id.quantitaDrink);
        immagine = (ImageView) itemView.findViewById(R.id.immagineDrinkCarrello);
        aggiungiUnDrink = (Button) itemView.findViewById(R.id.bottonePiuCarrello);
        rimuoviUnDrink = (Button) itemView.findViewById(R.id.bottoneMenoCarrello);
        prezzo = (TextView) itemView.findViewById(R.id.prezzoDrinkCarrello);
        totale = (TextView) itemView.findViewById(R.id.totalCounter);
    }
}
