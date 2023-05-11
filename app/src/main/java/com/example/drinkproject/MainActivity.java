package com.example.drinkproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.biometric.BiometricPrompt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkproject.activities.DrinkActivity;
import com.example.drinkproject.activities.RegistrazioneActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.Executor;

import controller.Controller;

public class MainActivity extends AppCompatActivity {


    //private Socket socket;
    private Controller  controller=null;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ottenere l'executor dell'UI thread
        Executor executor = ContextCompat.getMainExecutor(this);

// Postare un messaggio sulla coda dell'UI thread
        //TODO USARE MEtodo della prof
        // public void onClick(View v) {
        //    // Crea un nuovo thread
        //    new Thread(() -> {
        //        // Carica un'immagine da una sorgente remota
        //        final Bitmap b = caricaDaRete();
        //        // Imposta l'immagine caricata nella ImageView sulla UI thread
        //        iv.post(() -> {
        //            iv.setImageBitmap(b);
        //        });
        //    }).start();
        //}
        executor.execute(() -> {
            // dump dati
            controller = Controller.getInstance();
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Credenziali", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("email") && sharedPreferences.contains("password")) {
            // Le credenziali dell'utente sono state salvate in precedenza
            TextView textViewUsername =((TextView) findViewById(R.id.editTextUsername));
            String email = sharedPreferences.getString("email", "");

            textViewUsername.setText(email);
            //abilito bottone dei dati biometrici
            {
                FloatingActionButton biometricLoginButton = findViewById(R.id.biometricLogInButton);
                biometricLoginButton.setClickable(true);
            }

        } else {
            // Non ci sono credenziali salvate
            // disabilita bottone login dei dati biometrici
            {
                FloatingActionButton biometricLoginButton = findViewById(R.id.biometricLogInButton);
                biometricLoginButton.setClickable(false);
            }
        }



        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {

            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Intent intent = new Intent(getApplicationContext(), DrinkActivity.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Biometric login for my app").setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();
        FloatingActionButton biometricLoginButton = findViewById(R.id.biometricLogInButton);
        biometricLoginButton.setOnClickListener(view -> biometricPrompt.authenticate(promptInfo));
    }


    @Override
    protected void onResume() {
        super.onResume();

        View registerButton = findViewById(R.id.registerUsernamePasswordButton);
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegistrazioneActivity.class);
            startActivity(intent);
        });

        View logInButton = findViewById(R.id.loginButton);
        logInButton.setOnClickListener(v -> {
            String username = ((TextView) findViewById(R.id.editTextUsername)).getText().toString();
            String password = ((TextView) findViewById(R.id.editTextPassword)).getText().toString();

            if(controller.login(username, password)) {
                SharedPreferences sharedPreferences = getSharedPreferences("Credenziali", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", username);
                editor.putString("password", password);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), DrinkActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }

}