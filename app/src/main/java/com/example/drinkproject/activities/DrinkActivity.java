package com.example.drinkproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.drinkproject.R;
import com.example.drinkproject.views.DrinkAdapterDrinkView;
import com.example.drinkproject.views.DrinkItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DrinkActivity extends AppCompatActivity {
    public static List<DrinkItem> DRINKSSELECTED = new ArrayList<DrinkItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        //TODO add logic for filter menu and query to db
        Spinner dropDownFilterMenu = (Spinner) findViewById(R.id.dropDownMenu);
        String[] filters = new String[]{"smoothie", "cocktails","most sold", "bitter", "sweet", "very alcoholic"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, filters);
        dropDownFilterMenu.setAdapter(adapter);

        RecyclerView recyclerView = findViewById(R.id.drinkRecyclerView);
        List<DrinkItem> drinks = new ArrayList<DrinkItem>();

        drinks.add(new DrinkItem("Spritz the drinks god", "taste like zeus amatriciana", "500$",R.drawable.spritz));

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new DrinkAdapterDrinkView(getApplicationContext(), drinks));
    }


    @Override
    protected void onResume() {
        super.onResume();

        FloatingActionButton goToCartButton = findViewById(R.id.goToCartButton);
        goToCartButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), OrderSummaryActivity.class);
            intent.putExtra("selectedDrinks", new Gson().toJson(DRINKSSELECTED));
            startActivity(intent);
        });
    }
}