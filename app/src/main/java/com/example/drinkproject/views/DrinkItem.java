package com.example.drinkproject.views;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class DrinkItem {
    public String  name, description, price;
    public int image;


    public DrinkItem(String name, String description, String price, int image) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }

}
