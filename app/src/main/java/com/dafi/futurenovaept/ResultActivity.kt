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
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val ultimoDiagnostico = db.diagnosisDao().getLatestDiagnosis()
            if (ultimoDiagnostico != null) {
                // Aquí mostramos el nivel de riesgo
                tvRiesgo.text = "Tu nivel de riesgo detectado es: ${ultimoDiagnostico.nivelRiesgo}"
            } else {
                tvRiesgo.text = "No se encontraron resultados previos."
            }
        }

        // Redirigir al menú después de 4 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, DashboardActivity::class.java)

            // ESTA ES LA CLAVE: Borra el historial anterior (Login, carga, etc.)
            // y convierte DashboardActivity en la nueva pantalla raíz.
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish()
        }, 4000)
    }
}