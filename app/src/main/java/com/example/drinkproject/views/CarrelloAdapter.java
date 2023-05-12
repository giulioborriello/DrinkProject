package com.example.drinkproject.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;
import com.example.drinkproject.otherClasses.SwipeToDeleteCallback;

import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import model.Drink;
import model.DrinkOrdine;

public class CarrelloAdapter extends RecyclerView.Adapter<CarrelloHolder> {
    Context context;
    private final LayoutInflater inflater;
    private final Controller controller = Controller.getInstance();
    private final TextView totale;
    private final RecyclerView recyclerView;
    private List<DrinkOrdine> drinkOrdines = controller.getSummary();

    public CarrelloAdapter(Context context, TextView totale, RecyclerView recyclerView) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.totale = totale;
        this.recyclerView = recyclerView;
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this){
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                DrinkOrdine drinkOrdine = drinkOrdines.get(position);
                Drink drink = drinkOrdine.getDrink();
                if (drink == null) {
                    return;
                }
                controller.removeDrink(drink.getId());
                controller.updateDrink(drink.getId(), 0);
                removeItem(position);
                totale.setText(controller.getPrezzoTotale());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    @NonNull
    @Override
    public CarrelloHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drink_view,parent,false);
        return new CarrelloHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CarrelloHolder holder, int position) {
        DrinkOrdine drinkOrdine = drinkOrdines.get(position);
        Drink drink =  drinkOrdine.getDrink();

        if (drink == null) {
            return;
        }

        String name = drink.getNome();
        String description = drink.getDescrizione();
        double price = drink.getPrezzo();
        int quantity = drinkOrdine.getQuantita();

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

                    controller.updateDrink(drink.getId(), newQuantity);
                    totale.setText(controller.getPrezzoTotale());
                } else if (newQuantity == 1) {
                    controller.removeDrink(drink.getId());
                    controller.updateDrink(drink.getId(), 0);
                    if (getItemCount() == 1) {
                        removeItem(position);
                        totale.setText("0");
                    } else {
                        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this);
                        swipeToDeleteCallback.onSwiped(holder.itemView, ItemTouchHelper.RIGHT);
                    }
                    removeItem(position);
                    totale.setText("0");
                }
            }
        });
    }


    public void removeItem(int position) {
        notifyItemRemoved(position);
    }


    @Override
    public int getItemCount() {
        return drinkOrdines.size();
    }
}
