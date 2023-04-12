package com.example.drinkproject.views;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;
import com.example.drinkproject.activities.OrderSummaryActivity;

import java.util.List;

public class RecyclerVIewDrinkAdapter extends RecyclerView.Adapter<RecyclerViewDrinkHolder> {
    Context context;
    private final List<RecyclerViewDrinkItem> drinks;
    private final LayoutInflater inflater;


    public RecyclerVIewDrinkAdapter(Context context, List<RecyclerViewDrinkItem> drinks) {
        this.context = context;
        this.drinks = drinks;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public RecyclerViewDrinkHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.drink_view,parent,false);
        return new RecyclerViewDrinkHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewDrinkHolder holder, int position) {
        holder.nameText.setText(drinks.get(position).name);
        holder.descriptionText.setText(drinks.get(position).description);
        holder.drinkImage.setImageResource(drinks.get(position).image);
        holder.drinkPrice.setText(drinks.get(position).price);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, OrderSummaryActivity.class);
            intent.putExtra("name", drinks.get(position).name);
            intent.putExtra("description", drinks.get(position).description);
            intent.putExtra("image", drinks.get(position).image);
            intent.putExtra("price", drinks.get(position).price);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }


    public interface OnClickListener {
        void onClick(int position, RecyclerViewDrinkItem drink);
    }


    @Override
    public int getItemCount() {
        return drinks.size();
    }


}
