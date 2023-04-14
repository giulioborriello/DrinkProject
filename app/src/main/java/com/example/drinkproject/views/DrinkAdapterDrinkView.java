package com.example.drinkproject.views;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;
import com.example.drinkproject.activities.DrinkActivity;

import java.util.List;

public class DrinkAdapterDrinkView extends RecyclerView.Adapter<DrinkHolderDrinkView> {
    Context context;
    private final List<DrinkItem> drinks;
    private final LayoutInflater inflater;

    public DrinkAdapterDrinkView(Context context, List<DrinkItem> drinks) {
        this.context = context;
        this.drinks = drinks;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public DrinkHolderDrinkView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drink_view,parent,false);
        return new DrinkHolderDrinkView(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DrinkHolderDrinkView holder, int position) {
        String name = drinks.get(position).name;
        String description = drinks.get(position).description;
        String price = drinks.get(position).price;
        int image = drinks.get(position).image;

        holder.nameText.setText(name);
        holder.descriptionText.setText(description);
        holder.drinkImage.setImageResource(image);
        holder.drinkPrice.setText(price);

        DrinkItem drink = new DrinkItem(name, description, price, image);
        holder.itemView.setOnClickListener(view -> {
            DrinkActivity.DRINKSSELECTED.add(drink);
        });
    }


    public interface OnClickListener {
        void onClick(int position, DrinkItem drink);
    }


    @Override
    public int getItemCount() {
        return drinks.size();
    }


}
