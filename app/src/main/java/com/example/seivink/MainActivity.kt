package com.example.seivink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.seivink.databinding.ActivityMainBinding // Importante: esta clase se genera automáticamente

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        userId = intent.getIntExtra("USER_ID", -1)

        if (userId == -1) {
            Toast.makeText(this, "Error: No se pudo identificar al usuario.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        binding.greeting.text = "¡Hola, Usuario #$userId!"

        Toast.makeText(this, "Login exitoso. ID de Usuario: $userId", Toast.LENGTH_LONG).show()

    }
}