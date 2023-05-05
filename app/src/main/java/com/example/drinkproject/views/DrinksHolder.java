package com.example.drinkproject.views;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;

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
        prezzo = itemView.findViewById(R.id.drinkPrice);
        immagine = itemView.findViewById(R.id.drinkImage);
        nome = itemView.findViewById(R.id.drinkName);
        descrizione = itemView.findViewById(R.id.drinkDescription);
        quantita = itemView.findViewById(R.id.drinkQuantity);
        aggiungiUnDrink = itemView.findViewById(R.id.plusDrinkButton);
        rimuoviUnDrink = itemView.findViewById(R.id.minusDrinkButton);
    }
}
