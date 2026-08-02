package com.dafi.futurenovaept

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.TextView
import com.dafi.futurenovaept.R

class AguaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Aquí es donde vinculas tu diseño XML
        setContentView(R.layout.activity_agua)

        // Ahora, inicializamos los botones para usarlos más adelante
        val btnAdd = findViewById<ImageButton>(R.id.btnAdd)
        val btnMinus = findViewById<ImageButton>(R.id.btnMinus)
        val btnDrink = findViewById<ImageButton>(R.id.btnDrink)
        val tvTotalMl = findViewById<TextView>(R.id.tvTotalMl)

        // ¡Y aquí es donde pondremos la lógica!
        setupListeners(btnAdd, btnMinus, btnDrink, tvTotalMl)
    }

    private fun setupListeners(btnAdd: ImageButton, btnMinus: ImageButton, btnDrink: ImageButton, tvTotalMl: TextView) {
        // Por ahora, solo un log para probar que funcionan
        btnAdd.setOnClickListener {
            // Aquí sumaremos agua
        }

        btnDrink.setOnClickListener {
            // Aquí abriremos el diálogo de la botella
        }
    }
}