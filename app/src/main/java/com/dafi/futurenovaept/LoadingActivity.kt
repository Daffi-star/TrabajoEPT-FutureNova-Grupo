package com.dafi.futurenovaept

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading) // Asegúrate de que este archivo exista

        // Esperar 3 segundos
        Handler(Looper.getMainLooper()).postDelayed({

            // --- AQUÍ ESTÁ EL CAMBIO ---
            // Cambia 'DashboardActivity' por 'ResultadosActivity'
            val intent = Intent(this, ResultActivity::class.java)

            startActivity(intent)
            finish() // Cerramos LoadingActivity para que no vuelvan atrás
        }, 3000)
    }
}
