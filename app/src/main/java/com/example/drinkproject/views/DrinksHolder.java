package com.example.drinkproject.views;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;

public class DrinksHolder extends RecyclerView.ViewHolder {
    public ImageView drinkImage;
    public TextView nameText, descriptionText, drinkPrice;
    public Context context;
    public String id;


    public DrinksHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();

        itemView.setClickable(true);
        drinkPrice = itemView.findViewById(R.id.drinkPrice);
        drinkImage = itemView.findViewById(R.id.drinkImage);
        nameText = itemView.findViewById(R.id.drinkName);
        descriptionText = itemView.findViewById(R.id.drinkDescription);
    }
}
