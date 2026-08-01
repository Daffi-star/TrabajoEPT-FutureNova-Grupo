package com.dafi.futurenovaept

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        // Espera 3000 milisegundos (3 segundos)
        Handler(Looper.getMainLooper()).postDelayed({
            // Aquí irás a tu ResultActivity (aún no la tienes, pero la crearemos luego)
            // Por ahora, solo cerramos esta pantalla.
            finish()
        }, 3000)
    }
}

