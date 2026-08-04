package com.dafi.futurenovaept

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MasInformacionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_mas_informacion)

        val cardQueEs = findViewById<CardView>(R.id.cardQueEs)
        val cardCausas = findViewById<CardView>(R.id.cardCausas)
        val cardSintomas = findViewById<CardView>(R.id.cardSintomas)
        val cardTratamiento = findViewById<CardView>(R.id.cardTratamiento)

        cardQueEs.setOnClickListener {
            abrirDetalle(
                "¿Qué es la Anemia?",
                "La anemia es una afección en la que la sangre no cuenta con suficientes glóbulos rojos sanos para transportar un nivel adecuado de oxígeno a los tejidos del cuerpo.\n\nEsto puede hacer que te sientas cansado, débil y con menor rendimiento físico o mental en tu día a día."
            )
        }

        cardCausas.setOnClickListener {
            abrirDetalle(
                "Causas de la Anemia",
                "La causa más común es la deficiencia de hierro. El organismo necesita hierro para producir hemoglobina, la proteína encargada de llevar oxígeno desde los pulmones al resto del cuerpo.\n\nTambién puede deberse a la falta de ciertas vitaminas como la B12 o el ácido fólico, pérdidas de sangre o una mala absorción de nutrientes."
            )
        }

        cardSintomas.setOnClickListener {
            abrirDetalle(
                "Síntomas de la Anemia",
                "Los signos de alerta más frecuentes son:\n\n• Fatiga crónica y cansancio constante\n• Debilidad general\n• Piel pálida o amarillenta\n• Mareos o dolores de cabeza\n• Manos y pies fríos\n• Dificultad para respirar al realizar esfuerzos leves"
            )
        }

        cardTratamiento.setOnClickListener {
            abrirDetalle(
                "Tratamiento y Dieta",
                "El abordaje principal incluye:\n\n• Consumo de suplementos de hierro recetados por un especialista.\n• Incorporar alimentos ricos en hierro a tu dieta (carnes rojas, lentejas, espinacas, hígado, granos enteros).\n• Acompañar tus comidas con fuentes de vitamina C (como limón o naranja) para potenciar la absorción del hierro en el organismo."
            )
        }
    }

    private fun abrirDetalle(titulo: String, contenido: String) {
        val intent = Intent(this, DetalleInformacionActivity::class.java).apply {
            putExtra("EXTRA_TITULO", titulo)
            putExtra("EXTRA_CONTENIDO", contenido)
        }
        startActivity(intent)
    }
}