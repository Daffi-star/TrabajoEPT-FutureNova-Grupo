package com.dafi.futurenovaept

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.util.Calendar

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // FORZAR MODO CLARO para evitar el fondo negro
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Configuración del Nombre de Usuario
        val tvSaludo = findViewById<TextView>(R.id.tvSaludo)
        val nombreUsuario = "Dafne"
        tvSaludo.text = "Hola, $nombreUsuario 👋"

        // Configuración del Consejo diario
        val tvConsejo = findViewById<TextView>(R.id.tvConsejo)
        val consejos = listOf(
            "Tomar agua antes de comer ayuda a mejorar tu digestión.",
            "Caminar 10 minutos después de comer regula tu azúcar.",
            "Un buen descanso es clave para la absorción de nutrientes.",
            "La constancia es más importante que la perfección.",
            "Recuerda hacer estiramientos si pasas mucho tiempo sentada."
        )

        val diaDelAnio = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        val indice = diaDelAnio % consejos.size
        tvConsejo.text = consejos[indice]
    }
}