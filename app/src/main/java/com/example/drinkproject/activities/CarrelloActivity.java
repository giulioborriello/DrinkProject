package com.example.drinkproject.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkproject.R;
import com.example.drinkproject.views.CarrelloAdapter;

import java.util.List;

import controller.Controller;
import model.DrinkOrdine;

public class CarrelloActivity extends AppCompatActivity {
    private Controller controller = Controller.getInstance();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrello);

        recyclerView = findViewById(R.id.recyclerViewOrderSummary);
        List<DrinkOrdine> drinks = controller.getSummary();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new CarrelloAdapter(getApplicationContext(), drinks));


    }


    @Override
    protected void onResume() {
        super.onResume();
        TextView totalCounter = (TextView) findViewById(R.id.totalCounter);
        totalCounter.setText(controller.getPrezzoTotale());


        boolean paymentWork = true;         //variable for testing
        //TODO: add logic for pay with credit card
        View paymentButton = findViewById(R.id.paymentButton);
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paymentWork) {
                    CarrelloAdapter carrelloAdapter = (CarrelloAdapter) recyclerView.getAdapter();

                    if (carrelloAdapter != null)
                        carrelloAdapter.clear();

                    carrelloAdapter.notifyDataSetChanged();
                    recyclerView.removeAllViews();
                    totalCounter.setText("0.0");

                    Toast.makeText(getApplicationContext(), "Pagamento effettuato", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Pagamento non effettuato", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}