package com.example.drinkproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.drinkproject.R;
import com.example.drinkproject.views.RecyclerVIewDrinkAdapter;
import com.example.drinkproject.views.RecyclerViewDrinkHolder;
import com.example.drinkproject.views.RecyclerViewDrinkItem;

import java.util.ArrayList;
import java.util.List;

public class DrinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        RecyclerView recyclerView = findViewById(R.id.drinkRecyclerView);
        List<RecyclerViewDrinkItem> drinks = new ArrayList<RecyclerViewDrinkItem>();

        //TODO implementare logica del database per aggiungere i drink tramite le query dell'utente
        drinks.add(new RecyclerViewDrinkItem("Spritz the drinks god", "taste like zeus amatriciana", "water, love, the essence of the universe", R.drawable.spritz));

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new RecyclerVIewDrinkAdapter(getApplicationContext(), drinks));
    }
}