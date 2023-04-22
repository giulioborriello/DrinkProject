package com.example.drinkproject.activities;

import androidx.annotation.NonNull;
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
import android.widget.TextView;

import com.example.drinkproject.R;
import com.example.drinkproject.views.CartAdapter;

import java.util.List;

import controller.Controller;
import model.DrinkOrdine;

public class OrderSummaryActivity extends AppCompatActivity {
    private Controller controller = Controller.getInstance();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        recyclerView = findViewById(R.id.recyclerViewOrderSummary);
        List<DrinkOrdine> drinks = controller.getSummary();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new CartAdapter(getApplicationContext(), drinks));


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
                    CartAdapter cartAdapter = (CartAdapter) recyclerView.getAdapter();

                    if (cartAdapter != null)
                        cartAdapter.clear();

                    cartAdapter.notifyDataSetChanged();
                    recyclerView.removeAllViews();
                    totalCounter.setText("0.0");

                    onButtonShowPopupWindowClickPaymentWorked(v);
                } else {
                    onButtonShowPopupWindowClickPaymentFailed(v);
                }

            }
        });
    }


    public void onButtonShowPopupWindowClickPaymentWorked(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.payment_made_pop_up, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });
    }


    public void onButtonShowPopupWindowClickPaymentFailed(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.payment_not_made_pop_up, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });
    }




    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}