package com.example.drinkproject.views;

import android.content.Context;
import android.service.controls.Control;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;

import java.util.List;

import controller.Controller;
import model.Drink;
import model.DrinkOrdine;

public class CarrelloAdapter extends RecyclerView.Adapter<CarrelloHolder> {
    Context context;
    private List<DrinkOrdine> drinks;
    private final LayoutInflater inflater;
    private Controller controller = Controller.getInstance();

    public CarrelloAdapter(Context context, List<DrinkOrdine> drinks) {
        this.context = context;
        this.drinks = drinks;
        this.inflater = LayoutInflater.from(context);
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
                String nuovoPrezzo = String.valueOf(price * newQuantity);
                holder.prezzo.setText(nuovoPrezzo);
                controller.updateDrink(drink.getId(), newQuantity);
            }
        });

        holder.rimuoviUnDrink.setOnClickListener(v -> {
            if(!holder.quantita.getText().toString().equals("")) {
                int newQuantity = Integer.parseInt(holder.quantita.getText().toString());
                if (newQuantity > 1) {
                    newQuantity--;
                    holder.quantita.setText(String.valueOf(newQuantity));
                    holder.prezzo.setText(String.valueOf(price*newQuantity));
                    controller.updateDrink(drink.getId(), newQuantity);
                } else if (newQuantity == 1) {
                    drinks.remove(position);
                    controller.updateDrink(drink.getId(), 0);
                    notifyItemRemoved(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return drinks.size();
    }


    public void removeItem(int position) {
        drinks.remove(position);
        notifyItemRemoved(position);
    }


    public void clear() {
        int size = drinks.size();
        drinks.clear();
        notifyItemRangeRemoved(0, size);
    }





}
