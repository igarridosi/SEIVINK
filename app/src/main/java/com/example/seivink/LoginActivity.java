package com.example.seivink; // Asegúrate de que este sea tu paquete

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seivink.db.AppDatabase;
import com.example.seivink.db.entity.User;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsernameEmail;
    private EditText etPassword;
    private ImageButton passwordToggleButton;
    private Button btnLogin;
    private Button btnSignUpSecondary;
    private TextView tvDontHaveAccount;

    private boolean passwordVisible = false;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsernameEmail = findViewById(R.id.edit_text_username_email);
        etPassword = findViewById(R.id.edit_text_password);
        passwordToggleButton = findViewById(R.id.password_toggle_button);
        btnLogin = findViewById(R.id.button_login);
        btnSignUpSecondary = findViewById(R.id.button_signup_secondary);
        tvDontHaveAccount = findViewById(R.id.text_dont_have_account);

        // Configurar el toggle de visibilidad de contraseña
        passwordToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordVisible) {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordToggleButton.setImageResource(R.drawable.baseline_visibility_off_24);
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordToggleButton.setImageResource(R.drawable.baseline_visibility_24);
                }
                etPassword.setSelection(etPassword.getText().length());
                passwordVisible = !passwordVisible;
            }
        });

        // Configurar el botón de Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameEmail = etUsernameEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (usernameEmail.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor, ingresa usuario/email y contraseña.", Toast.LENGTH_SHORT).show();
                } else {
                    // Aquí iría tu lógica de autenticación
                    Toast.makeText(LoginActivity.this, "Login exitoso (simulado)!", Toast.LENGTH_SHORT).show();
                    // Ejemplo: Intent para ir a la pantalla principal
                    // startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    // finish();
                }
                loginUser();
            }
        });

        // Configurar el botón secundario de Sign Up
        btnSignUpSecondary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });

        // Configurar el texto "Don't have an account? Sign Up" para ser clicable
        String text = "Don't have an account? Sign Up";
        SpannableString ss = new SpannableString(text);

        ClickableSpan signUpClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        };

        // Aplicar estilo bold a "Sign Up"
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);

        ss.setSpan(signUpClickableSpan, text.indexOf("Sign Up"), text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(boldSpan, text.indexOf("Sign Up"), text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvDontHaveAccount.setText(ss);
        tvDontHaveAccount.setMovementMethod(LinkMovementMethod.getInstance());
        tvDontHaveAccount.setHighlightColor(android.R.color.transparent);


    }
    private void loginUser() {
        String email = etUsernameEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Por favor, ingresa correo y contraseña.", Toast.LENGTH_SHORT).show();
            return;
        }

        String passwordHash = password; // Recordatorio: encriptar en una app real

        AppDatabase db = AppDatabase.Companion.getDatabase(getApplicationContext());

        // --- CÓDIGO SIMPLIFICADO ---
        // Hacemos la consulta a la base de datos directamente
        User user = db.appDao().loginUser(email, passwordHash);

        if (user != null) {
            // Login exitoso
            Toast.makeText(LoginActivity.this, "¡Bienvenido, " + user.getFullName() + "!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("USER_ID", user.getId());
            startActivity(intent);
            finish();
        } else {
            // Credenciales incorrectas
            Toast.makeText(LoginActivity.this, "Correo o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
        }
    }
}