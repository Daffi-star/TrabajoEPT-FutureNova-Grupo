package com.dafi.futurenovaept

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.dafi.futurenovaept.data.AppDatabase
import com.dafi.futurenovaept.data.DiagnosisRecord
import kotlinx.coroutines.launch

class DiagnosisActivity : AppCompatActivity() {

    private var currentQuestion = 1
    private val answers = mutableListOf<String>()

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
        tvOpt1 = findViewById(R.id.tvOption1)
        tvOpt2 = findViewById(R.id.tvOption2)
        tvOpt3 = findViewById(R.id.tvOption3)
        tvOpt4 = findViewById(R.id.tvOption4)

        val btnSkip = findViewById<Button>(R.id.btnDiagnosisSkip)
        val btnNext = findViewById<Button>(R.id.btnDiagnosisNext)

        val options = listOf(
            findViewById<LinearLayout>(R.id.option1),
            findViewById(R.id.option2),
            findViewById(R.id.option3),
            findViewById(R.id.option4)
        )

        val checks = listOf<ImageView>(
            findViewById(R.id.check1),
            findViewById(R.id.check2),
            findViewById(R.id.check3),
            findViewById(R.id.check4)
        )

        btnSkip.setOnClickListener {
            Toast.makeText(this, "Función pronto disponible", Toast.LENGTH_SHORT).show()
        }

        // Configurar clics de selección
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
            val selectedOption = options.find { it.isSelected }

            if (selectedOption != null) {
                // Guardar respuesta actual
                val answerText = when (selectedOption) {
                    options[0] -> tvOpt1.text.toString()
                    options[1] -> tvOpt2.text.toString()
                    options[2] -> tvOpt3.text.toString()
                    else -> tvOpt4.text.toString()
                }
                answers.add(answerText)

                if (currentQuestion < 5) {
                    currentQuestion++
                    loadQuestion(currentQuestion, options, checks)
                    updateProgressBar(currentQuestion, 5)
                } else {
                    saveDiagnosisToDatabase()
                }
            } else {
                Toast.makeText(this, "Por favor, selecciona una opción", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun calcularRiesgo(respuestas: List<String>): String {
        var puntos = 0
        respuestas.forEach { r ->
            if (r.contains("Muy seguido") || r.contains("Sí, siempre") || r.contains("Siempre me ocurre")) {
                puntos += 2
            } else if (r.contains("A veces") || r.contains("Ocasionalmente")) {
                puntos += 1
            }
        }
        return when {
            puntos >= 4 -> "Alto"
            puntos >= 2 -> "Moderado"
            else -> "Bajo"
        }
    }

    private fun saveDiagnosisToDatabase() {
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val nuevoRegistro = DiagnosisRecord(
                date = System.currentTimeMillis(),
                nivelRiesgo = calcularRiesgo(answers),
                respuestasRaw = answers.joinToString(" | "),
                observacion = "Encuesta completada"
            )

            db.diagnosisDao().insert(nuevoRegistro)

            // CORRECCIÓN AQUÍ: Ahora apunta a LoadingActivity
            val intent = Intent(this@DiagnosisActivity, LoadingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

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
            5 -> {
                tvQuestion.text = "¿Sientes las manos o los pies fríos todo el tiempo?"
                tvOpt1.text = "Siempre"
                tvOpt2.text = "Ocasionalmente"
                tvOpt3.text = "Casi nunca"
                tvOpt4.text = "Nunca"
        }
        }
    }

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