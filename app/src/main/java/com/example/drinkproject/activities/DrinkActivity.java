package com.example.drinkproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.drinkproject.R;
import com.example.drinkproject.views.DrinksAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import controller.Controller;
import model.Drink;

public class DrinkActivity extends AppCompatActivity {
    private Controller controller = Controller.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        //TODO add logic for filter menu and query to db
        Spinner dropDownFilterMenu = (Spinner) findViewById(R.id.dropDownMenu);
        String[] filters = new String[]{"smoothie", "cocktails", "most sold", "bitter", "sweet", "very alcoholic"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, filters);
        dropDownFilterMenu.setAdapter(adapter);

        RecyclerView recyclerView = findViewById(R.id.drinkRecyclerView);
        List<Drink> drinks = controller.getDrinks();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new DrinksAdapter(getApplicationContext(), drinks));
    }


    @Override
    protected void onResume() {
        super.onResume();

        FloatingActionButton goToCartButton = findViewById(R.id.goToCartButton);
        goToCartButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), OrderSummaryActivity.class);
            startActivity(intent);
        });
    }
}