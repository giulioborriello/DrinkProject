package com.example.drinkproject.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;

import java.util.List;

public class RecyclerVIewDrinkAdapter extends RecyclerView.Adapter<RecyclerViewDrinkHolder> {
    Context context;
    List<RecyclerViewDrinkItem> drinks;

    public RecyclerVIewDrinkAdapter(Context context, List<RecyclerViewDrinkItem> drinks) {
        this.context = context;
        this.drinks = drinks;
    }

    @NonNull
    @Override
    public RecyclerViewDrinkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewDrinkHolder(LayoutInflater.from(context).inflate(R.layout.drink_view,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewDrinkHolder holder, int position) {
        holder.nameText.setText(drinks.get(position).getName());
        holder.flavourText.setText(drinks.get(position).getFlavour());
        holder.recipeText.setText(drinks.get(position).getIngredients());
        holder.drinkImage.setImageResource(drinks.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }
}
