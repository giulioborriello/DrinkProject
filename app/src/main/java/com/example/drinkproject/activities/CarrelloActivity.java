package com.example.drinkproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkproject.R;
import com.example.drinkproject.views.CarrelloAdapter;
import com.example.drinkproject.views.DrinksHolder;

import java.util.List;

import controller.Controller;
import model.DrinkOrdine;

public class CarrelloActivity extends AppCompatActivity {
    private Controller controller = Controller.getInstance();
    RecyclerView recyclerView;
    LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
    View view = inflater.inflate(R.layout.drink_view,parent,false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrello);

        recyclerView = findViewById(R.id.recyclerViewOrderSummary);
        List<DrinkOrdine> drinks = controller.getSummary();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new CarrelloAdapter(getApplicationContext(), drinks, findViewById(R.id.totalCounter), new DrinksHolder()));


    }


    @Override
    protected void onResume() {
        super.onResume();
        TextView totalCounter = (TextView) findViewById(R.id.totalCounter);
        totalCounter.setText(controller.getPrezzoTotale());

        boolean paymentWork = true;         //variable for testing
        //TODO: add logic for pay with credit card
        View paymentButton = findViewById(R.id.paymentButton);
        paymentButton.setOnClickListener(v -> {
            if(!controller.ilCarrelloéVuoto()) {
                Intent intent = new Intent(getApplicationContext(), PagamentoActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Il carrello è vuoto", Toast.LENGTH_SHORT).show();
            }

            /*if (paymentWork) {
                CarrelloAdapter carrelloAdapter = (CarrelloAdapter) recyclerView.getAdapter();

                if (carrelloAdapter != null)
                    carrelloAdapter.clear();

                carrelloAdapter.notifyDataSetChanged();
                recyclerView.removeAllViews();
                totalCounter.setText("0.0");

                Toast.makeText(getApplicationContext(), "Pagamento effettuato", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Pagamento non effettuato", Toast.LENGTH_SHORT).show();
            } */

        });
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}