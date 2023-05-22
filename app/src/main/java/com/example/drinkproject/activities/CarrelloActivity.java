package com.example.drinkproject.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkproject.R;
import com.example.drinkproject.views.CarrelloAdapter;

import controller.Controller;

public class CarrelloActivity extends AppCompatActivity {
    private Controller controller = Controller.getInstance();
    RecyclerView recyclerView;
    CarrelloAdapter myAdapter;
    private View vaiAlPagamentoPulsante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrello);
        effettuaIlCollegamentoDelleViews();
        creaAdapter();

    }


    @Override
    protected void onResume() {
        super.onResume();
        settaIColori();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(myAdapter);
        TextView totalCounter = (TextView) findViewById(R.id.totalCounter);
        totalCounter.setText(controller.getPrezzoTotale());
        settaIListner();
    }


    private void creaAdapter() {
        myAdapter = new CarrelloAdapter(getApplicationContext(), findViewById(R.id.totalCounter), recyclerView);
    }


    private void effettuaIlCollegamentoDelleViews() {
        vaiAlPagamentoPulsante = findViewById(R.id.paymentButton);
        recyclerView = findViewById(R.id.recyclerViewOrderSummary);
    }


    private void settaIListner() {
        View pulsanteImpostazioni = findViewById(R.id.impostazioniPulsanteCarrello);
        pulsanteImpostazioni.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ImpostazioniActivity.class);
            startActivityForResult(intent, 1);
        });


        boolean paymentWork = true;         //variable for testing
        //TODO: add logic for pay with credit card
        vaiAlPagamentoPulsante.setOnClickListener(v -> {
            if(controller.ilCarrelloeVuoto()) {
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

    private void settaIColori() {
        RelativeLayout carrelloLayout = findViewById(R.id.carrelloLayout);
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(ImpostazioniActivity.CHIAVE_STATO_SWITCH, false)) {
            carrelloLayout.setBackgroundColor(getResources().getColor(android.R.color.black));
            setTextColorForAllViews(carrelloLayout, getResources().getColor(android.R.color.white));
        }
        else {
            carrelloLayout.setBackgroundColor(getResources().getColor(R.color.brick_red));
            setTextColorForAllViews(carrelloLayout, getResources().getColor(android.R.color.white));
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            myAdapter.notifyDataSetChanged();
        }
    }


    private void setTextColorForAllViews(ViewGroup viewGroup, int color) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);

            if (view instanceof ViewGroup) {
                setTextColorForAllViews((ViewGroup) view, color);
            } else if (view instanceof TextView) {
                ((TextView) view).setTextColor(color);
            }
        }
    }
}