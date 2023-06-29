package com.example.drinkproject.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkproject.R;
import com.example.drinkproject.views.DrinksAdapter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import controller.Controller;
import model.Drink;

public class DrinkActivity extends AppCompatActivity {
    private Controller controller;
    private Executor executor;
    private  boolean doubleBackToExitPressedOnce = false;
    private DrinksAdapter myAdapter;
    private RecyclerView recyclerView;
    private View impostazioniPulsante, pulsanteVaiAlCarrello;
    private Spinner filtroATendina;
    private SearchView barraDiRicerca;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ottieniLaConnessione();
        List<Drink> drinks = controller.getDrinks();
        setContentView(R.layout.activity_drink);
        effettuaIlCollegamentoDelleViews();
        //TODO add logic for filter menu and query to db
        settaIListner();
        settaIFiltri();
        settaLaRecyclerView();
    }


    private void ottieniLaConnessione() {
        executor = ContextCompat.getMainExecutor(this);
        Thread controllerThread = new Thread(() -> {
            try {
                controller = Controller.getInstance();
            } catch (Exception e) {
                //TODO: aggiungere fakedump
                e.printStackTrace();
            }
        });
        attivaIlThreadEAttendi(controllerThread);
    }


    private static void attivaIlThreadEAttendi(Thread controllerThread) {
        controllerThread.start();
        try {
            controllerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void settaIFiltri() {
        String[] filters = controller.getCategorie();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filters);
        filtroATendina.setAdapter(adapter);
    }


    private void settaLaRecyclerView() {
        myAdapter = new DrinksAdapter(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(myAdapter);
    }


    private void settaIListner() {
        impostazioniPulsante.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ImpostazioniActivity.class);
            startActivityForResult(intent, 1);
        });

        pulsanteVaiAlCarrello.setOnClickListener(v -> {
            verificaSeIlCarrelloEVuotoEVaiAlCarrello();
        });

        barraDiRicerca.setOnClickListener(v -> barraDiRicerca.setIconified(false));
        barraDiRicerca.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        filtroATendina.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                myAdapter.getCategoriesFilter().filter(filtroATendina.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    private void verificaSeIlCarrelloEVuotoEVaiAlCarrello() {
        if(controller.ilCarrelloeVuoto()) {
            Intent intent = new Intent(getApplicationContext(), CarrelloActivity.class);
            startActivityForResult(intent,1);
        } else {
            Toast.makeText(getApplicationContext(), "Il carrello è vuoto", Toast.LENGTH_SHORT).show();
        }
    }


    private void effettuaIlCollegamentoDelleViews() {
        recyclerView = findViewById(R.id.drinkRecyclerView);
        impostazioniPulsante = findViewById(R.id.impostazioniPulsanteDrink);
        pulsanteVaiAlCarrello = findViewById(R.id.goToCartButton);
        filtroATendina = findViewById(R.id.dropDownMenu);
        barraDiRicerca = findViewById(R.id.searchView);
    }


    @SuppressWarnings("deprecation")
    @Override
    protected void onResume(){
        super.onResume();
        settaIColori();
        if(Controller.pagamentoEffettuatoConSuccesso){
            Toast.makeText(getApplicationContext(), "Pagamento effettuato con successo", Toast.LENGTH_SHORT).show();
            Controller.pagamentoEffettuatoConSuccesso = false;
        }
    }


    private void settaIColori() {
        RelativeLayout drinkLayout = findViewById(R.id.drinkLayout);
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(ImpostazioniActivity.CHIAVE_STATO_SWITCH, false)) {
            drinkLayout.setBackgroundColor(getResources().getColor(android.R.color.black));
            barraDiRicerca.setBackgroundColor(getResources().getColor(android.R.color.white));
            setTextColorForAllViews(drinkLayout, getResources().getColor(android.R.color.white));
        }
        else {
            drinkLayout.setBackgroundColor(getResources().getColor(R.color.brick_red));
            barraDiRicerca.setBackgroundColor(getResources().getColor(R.color.brick_red));
            setTextColorForAllViews(drinkLayout, getResources().getColor(android.R.color.white));
        }
    }


    @SuppressWarnings("deprecation")
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            System.exit(0);
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "premi ancora per uscire", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
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