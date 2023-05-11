package com.example.drinkproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
            EditText nomeEditText = findViewById(R.id.registerName);
            EditText cognomeEditText = findViewById(R.id.registerSurname);
            EditText usernameEditText = findViewById(R.id.registerUsername);
            EditText passwordEditText = findViewById(R.id.registerPassword);

            String name = nomeEditText.getText().toString();
            String surname = cognomeEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if(controller.signIn(name, surname, username, password)) {
                SharedPreferences sharedPreferences = getSharedPreferences("Credenziali", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", username);
                editor.putString("password", password);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Username già in uso", Toast.LENGTH_SHORT).show();
            }

        });
    }
}