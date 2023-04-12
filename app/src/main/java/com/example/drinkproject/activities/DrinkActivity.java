package com.example.drinkproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.drinkproject.R;
import com.example.drinkproject.databinding.ActivityMainBinding;
import com.example.drinkproject.views.RecyclerVIewDrinkAdapter;
import com.example.drinkproject.views.RecyclerViewDrinkItem;

import java.util.ArrayList;
import java.util.List;

public class DrinkActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


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
        List<RecyclerViewDrinkItem> drinks = new ArrayList<RecyclerViewDrinkItem>();


        drinks.add(new RecyclerViewDrinkItem("Spritz the drinks god", "taste like zeus amatriciana", "500$",R.drawable.spritz));

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new RecyclerVIewDrinkAdapter(getApplicationContext(), drinks));
    }
}