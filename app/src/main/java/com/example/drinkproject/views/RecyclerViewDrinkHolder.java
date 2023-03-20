package com.example.drinkproject.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drinkproject.R;

public class RecyclerViewDrinkHolder extends RecyclerView.ViewHolder {
    ImageView drinkImage;
    TextView nameText, flavourText, recipeText;
    public RecyclerViewDrinkHolder(@NonNull View itemView) {
        super(itemView);
        drinkImage = itemView.findViewById(R.id.drinkImageView);
        nameText = itemView.findViewById(R.id.drinkName);
        flavourText = itemView.findViewById(R.id.drinkFlavour);
        recipeText = itemView.findViewById(R.id.drinkRecipe);
    }
}
