package com.example.drinkproject.views;

public class RecyclerViewDrinkItem {
    String  name, flavour, ingredients;
    int image;

    public RecyclerViewDrinkItem(String name, String flavour, String ingredients, int image) {
        this.name = name;
        this.flavour = flavour;
        this.ingredients = ingredients;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
