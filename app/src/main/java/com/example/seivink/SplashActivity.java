package com.example.seivink;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000; // 3 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Este método se ejecutará después del tiempo de espera del splash.
                // Inicia tu actividad principal (LoginActivity en este caso).
                Intent i = new Intent(SplashActivity.this, AccessActivity.class);
                startActivity(i);

                // Cierra esta actividad para que el usuario no pueda volver a ella con el botón "atrás".
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
