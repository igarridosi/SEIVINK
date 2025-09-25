package com.example.seivink; // Asegúrate de que este sea tu paquete

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText etFullName, etEmail, etMobileNumber, etDateOfBirth, etPassword, etConfirmPassword;
    private ImageButton passwordToggleButton, confirmPasswordToggleButton;
    private Button btnSignUp;
    private TextView tvTermsPrivacy, tvAlreadyHaveAccount;

    private boolean passwordVisible = false;
    private boolean confirmPasswordVisible = false;

    @SuppressLint({"ResourceAsColor", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        etFullName = findViewById(R.id.et_full_name);
        etEmail = findViewById(R.id.et_email);
        etMobileNumber = findViewById(R.id.et_mobile_number);
        etDateOfBirth = findViewById(R.id.et_date_of_birth);
        etPassword = findViewById(R.id.et_password_register);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        passwordToggleButton = findViewById(R.id.password_toggle_button_register);
        confirmPasswordToggleButton = findViewById(R.id.confirm_password_toggle_button);
        btnSignUp = findViewById(R.id.button_signup_register);
        tvTermsPrivacy = findViewById(R.id.tv_terms_privacy);
        tvAlreadyHaveAccount = findViewById(R.id.tv_already_have_account);

        // Configurar el DatePicker para "Date Of Birth"
        etDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

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

        // Configurar el toggle de visibilidad de confirmar contraseña
        confirmPasswordToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirmPasswordVisible) {
                    etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmPasswordToggleButton.setImageResource(R.drawable.baseline_visibility_off_24);
                } else {
                    etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    confirmPasswordToggleButton.setImageResource(R.drawable.baseline_visibility_24);
                }
                etConfirmPassword.setSelection(etConfirmPassword.getText().length());
                confirmPasswordVisible = !confirmPasswordVisible;
            }
        });

        // Configurar el botón de Sign Up
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí iría tu lógica de registro
                String fullName = etFullName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String mobile = etMobileNumber.getText().toString().trim();
                String dob = etDateOfBirth.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                if (fullName.isEmpty() || email.isEmpty() || mobile.isEmpty() || dob.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(CreateAccountActivity.this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(CreateAccountActivity.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateAccountActivity.this, "Cuenta creada (simulado)!", Toast.LENGTH_SHORT).show();
                    // Ejemplo: Redirigir al login o a la pantalla principal
                    // startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                    // finish();
                }
            }
        });

        // Configurar el texto de Términos de Uso y Política de Privacidad para ser clicable
        String termsText = "By continuing, you agree to Terms of Use and Privacy Policy.";
        SpannableString ssTerms = new SpannableString(termsText);

        ClickableSpan termsClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(CreateAccountActivity.this, "Abrir Términos de Uso", Toast.LENGTH_SHORT).show();
                // Aquí podrías abrir un WebView o un navegador
            }
        };

        ClickableSpan privacyClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(CreateAccountActivity.this, "Abrir Política de Privacidad", Toast.LENGTH_SHORT).show();
                // Aquí podrías abrir un WebView o un navegador
            }
        };

        ssTerms.setSpan(termsClickableSpan, termsText.indexOf("Terms of Use"), termsText.indexOf(" and"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssTerms.setSpan(privacyClickableSpan, termsText.indexOf("Privacy Policy."), termsText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssTerms.setSpan(new StyleSpan(Typeface.BOLD), termsText.indexOf("Terms of Use"), termsText.indexOf(" and"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssTerms.setSpan(new StyleSpan(Typeface.BOLD), termsText.indexOf("Privacy Policy."), termsText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        tvTermsPrivacy.setText(ssTerms);
        tvTermsPrivacy.setMovementMethod(LinkMovementMethod.getInstance());
        tvTermsPrivacy.setHighlightColor(android.R.color.transparent);

        // Configurar el texto "Already have an account? Log In" para ser clicable
        String loginText = "Already have an account? Log In";
        SpannableString ssLogin = new SpannableString(loginText);

        ClickableSpan loginClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finalizar esta actividad para que el usuario no pueda volver con el botón de atrás
            }
        };

        ssLogin.setSpan(loginClickableSpan, loginText.indexOf("Log In"), loginText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssLogin.setSpan(new StyleSpan(Typeface.BOLD), loginText.indexOf("Log In"), loginText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        tvAlreadyHaveAccount.setText(ssLogin);
        tvAlreadyHaveAccount.setMovementMethod(LinkMovementMethod.getInstance());
        tvAlreadyHaveAccount.setHighlightColor(android.R.color.transparent);
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Formatear la fecha como DD / MM / YYYY
                        String formattedDay = String.format("%02d", dayOfMonth);
                        String formattedMonth = String.format("%02d", (monthOfYear + 1)); // monthOfYear es base 0
                        etDateOfBirth.setText(formattedDay + " / " + formattedMonth + " / " + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
}