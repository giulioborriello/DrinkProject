package com.example.drinkproject.views;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;

public class DrinksHolder extends RecyclerView.ViewHolder {
    public ImageView immagine;
    public TextView nome, descrizione, prezzo, quantita;
    public Context context;
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

        aggiungiUnDrink = itemView.findViewById(R.id.pulsantePiu);
        rimuoviUnDrink = itemView.findViewById(R.id.pulsanteMeno);
    }
}
