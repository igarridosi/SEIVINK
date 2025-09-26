package com.example.seivink // Asegúrate de que este sea tu paquete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import android.widget.ImageView // Asegúrate de importar ImageView
import com.example.seivink.databinding.ActivityMainBinding // Importa la clase de binding generada

class MainActivity : AppCompatActivity() {

    // Declara una propiedad para tu clase de binding.
    // 'lateinit' significa que la inicializarás más tarde, específicamente en onCreate.
    private lateinit var binding: ActivityMainBinding

    annotation class ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa el objeto de binding. Esto infla tu layout 'activity_main.xml'.
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Establece la vista de la actividad a la vista raíz obtenida del binding.
        setContentView(binding.root)

        // --- Configuración e interactividad de las vistas ---

        // Configurar la barra de progreso (usando el binding para acceder a la vista)
        binding.expenseProgressBar.progress = 30 // Establecer el progreso inicial a 30%

        // Listener para el icono de notificación (usando el binding y una lambda para el click)
        binding.notificationBell.setOnClickListener {
            Toast.makeText(this, "Notificaciones clickeadas", Toast.LENGTH_SHORT).show()
            // Aquí puedes iniciar una nueva actividad para ver las notificaciones
        }

        // Configurar ToggleButtons para Daily/Weekly/Monthly
        // Definimos un único listener para los tres botones
        val filterClickListener = View.OnClickListener { v ->
            // Resetear el estado de todos los botones a 'no checked'
            binding.btnDaily.isChecked = false
            binding.btnWeekly.isChecked = false
            binding.btnMonthly.isChecked = false

            // Establecer el botón clickeado como 'checked'
            (v as ToggleButton).isChecked = true // Se hace un 'cast' seguro a ToggleButton

            // Lógica para filtrar transacciones (ej. cargar diferentes datos en la lista)
            val selectedFilter = v.text.toString() // Acceso directo a la propiedad 'text'
            Toast.makeText(this, "Filtrar por: $selectedFilter", Toast.LENGTH_SHORT).show()
        }

        // Asignar el mismo listener a los tres ToggleButtons
        binding.btnDaily.setOnClickListener(filterClickListener)
        binding.btnWeekly.setOnClickListener(filterClickListener)
        binding.btnMonthly.setOnClickListener(filterClickListener)

        // Configurar listeners para la barra de navegación inferior
        // Usamos una expresión 'when' para manejar los diferentes IDs de clic de manera concisa
        val bottomNavClickListener = View.OnClickListener { v ->
            val message = when (v.id) {
                R.id.nav_home -> "Home clickeado"
                R.id.nav_stats -> "Estadísticas clickeadas"
                R.id.nav_transfer -> "Transferir clickeado"
                R.id.nav_wallet -> "Billetera clickeada"
                R.id.nav_profile -> "Perfil clickeado"
                else -> "" // En caso de que se haga clic en algo no esperado
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

            // Lógica para cambiar los colores de los iconos de la barra de navegación:
            // Esto es un ejemplo. En una implementación real, podrías tener una función
            // que restablezca todos los colores y luego establezca el del elemento seleccionado.
            // Para cambiar el color de un ImageView programáticamente:
            // val imageView = (v as LinearLayout).getChildAt(0) as ImageView
            // imageView.setColorFilter(resources.getColor(R.color.purple_primary, theme))
        }

        // Asignar el listener a cada elemento de la barra de navegación
        binding.navHome.setOnClickListener(bottomNavClickListener)
        binding.navStats.setOnClickListener(bottomNavClickListener)
        binding.navTransfer.setOnClickListener(bottomNavClickListener)
        binding.navWallet.setOnClickListener(bottomNavClickListener)
        binding.navProfile.setOnClickListener(bottomNavClickListener)
    }
}