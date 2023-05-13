package com.example.drinkproject.views;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
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
    InputFilter filtroQuantitaMassima = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (input > 30) {
                return "";
            }
            return null;
        }
    };

    public DrinksHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        prezzo = itemView.findViewById(R.id.prezzoDrink);
        immagine = itemView.findViewById(R.id.immagineDrink);
        nome = itemView.findViewById(R.id.nomeDrink);
        descrizione = itemView.findViewById(R.id.descrizioneDrink);
        quantita = itemView.findViewById(R.id.quantitaDrinkCarrello);
        quantita.setFilters(new InputFilter[]{filtroQuantitaMassima});
        aggiungiUnDrink = itemView.findViewById(R.id.pulsantePiu);
        rimuoviUnDrink = itemView.findViewById(R.id.pulsanteMeno);
    }
}
