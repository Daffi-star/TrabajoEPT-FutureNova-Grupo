package com.dafi.futurenovaept

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetalleRecetaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_detalle_receta)

        val btnBack = findViewById<ImageView>(R.id.btnBackDetalle)
        val tvTitulo = findViewById<TextView>(R.id.tvTituloDetalle)
        val tvRiesgo = findViewById<TextView>(R.id.tvRiesgoDetalle)
        val tvIngredientes = findViewById<TextView>(R.id.tvIngredientesDetalle)
        val tvPreparacion = findViewById<TextView>(R.id.tvPreparacionDetalle)
        val tvComoAyuda = findViewById<TextView>(R.id.tvComoAyudaDetalle)
        val tvDatosExtra = findViewById<TextView>(R.id.tvDatosExtraDetalle)

        // Recibir los datos enviados por el Intent
        tvTitulo.text = intent.getStringExtra("EXTRA_TITULO")
        tvRiesgo.text = "Ideal para nivel: ${intent.getStringExtra("EXTRA_RIESGO")?.uppercase()}"
        tvIngredientes.text = intent.getStringExtra("EXTRA_INGREDIENTES")
        tvPreparacion.text = intent.getStringExtra("EXTRA_PREPARACION")
        tvComoAyuda.text = intent.getStringExtra("EXTRA_COMO_AYUDA")
        tvDatosExtra.text = intent.getStringExtra("EXTRA_DATOS_EXTRA")

        btnBack.setOnClickListener {
            finish()
        }
    }
}