package com.example.drinkproject.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;

public class CartHolder extends RecyclerView.ViewHolder {
    public ImageView drinkImageCart;
    public TextView nameTextCart, descriptionTextCart, drinkPriceCart, drinkQuantityCart;

    public CartHolder(@NonNull View itemView) {
        super(itemView);
        nameTextCart = (TextView) itemView.findViewById(R.id.drinkNameOrderSummary);
        descriptionTextCart = (TextView) itemView.findViewById(R.id.drinkDescriptionOrderSummary);
        drinkPriceCart = (TextView) itemView.findViewById(R.id.totalPriceOrderSummary);
        drinkQuantityCart = (TextView) itemView.findViewById(R.id.drinkQuantityOrderSummary);
        drinkImageCart = (ImageView) itemView.findViewById(R.id.drinkImageOrderSummary);
    }
}
