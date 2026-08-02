package com.dafi.futurenovaept

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.dafi.futurenovaept.data.AppDatabase
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.launch
import android.content.Intent
import com.google.android.material.button.MaterialButton

class ResultadosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultados)
        // 1. Encuentra el botón (¡Asegúrate de que el ID coincida con el XML!)
        val btnVolver = findViewById<MaterialButton>(R.id.btnVolverAlMenu)

// 2. Dale la instrucción de qué hacer al tocarlo
        btnVolver.setOnClickListener {
            // Creamos el camino hacia el Dashboard
            val intent = Intent(this, DashboardActivity::class.java)

            // Esta bandera es vital para que al volver, la app no se confunda
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish() // Cerramos esta pantalla de resultados
        }

        cargarUltimoDiagnostico()
    }

    private fun cargarUltimoDiagnostico() {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "diagnosis-database"
        ).build()

        lifecycleScope.launch {
            // Asegúrate de que tu DAO tenga el método getLastRecord()
            val record = db.diagnosisDao().getLastRecord()
            if (record != null) {
                mostrarResultado(record.nivelRiesgo)
            }
        }
    }

    private fun mostrarResultado(nivel: String) {
        val tvNivel = findViewById<TextView>(R.id.tvNivelRiesgo)
        val tvExplicacion = findViewById<TextView>(R.id.tvExplicacion)
        val tvRecomendaciones = findViewById<TextView>(R.id.tvRecomendaciones)
        val card = findViewById<MaterialCardView>(R.id.cardResultado)

        when (nivel) {
            "Alto" -> {
                // Un rojo un poco más suave y elegante
                card.setCardBackgroundColor(Color.parseColor("#EF5350"))
                tvNivel.text = "Nivel: Alto"
                tvNivel.setTextColor(Color.WHITE)
                tvExplicacion.text = "Tu diagnóstico indica que existen varios indicadores de riesgo en tu bienestar actual. Esto no es un diagnóstico médico, pero sí una señal de que tu cuerpo necesita atención."
                tvRecomendaciones.text = "• Agenda una cita con un profesional de la salud.\n• Prioriza tu descanso.\n• Aumenta la ingesta de hierro.\n• Mantente hidratada con 2 litros de agua al día."
            }
            "Moderado" -> {
                // Un azul pastel muy limpio que combina con todo
                card.setCardBackgroundColor(Color.parseColor("#E3F2FD"))
                tvNivel.text = "Nivel: Moderado"
                // IMPORTANTE: Como el fondo ahora es claro, cambia el texto a oscuro
                tvNivel.setTextColor(Color.parseColor("#1565C0"))
                tvExplicacion.text = "Tu estado es estable, pero hay señales que no debes ignorar. Es el momento perfecto para hacer pequeños ajustes en tu estilo de vida."
                tvRecomendaciones.text = "• Incorpora una rutina de ejercicio ligero.\n• Mejora tu alimentación con frutas y verduras.\n• Monitorea tus síntomas semanalmente."
            }
            else -> {
                card.setCardBackgroundColor(Color.parseColor("#4CAF50"))
                tvNivel.text = "Nivel: Bajo (¡Excelente!)"
                tvNivel.setTextColor(Color.WHITE)
                tvExplicacion.text = "¡Vas por muy buen camino! Tus indicadores sugieren que mantienes hábitos saludables."
                tvRecomendaciones.text = "• Mantén tu rutina actual.\n• Sigue priorizando tu hidratación.\n• ¡No olvides disfrutar de tu tiempo libre!"
            }
        }
    }
}