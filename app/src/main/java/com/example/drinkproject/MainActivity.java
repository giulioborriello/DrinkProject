package com.example.drinkproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.biometric.BiometricPrompt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drinkproject.activities.DrinkActivity;
import com.example.drinkproject.activities.ImpostazioniActivity;
import com.example.drinkproject.activities.RegistrazioneActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.Executor;

import controller.Controller;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {
    private Controller  controller;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private  boolean doubleBackToExitPressedOnce=false;
    private Executor executor;
    public static boolean isAccessibilityEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ottieniLaConnessione();
        SharedPreferences sharedPreferences = getSharedPreferences("Credenziali", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("email") && sharedPreferences.contains("password")) {
            // Le credenziali dell'utente sono state salvate in precedenza
            TextView textViewUsername = findViewById(R.id.editTextUsername);
            String email = sharedPreferences.getString("email", "");

            textViewUsername.setText(email);
            //abilito bottone dei dati biometrici
            {
                biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {

                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        Toast.makeText(getApplicationContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        //recupero dati utente

                        SharedPreferences sharedPreferences = getSharedPreferences("Credenziali", Context.MODE_PRIVATE);
                        String email = sharedPreferences.getString("email", "");
                        String password = sharedPreferences.getString("password", "");

                        Thread loginThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                controller.login(email, password);
                                SharedPreferences sharedPreferences = getSharedPreferences("Credenziali", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("email", email);
                                editor.putString("password", password);
                                editor.apply();
                            }
                        });
                        try {
                            loginThread.start();
                            loginThread.join();
                            if(controller.eLoggato()){
                                Intent intent = new Intent(getApplicationContext(), DrinkActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Credenziali errate", Toast.LENGTH_SHORT).show();
                            }
                        } catch (InterruptedException e) {
                            Toast.makeText(getApplicationContext(), "Si è verificato un errore", Toast.LENGTH_SHORT).show();
                        }
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
        } else {
            // Non ci sono credenziali salvate
            // disabilita bottone login dei dati biometrici
            {
                FloatingActionButton biometricLoginButton = findViewById(R.id.biometricLogInButton);
                biometricLoginButton.setClickable(false);
            }
        }
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
        ConstraintLayout mainConstraint = findViewById(R.id.mainLayout);

        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(ImpostazioniActivity.CHIAVE_STATO_SWITCH, false)) {
            mainConstraint.setBackgroundColor(getResources().getColor(android.R.color.white));
            setTextColorForAllViews(mainConstraint, getResources().getColor(android.R.color.black));
        }
        else {
            mainConstraint.setBackgroundColor(getResources().getColor(R.color.brick_red));
            setTextColorForAllViews(mainConstraint, getResources().getColor(android.R.color.white));
        }

        View registerButton = findViewById(R.id.registerUsernamePasswordButton);
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegistrazioneActivity.class);
            startActivity(intent);
        });

        View logInButton = findViewById(R.id.loginButton);
        logInButton.setOnClickListener(v -> {
            String username = ((TextView) findViewById(R.id.editTextUsername)).getText().toString();
            String password = ((TextView) findViewById(R.id.editTextPassword)).getText().toString();
            Thread loginThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    controller.login(username, password);
                    SharedPreferences sharedPreferences = getSharedPreferences("Credenziali", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("email", username);
                    editor.putString("password", password);
                    editor.apply();
                }
            });
            try {
                loginThread.start();
                loginThread.join();
                if(controller.eLoggato()){
                    Intent intent = new Intent(getApplicationContext(), DrinkActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Credenziali errate", Toast.LENGTH_SHORT).show();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        View pulsanteImpostazioni = findViewById(R.id.impostazioniPulsanteMain);
        pulsanteImpostazioni.setOnClickListener(t -> {
            Intent intent = new Intent(getApplicationContext(), ImpostazioniActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "premi ancora per uscire", Toast.LENGTH_SHORT).show();

        //noinspection deprecation
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
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
