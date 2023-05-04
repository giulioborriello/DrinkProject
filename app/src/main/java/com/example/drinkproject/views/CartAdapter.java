package com.example.drinkproject.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;
import com.example.drinkproject.activities.OrderSummaryActivity;

import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import model.Drink;
import model.DrinkOrdine;

public class CartAdapter  extends RecyclerView.Adapter<CartHolder> {
    Context context;
    private List<DrinkOrdine> drinks;
    private final LayoutInflater inflater;

    public CartAdapter(Context context, List<DrinkOrdine> drinks) {
        this.context = context;
        this.drinks = drinks;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drink_view_summary,parent,false);
        return new CartHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        Drink drink = drinks.get(position).getDrink();

        String name = drink.getNome();
        String description = drink.getDescrizione();
        double price = drink.getPrezzo();
        int quantity = drinks.get(position).getQuantita();

        holder.nameTextCart.setText(name);
        holder.descriptionTextCart.setText(description);
        holder.drinkPriceCart.setText(String.valueOf(price*quantity));
        holder.drinkQuantityCart.setText(String.valueOf(quantity));
        holder.drinkImageCart.setImageResource(R.drawable.spritz);
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
