package com.example.drinkproject.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.drinkproject.R;
import com.example.drinkproject.views.DrinksAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

import controller.Controller;
import model.Drink;

public class DrinkActivity extends AppCompatActivity {
    private Controller controller = Controller.getInstance();
    List<Drink> drinks = controller.getDrinks();
    DrinksAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        //TODO add logic for filter menu and query to db
        Spinner dropDownFilterMenu = (Spinner) findViewById(R.id.dropDownMenu);
        String[] filters = controller.getCategorie();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, filters);
        dropDownFilterMenu.setAdapter(adapter);

        RecyclerView recyclerView = findViewById(R.id.drinkRecyclerView);
        myAdapter = new DrinksAdapter(getApplicationContext(), drinks);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        recyclerView.setAdapter(myAdapter);
    }


    @Override
    protected void onResume(){
        super.onResume();



        FloatingActionButton goToCartButton = findViewById(R.id.goToCartButton);
        goToCartButton.setOnClickListener(v -> {
            if(!controller.ilCarrelloéVuoto()) {
                Intent intent = new Intent(getApplicationContext(), CarrelloActivity.class);
                startActivityForResult(intent,1);
            } else {
                Toast.makeText(getApplicationContext(), "Il carrello è vuoto", Toast.LENGTH_SHORT).show();
            }
        });

        Spinner dropDownFilterMenu = (Spinner) findViewById(R.id.dropDownMenu);
        dropDownFilterMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    myAdapter.getCategoriesFilter().filter(dropDownFilterMenu.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK) {
            myAdapter.notifyDataSetChanged();
        }
    }
}