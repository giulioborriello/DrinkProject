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
    public TextView nome, descrizione, prezzo, quantita;
    public Button aggiungiUnDrink, rimuoviUnDrink;

    public CarrelloHolder(@NonNull View itemView) {
        super(itemView);
        nome = (TextView) itemView.findViewById(R.id.drinkNameOrderSummary);
        descrizione = (TextView) itemView.findViewById(R.id.drinkDescriptionOrderSummary);
        prezzo = (TextView) itemView.findViewById(R.id.totalPriceOrderSummary);
        quantita = (TextView) itemView.findViewById(R.id.drinkQuantityOrderSummary);
        immagine = (ImageView) itemView.findViewById(R.id.drinkImageOrderSummary);
        aggiungiUnDrink = (Button) itemView.findViewById(R.id.plusDrinkOrderSummaryButton);
        rimuoviUnDrink = (Button) itemView.findViewById(R.id.minusDrinkOrderSummaryButton);
    }
}
