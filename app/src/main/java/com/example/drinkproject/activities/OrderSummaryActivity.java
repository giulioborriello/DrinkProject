package com.example.drinkproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.drinkproject.R;
import com.example.drinkproject.views.RecyclerVIewDrinkAdapter;
import com.example.drinkproject.views.RecyclerViewDrinkItem;

import java.util.ArrayList;
import java.util.List;

public class OrderSummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);



        RecyclerView recyclerView = findViewById(R.id.summaryRecyclerView);
        List<RecyclerViewDrinkItem> drinks = new ArrayList<RecyclerViewDrinkItem>();

        drinks.add(new RecyclerViewDrinkItem(getIntent().getExtras().getString("name"), getIntent().getExtras().getString("description"), getIntent().getExtras().getString("price"),getIntent().getExtras().getInt("image")));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new RecyclerVIewDrinkAdapter(getApplicationContext(), drinks));
    }


    @Override
    protected void onResume() {
        super.onResume();

        //TODO: add logic for pay with credit card
        View paymentButton = findViewById(R.id.paymentButton);
        paymentButton.setOnClickListener(this::onButtonShowPopupWindowClick);
    }


    public void onButtonShowPopupWindowClick(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.payment_made_pop_up, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}