package com.dafi.futurenovaept

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.dafi.futurenovaept.data.AppDatabase
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvRiesgo = findViewById<TextView>(R.id.tvRiesgoResultado)

        // Consultar el último resultado
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "diagnosis-database"
        ).build()

        lifecycleScope.launch {
            val ultimoDiagnostico = db.diagnosisDao().getLatestDiagnosis()
            if (ultimoDiagnostico != null) {
                // Aquí mostramos el nivel de riesgo
                tvRiesgo.text = "Tu nivel de riesgo detectado es: ${ultimoDiagnostico.nivelRiesgo}"
            } else {
                tvRiesgo.text = "No se encontraron resultados previos."
            }
        }

        // Redirigir al menú después de 4 segundos (para que el usuario alcance a leer)
        Handler(Looper.getMainLooper()).postDelayed({
            // Cambia 'MainActivity' por el nombre de tu clase de menú principal
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }
}