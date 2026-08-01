package com.dafi.futurenovaept // Asegúrate de dejar el package que ya tengas arriba

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Asegúrate de que el nombre aquí coincida con tu archivo XML
        setContentView(R.layout.activity_dashboard)

        // 1. Configuración del Nombre de Usuario
        val tvSaludo = findViewById<TextView>(R.id.tvSaludo)

        // Aquí obtendrías el nombre desde tu base de datos o sesión
        // Por ahora, lo dejamos como "Dafne" como acordamos
        val nombreUsuario = "Dafne"
        tvSaludo.text = "Hola, $nombreUsuario 👋"

        // 2. Configuración del Consejo diario
        val tvConsejo = findViewById<TextView>(R.id.tvConsejo)
        val consejos = listOf(
            "Tomar agua antes de comer ayuda a mejorar tu digestión.",
            "Caminar 10 minutos después de comer regula tu azúcar.",
            "Un buen descanso es clave para la absorción de nutrientes.",
            "La constancia es más importante que la perfección.",
            "Recuerda hacer estiramientos si pasas mucho tiempo sentada."
        )

        // Calcula el índice basado en el día del año
        val diaDelAnio = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        val indice = diaDelAnio % consejos.size

        // Asigna el consejo correspondiente
        tvConsejo.text = consejos[indice]
    }
}