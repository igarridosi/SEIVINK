package com.example.seivink;

import android.content.Intent; // Importa la clase Intent
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView; // Aunque no lo uses, lo dejo por si acaso
import android.widget.Toast; // Para las pruebas iniciales, luego puedes quitarlo
import androidx.appcompat.app.AppCompatActivity;

public class AccessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        Button buttonSignUp = findViewById(R.id.buttonSignUp);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccessActivity.this, LoginActivity.class);
                startActivity(intent);

                // finish();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccessActivity.this, CreateAccountActivity.class);
                startActivity(intent);
                // finish();
            }
        });
    }
}