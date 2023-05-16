package com.example.drinkproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkproject.MainActivity;
import com.example.drinkproject.R;
import com.example.drinkproject.classiDiSupporto.ImpostazioniAttributi;

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
        ConstraintLayout registrazioneLayout = findViewById(R.id.registrazioneLayout);
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(ImpostazioniActivity.CHIAVE_STATO_SWITCH, false)) {
            registrazioneLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
            setTextColorForAllViews(registrazioneLayout, getResources().getColor(android.R.color.black));
        }
        else {
            registrazioneLayout.setBackgroundColor(getResources().getColor(R.color.brick_red));
            setTextColorForAllViews(registrazioneLayout, getResources().getColor(android.R.color.white));
        }

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


    private void setTextColorForAllViews(ViewGroup viewGroup, int color) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);

            if (view instanceof ViewGroup) {
                setTextColorForAllViews((ViewGroup) view, color);
            } else if (view.getClass() == TextView.class) {
                ((TextView) view).setTextColor(color);
            } else if (view.getClass() == androidx.appcompat.widget.AppCompatEditText.class) {
                ((EditText) view).setHintTextColor(getResources().getColor(android.R.color.black));
                ((EditText) view).setBackgroundColor(color);
            }
        }
    }
}