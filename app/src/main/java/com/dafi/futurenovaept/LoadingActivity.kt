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
            // Creamos el intent para ir a ResultActivity
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)

            // Cerramos LoadingActivity para que el usuario no pueda volver atrás
            finish()
        }, 3000)
    }
}

