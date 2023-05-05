package com.example.drinkproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.drinkproject.MainActivity;
import com.example.drinkproject.R;

import controller.Controller;

public class RegistrazioneActivity extends AppCompatActivity {
    Controller controller = Controller.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
    }


    @Override
    protected void onResume() {
        super.onResume();
        View registerButton = findViewById(R.id.submitRegistrationWithUsernameAndPassword);

        registerButton.setOnClickListener(v -> {
            String name = findViewById(R.id.registerName).toString();
            String surname = findViewById(R.id.registerSurname).toString();
            String username = findViewById(R.id.registerUsername).toString();
            String password = findViewById(R.id.registerPassword).toString();

            if(controller.signIn(name, surname, username, password)) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else {
                onButtonShowPopupWindowClick(LayoutInflater.from(this).inflate(R.layout.registration_not_done_pop_up, null));
            }

        });
    }


    public void onButtonShowPopupWindowClick(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.registration_done_pop_up, null);

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