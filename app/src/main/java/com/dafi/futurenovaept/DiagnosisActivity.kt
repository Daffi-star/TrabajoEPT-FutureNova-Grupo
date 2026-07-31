package com.dafi.futurenovaept

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DiagnosisActivity : AppCompatActivity() {

    // 1. Variable para saber en qué pregunta estamos
    private var currentQuestion = 1

    // Declaramos variables para las vistas que usaremos
    private lateinit var tvQuestion: TextView
    private lateinit var tvCount: TextView
    private lateinit var tvOpt1: TextView
    private lateinit var tvOpt2: TextView
    private lateinit var tvOpt3: TextView
    private lateinit var tvOpt4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnosis)

        // Inicializar vistas
        tvQuestion = findViewById(R.id.tvQuestionText)
        tvCount = findViewById(R.id.tvQuestionCount)
        tvOpt1 = findViewById(R.id.tvOption1) // ¡Asegúrate de poner estos IDs en tu XML!
        tvOpt2 = findViewById(R.id.tvOption2)
        tvOpt3 = findViewById(R.id.tvOption3)
        tvOpt4 = findViewById(R.id.tvOption4)

        val btnNext = findViewById<Button>(R.id.btnDiagnosisNext)

        // Tu código original de selección (lo mantenemos igual)
        val option1 = findViewById<LinearLayout>(R.id.option1)
        val option2 = findViewById<LinearLayout>(R.id.option2)
        val option3 = findViewById<LinearLayout>(R.id.option3)
        val option4 = findViewById<LinearLayout>(R.id.option4)
        val checks = listOf(findViewById<ImageView>(R.id.check1), findViewById(R.id.check2), findViewById(R.id.check3), findViewById(R.id.check4))
        val options = listOf(option1, option2, option3, option4)

        options.forEachIndexed { index, option ->
            option.setOnClickListener {
                options.forEachIndexed { i, other ->
                    other.isSelected = false
                    checks[i].visibility = View.GONE
                }
                option.isSelected = true
                checks[index].visibility = View.VISIBLE
            }
        }

        // Lógica del botón Continuar
        btnNext.setOnClickListener {
            if (currentQuestion < 5) {
                currentQuestion++
                loadQuestion(currentQuestion, options, checks)
            } else {
                Toast.makeText(this, "¡Diagnóstico completado!", Toast.LENGTH_SHORT).show()
                // Aquí podrías navegar a la siguiente actividad
            }
        }
    }

    // 2. Función que cambia los textos
    private fun loadQuestion(n: Int, options: List<LinearLayout>, checks: List<ImageView>) {
        // Primero, limpiamos las selecciones anteriores al pasar de pregunta
        options.forEach { it.isSelected = false }
        checks.forEach { it.visibility = View.GONE }

        // Actualizamos textos según el número de pregunta
        tvCount.text = "Pregunta $n de 5"

        when (n) {
            2 -> {
                tvQuestion.text = "¿Sueles tener dolores de cabeza frecuentes?"
                tvOpt1.text = "Muy seguido"
                tvOpt2.text = "A veces"
                tvOpt3.text = "Rara vez"
                tvOpt4.text = "Nunca"
            }
            3 -> {
                tvQuestion.text = "¿Notas fragilidad en tus uñas?"
                tvOpt1.text = "Sí, siempre"
                tvOpt2.text = "Solo a veces"
                tvOpt3.text = "Casi nunca"
                tvOpt4.text = "Nunca"
            }
            4 -> {
                tvQuestion.text = "¿Te sientes mareado(a) al levantarte rápido?"
                tvOpt1.text = "Siempre me ocurre"
                tvOpt2.text = "Ocasionalmente"
                tvOpt3.text = "Casi nunca"
                tvOpt4.text = "Nunca"
            }
            // ... Agrega el 4 y el 5 aquí

        }
    }
}