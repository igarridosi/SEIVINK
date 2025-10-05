package com.example.seivink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.seivink.databinding.ActivityMainBinding // Importante: esta clase se genera automáticamente
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var userId: Int = -1

    // Let's assume you have a data class for your saving goal
    data class SavingGoal(val name: String, val currentAmount: Double, val targetAmount: Double)

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

        loadSavingGoal()
    }

    private fun loadSavingGoal() {
        // Here you would typically call your DatabaseHelper to get the saving goal for the user.
        // For now, let's simulate it with a nullable SavingGoal object.
        val savingGoal: SavingGoal? = getSavingGoalFromDatabase(userId)

        if (savingGoal != null) {
            binding.progressCard.visibility = View.VISIBLE

            val progressPercentage = (savingGoal.currentAmount / savingGoal.targetAmount * 100).toInt()

            binding.progressTitle.text = savingGoal.name
            binding.savingsProgressBar.progress = progressPercentage
            binding.savingsProgressPercentage.text = "$progressPercentage% Alcanzado"

            val format = NumberFormat.getCurrencyInstance(Locale.US)
            val currentAmountFormatted = format.format(savingGoal.currentAmount)
            val targetAmountFormatted = format.format(savingGoal.targetAmount)

            binding.savingsTargetAmount.text = "$currentAmountFormatted / $targetAmountFormatted"
        } else {
            binding.progressCard.visibility = View.GONE
        }
    }

    private fun getSavingGoalFromDatabase(userId: Int): SavingGoal? {
        // TODO: Replace this with your actual database call
        // This is a dummy implementation
        if (userId % 2 == 0) { // Example logic: goal exists for even user IDs
            return SavingGoal("Ahorro para viaje", 750.0, 1500.0)
        } else {
            return null
        }
    }
}