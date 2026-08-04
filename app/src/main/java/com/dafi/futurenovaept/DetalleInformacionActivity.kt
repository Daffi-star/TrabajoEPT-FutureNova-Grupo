package com.dafi.futurenovaept

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetalleInformacionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_detalle_informacion)

        val btnBack = findViewById<ImageView>(R.id.btnBackDetalle)
        val tvTitulo = findViewById<TextView>(R.id.tvTituloDetalle)
        val tvContenido = findViewById<TextView>(R.id.tvContenidoDetalle)

        // Recibimos los datos enviados desde el menú
        val titulo = intent.getStringExtra("EXTRA_TITULO") ?: ""
        val contenido = intent.getStringExtra("EXTRA_CONTENIDO") ?: ""

        tvTitulo.text = titulo
        tvContenido.text = contenido

        btnBack.setOnClickListener {
            finish()
        }
    }
}