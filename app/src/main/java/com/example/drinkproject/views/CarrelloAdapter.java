package com.example.drinkproject.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;

import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import model.Drink;
import model.DrinkOrdine;

public class CarrelloAdapter extends RecyclerView.Adapter<CarrelloHolder> {
    Context context;
    private final List<DrinkOrdine> drinks;
    private final LayoutInflater inflater;
    private final Controller controller = Controller.getInstance();
    private final TextView totale;
    private final DrinksHolder drinksHolder;

    public CarrelloAdapter(Context context, List<DrinkOrdine> drinks, TextView totale,  DrinksHolder totaleSchermataDrink) {
        this.context = context;
        this.drinks = drinks;
        this.inflater = LayoutInflater.from(context);
        this.totale = totale;
        this.drinksHolder = totaleSchermataDrink;
    }


    @NonNull
    @Override
    public CarrelloHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drink_view_carrello,parent,false);

        return new CarrelloHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CarrelloHolder holder, int position) {
        Drink drink = drinks.get(position).getDrink();
        if (drink == null) {
            return;
        }
        String name = drink.getNome();
        String description = drink.getDescrizione();
        double price = drink.getPrezzo();
        int quantity = drinks.get(position).getQuantita();

        holder.nome.setText(name);
        holder.descrizione.setText(description);
        holder.prezzo.setText(String.valueOf(price*quantity));
        holder.quantita.setText(String.valueOf(quantity));
        holder.immagine.setImageResource(R.drawable.spritz);

        holder.aggiungiUnDrink.setOnClickListener(v -> {
            if(holder.quantita.getText().toString().equals("")) {
                holder.quantita.setText("1");
            } else {
                int newQuantity = Integer.parseInt(holder.quantita.getText().toString());
                newQuantity++;
                holder.quantita.setText(String.valueOf(newQuantity));
                drinksHolder.quantita.setText(String.valueOf(newQuantity));

                String nuovoPrezzo = String.valueOf(price * newQuantity);
                holder.prezzo.setText(nuovoPrezzo);

                controller.updateDrink(drink.getId(), newQuantity);
                totale.setText(controller.getPrezzoTotale());
            }
        });

        holder.rimuoviUnDrink.setOnClickListener(v -> {
            if(!holder.quantita.getText().toString().equals("")) {
                int newQuantity = Integer.parseInt(holder.quantita.getText().toString());
                if (newQuantity > 1) {
                    newQuantity--;
                    holder.quantita.setText(String.valueOf(newQuantity));
                    holder.prezzo.setText(String.valueOf(price*newQuantity));
                    drinksHolder.quantita.setText(String.valueOf(newQuantity));

                    controller.updateDrink(drink.getId(), newQuantity);
                    totale.setText(controller.getPrezzoTotale());
                } else if (newQuantity == 1) {
                    drinks.remove(position);
                    controller.updateDrink(drink.getId(), 0);
                    drinksHolder.quantita.setText("");

                    notifyItemRemoved(position);
                    totale.setText("0");
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return drinks.size();
    }


    public interface OnDataPass {
        void onDataPass(ArrayList<String> data);
    }

}
