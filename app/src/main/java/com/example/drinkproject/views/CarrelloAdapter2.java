package com.example.drinkproject.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;
import com.example.drinkproject.classiDiSupporto.CaricatoreImmaginiCarrello;

import java.util.List;
import java.util.concurrent.Executor;

import controller.Controller;
import model.Drink;
import model.DrinkOrdine;

public class CarrelloAdapter2 {
   /* private Executor executor;
    private Controller controller;
    private List<DrinkOrdine> listaDeiDrinkOrdinati;
    private Context context;

    public CarrelloAdapter2() {
        controller = Controller.getInstance();
        listaDeiDrinkOrdinati = controller.getSummary();
    }


    @NonNull
    @Override
    public CarrelloHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.drink_view,parent,false);
        return new CarrelloHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CarrelloHolder holder, int position) {
        DrinkOrdine drinkOrdine = listaDeiDrinkOrdinati.get(position);
        Drink drink = drinkOrdine.getDrink();

        String name = drink.getNome();
        String description = drink.getDescrizione();
        String id = drink.getId();
        double price = drink.getPrezzo();
        int quantity = drinkOrdine.getQuantita();

        holder.nome.setText(name);
        holder.descrizione.setText(description);
        holder.prezzo.setText(String.valueOf(price*quantity));
        holder.quantita.setText(String.valueOf(quantity));
        holder.id = id;

        caricaImmagini(holder);


        holder.quantita.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                int realposition = holder.getAdapterPosition();
                if (realposition != RecyclerView.NO_POSITION) {
                    removeItem(realposition, id);
                }

            }
        });
    }

    public void removeItem(int position, String id) {
        controller.removeDrink(id);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaDeiDrinkOrdinati.size());
    }

    @Override
    public int getItemCount() {
        return listaDeiDrinkOrdinati.size();
    }


    private void caricaImmagini(@NonNull CarrelloHolder holder) {
        try {
            CaricatoreImmaginiCarrello caricatoreImmagini = new CaricatoreImmaginiCarrello(controller, context, holder);
            caricatoreImmagini.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    */
}
