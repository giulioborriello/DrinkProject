package com.example.drinkproject.views;

public class RecyclerViewDrinkItem {
    public String  name, description, price;
    public int image;


    public RecyclerViewDrinkItem(String name, String description,String price, int image) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }
}
