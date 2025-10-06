package com.example.seivink

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.seivink.db.AppDatabase
import com.example.seivink.db.entity.SavingGoal
import com.example.seivink.db.entity.Transaction

class MainActivity : ComponentActivity() {

    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userId = intent.getIntExtra("USER_ID", -1)

        if (userId == -1) {
            Toast.makeText(this, "Error: No se pudo identificar al usuario.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val dao = AppDatabase.getDatabase(this).appDao()

        setContent {
            // SEIVINKTheme es el tema de tu app, puedes ajustarlo en el archivo Theme.kt
            // Por ahora, usamos el tema por defecto de Material 3
            MaterialTheme {
                // Obtenemos los datos de la base de datos como un "Estado"
                // La UI se reconstruirá automáticamente cuando estos datos cambien
                val savingGoals by dao.getAllSavingGoalsForUser(userId).collectAsStateWithLifecycle(initialValue = emptyList())
                val transactions by dao.getAllTransactionsForUser(userId).collectAsStateWithLifecycle(initialValue = emptyList())

                MainScreen(
                    userName = "Usuario #$userId", // Puedes obtener el nombre real del usuario con otra consulta
                    savingGoals = savingGoals,
                    transactions = transactions
                )
            }
        }
    }
}

// Composable principal que contiene toda la pantalla
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    userName: String,
    savingGoals: List<SavingGoal>,
    transactions: List<Transaction>
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "¡Hola, $userName!") }) },
        // Aquí iría tu barra de navegación inferior si la creas como un Composable
        bottomBar = { /* BottomNavBar() */ }
    ) { paddingValues ->
        // LazyColumn es el equivalente de RecyclerView en Compose, es muy eficiente
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Dp(16f)), // Padding horizontal para todo el contenido
            verticalArrangement = Arrangement.spacedBy(Dp(24f)) // Espacio entre cada item
        ) {
            // Mostramos la tarjeta de balance
            item {
                BalanceCard(balance = 1234.56) // TODO: Reemplazar con el balance real del DAO
            }

            // Si hay objetivos de ahorro, mostramos el primero
            if (savingGoals.isNotEmpty()) {
                item {
                    SavingGoalProgressCard(goal = savingGoals.first())
                }
            }

            // Mostramos los filtros
            item {
                // Aquí irían los ToggleButtons como un Composable
                // FilterButtons()
            }

            // Título para la lista de transacciones
            item {
                Text(text = "Transacciones Recientes", style = MaterialTheme.typography.titleMedium)
            }

            // Mostramos la lista de transacciones
            items(transactions) { transaction ->
                TransactionItem(transaction = transaction)
            }
        }
    }
}

// --- Componentes Individuales (Reemplazan las partes de tu XML) ---

@Composable
fun BalanceCard(balance: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = Dp(4f))
    ) {
        Column(modifier = Modifier.padding(Dp(20f))) {
            Text(text = "Balance Total", style = MaterialTheme.typography.titleSmall)
            Text(text = "$$balance", style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Composable
fun SavingGoalProgressCard(goal: SavingGoal) {
    val progress = (goal.currentAmount / goal.targetAmount).toFloat()

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = Dp(4f))
    ) {
        Column(modifier = Modifier.padding(Dp(20f))) {
            Text(text = goal.name, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(Dp(16f)))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(Dp(8f))
            )
            // Aquí puedes añadir los textos de porcentaje y monto
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dp(12f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Aquí iría el Icono
        // Icon(...)
        Spacer(modifier = Modifier.width(Dp(16f)))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = transaction.category, style = MaterialTheme.typography.bodyLarge)
            Text(text = transaction.type, style = MaterialTheme.typography.bodySmall)
        }
        Text(text = "${transaction.amount}")
    }
}


// --- Preview para ver tu diseño sin ejecutar la app ---

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() { // Le cambiamos el nombre para que sea más claro
    // 1. Creamos datos falsos que se parezcan a los reales
    val sampleGoals = listOf(
        SavingGoal(id = 1, userId = 1, name = "Vacaciones en la playa", targetAmount = 2000.0, currentAmount = 500.0, creationDate = 0L, targetDate = null)
    )

    val sampleTransactions = listOf(
        Transaction(id = 1, userId = 1, amount = -55.40, type = "expense", category = "Comida", description = "Compra en el supermercado", date = 0L, savingGoalId = null),
        Transaction(id = 2, userId = 1, amount = 1800.0, type = "income", category = "Salario", description = "Nómina de Octubre", date = 0L, savingGoalId = null)
    )

    // 2. Usamos un tema para que la preview se vea bien
    MaterialTheme {
        // 3. Llamamos a nuestro Composable principal pasándole los datos falsos
        MainScreen(
            userName = "Usuario de Prueba",
            savingGoals = sampleGoals,
            transactions = sampleTransactions
        )
    }
}