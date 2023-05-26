package com.example.drinkproject.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkproject.R;

import java.util.concurrent.Executor;

import controller.Controller;

public class RegistrazioneActivity extends AppCompatActivity {
    private Controller controller;
    private Executor executor;
    private EditText registraIlNome, registraIlCognome, registraUsername, registraLaPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
        ottieniLaConnessione();
        effettuaIlCollegamentoDelleViews();
        settaIlListnerDellaRegisrazione();
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


    @Override
    protected void onResume() {
        super.onResume();
        settaIColori();
    }


    private void effettuaIlCollegamentoDelleViews() {
        registraIlNome = findViewById(R.id.registraIlNome);
        registraIlCognome = findViewById(R.id.registraIlCognome);
        registraUsername = findViewById(R.id.registraUsername);
        registraLaPassword = findViewById(R.id.registraLaPassword);
    }


    private void settaIlListnerDellaRegisrazione() {
        View registerButton = findViewById(R.id.submitRegistrationWithUsernameAndPassword);
        registerButton.setOnClickListener(v -> {
            EditText nomeEditText = findViewById(R.id.registraIlNome);
            EditText cognomeEditText = findViewById(R.id.registraIlCognome);
            EditText usernameEditText = findViewById(R.id.registraUsername);
            EditText passwordEditText = findViewById(R.id.registraLaPassword);

            String name = nomeEditText.getText().toString();
            String surname = cognomeEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            Thread registrazioneThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    controller.signIn(name, surname, username, password);
                }
            });
            try {
                registrazioneThread.start();
                registrazioneThread.join();
                if(controller.eLoggato()) {
                    SharedPreferences sharedPreferences = getSharedPreferences("Credenziali", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", username);
                    editor.putString("password", password);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), DrinkActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Registazione fallita", Toast.LENGTH_SHORT).show();
                }
            } catch (InterruptedException e) {
                Toast.makeText(getApplicationContext(), "Si è verificato un errore", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void settaIColori() {
        ConstraintLayout registrazioneLayout = findViewById(R.id.registrazioneLayout);
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(ImpostazioniActivity.CHIAVE_STATO_SWITCH, false)) {
            registrazioneLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
            setTextColorForAllViews(registrazioneLayout, getResources().getColor(android.R.color.black));
            registraIlCognome.setHintTextColor(getResources().getColor(R.color.white));
            registraIlNome.setHintTextColor(getResources().getColor(R.color.white));
            registraUsername.setHintTextColor(getResources().getColor(R.color.white));
            registraLaPassword.setHintTextColor(getResources().getColor(R.color.white));
        }
        else {
            registrazioneLayout.setBackgroundColor(getResources().getColor(R.color.brick_red));
            setTextColorForAllViews(registrazioneLayout, getResources().getColor(android.R.color.white));
        }
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