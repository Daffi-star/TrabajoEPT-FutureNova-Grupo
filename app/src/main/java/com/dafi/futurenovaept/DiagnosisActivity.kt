package com.dafi.futurenovaept

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DiagnosisActivity : AppCompatActivity() {

    private var currentQuestion = 1

    private lateinit var tvQuestion: TextView
    private lateinit var tvCount: TextView
    private lateinit var tvOpt1: TextView
    private lateinit var tvOpt2: TextView
    private lateinit var tvOpt3: TextView
    private lateinit var tvOpt4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnosis)

        // 1. Inicializar vistas básicas
        tvQuestion = findViewById(R.id.tvQuestionText)
        tvCount = findViewById(R.id.tvQuestionCount)
        tvOpt1 = findViewById(R.id.tvOption1)
        tvOpt2 = findViewById(R.id.tvOption2)
        tvOpt3 = findViewById(R.id.tvOption3)
        tvOpt4 = findViewById(R.id.tvOption4)

        // 2. Lógica del botón Saltar
        val btnSkip = findViewById<Button>(R.id.btnDiagnosisSkip)
        btnSkip.setOnClickListener {
            Toast.makeText(this, "Esta función estará disponible pronto", Toast.LENGTH_SHORT).show()
        }

        // 3. Inicializar opciones y checks
        val btnNext = findViewById<Button>(R.id.btnDiagnosisNext)
        val option1 = findViewById<LinearLayout>(R.id.option1)
        val option2 = findViewById<LinearLayout>(R.id.option2)
        val option3 = findViewById<LinearLayout>(R.id.option3)
        val option4 = findViewById<LinearLayout>(R.id.option4)

        val checks = listOf(
            findViewById<ImageView>(R.id.check1),
            findViewById(R.id.check2),
            findViewById(R.id.check3),
            findViewById(R.id.check4)
        )
        val options = listOf(option1, option2, option3, option4)

        // 4. Configurar clics de selección
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

        // 5. Lógica del botón Continuar
        btnNext.setOnClickListener {
            if (currentQuestion < 5) {
                currentQuestion++
                loadQuestion(currentQuestion, options, checks)
                // Llamamos a la barra para que se actualice
                updateProgressBar(currentQuestion, 5)
            } else {
                Toast.makeText(this, "¡Diagnóstico completado!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 6. Función para cambiar textos
    private fun loadQuestion(n: Int, options: List<LinearLayout>, checks: List<ImageView>) {
        options.forEach { it.isSelected = false }
        checks.forEach { it.visibility = View.GONE }

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
        }
    }

    // 7. Función para actualizar la barra (Ahora está DENTRO de la clase)
    private fun updateProgressBar(currentQuestion: Int, totalQuestions: Int) {
        val progressContainer = findViewById<FrameLayout>(R.id.flProgressContainer)
        val progressBar = findViewById<View>(R.id.viewProgressFill)

        val percentage = currentQuestion.toFloat() / totalQuestions.toFloat()

        progressContainer.post {
            val containerWidth = progressContainer.width
            val newWidth = (containerWidth * percentage).toInt()

            val params = progressBar.layoutParams
            params.width = newWidth
            progressBar.layoutParams = params
        }
    }
}