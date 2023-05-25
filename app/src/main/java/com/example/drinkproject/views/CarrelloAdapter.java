package com.example.drinkproject.views;

import android.app.Notification;
import android.content.Context;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;
import com.example.drinkproject.activities.ImpostazioniActivity;
import com.example.drinkproject.classiDiSupporto.CaricatoreImmaginiCarrello;
import com.example.drinkproject.classiDiSupporto.CaricatoreImmaginiLista;
import com.example.drinkproject.classiDiSupporto.SwipeToDeleteCallback;

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
        holder.id = drink.getId();
        caricaImmagini(holder);
        if(PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getBoolean(ImpostazioniActivity.CHIAVE_STATO_SWITCH, false)){
            holder.itemView.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            holder.nome.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.descrizione.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.prezzo.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.quantita.setTextColor(context.getResources().getColor(android.R.color.black));
        }

        holder.aggiungiUnDrink.setOnClickListener(v -> {
            if(holder.quantita.getText().toString().equals("")) {
                holder.quantita.setText("1");
            }
            else {
                int newQuantity = Integer.parseInt(holder.quantita.getText().toString());
                newQuantity++;
                holder.quantita.setText(String.valueOf(newQuantity));
                holder.prezzo.setText(String.valueOf(price * newQuantity));

                //controller.updateDrink(drink.getId(), newQuantity);
                //totale.setText(controller.getPrezzoTotale());
            }
        });

        holder.rimuoviUnDrink.setOnClickListener(v -> {
            String quantitaInput = holder.quantita.getText().toString();
            if(!quantitaInput.equals("")) {
                int newQuantity = Integer.parseInt(quantitaInput);
                if (newQuantity >= 1) {
                    newQuantity--;
                    holder.quantita.setText(String.valueOf(newQuantity));
                    holder.prezzo.setText(String.valueOf(price*newQuantity));

                    //controller.updateDrink(drink.getId(), newQuantity);
                    //totale.setText(controller.getPrezzoTotale());
                }
                else if (newQuantity == 1) {
                    controller.removeDrink(drink.getId());
                    //controller.updateDrink(drink.getId(), 0);
                    //removeItem(position);
                }
            }
        });

        holder.quantita.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s!=null && !s.toString().equals("0") && !s.toString().equals("")) {
                    controller.updateDrink(drinkOrdines.get(position).getDrink().getId(), Integer.parseInt(s.toString()));
                    totale.setText(controller.getPrezzoTotale());
                }
                else if (s != null && s.toString().equals("0")) {
                    controller.removeDrink(drink.getId());
                    controller.updateDrink(drink.getId(), 0);
                    removeItem(position);
                    totale.setText(controller.getPrezzoTotale());
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


    private void caricaImmagini(@NonNull CarrelloHolder holder) {
        try {
            CaricatoreImmaginiCarrello caricatoreImmagini = new CaricatoreImmaginiCarrello(controller, context, holder);
            caricatoreImmagini.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
