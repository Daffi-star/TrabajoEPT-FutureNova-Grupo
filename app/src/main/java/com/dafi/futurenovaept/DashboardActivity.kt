package com.dafi.futurenovaept

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.*
import android.view.View
import android.widget.LinearLayout
import com.dafi.futurenovaept.AgendaActivity

class DashboardActivity : AppCompatActivity() {

    private lateinit var tvSaludo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_dashboard)

        tvSaludo = findViewById(R.id.tvSaludo)

        // 1. Configuración del botón Diagnóstico
        // 1. Configuración dinámica del botón Diagnóstico
        val btnDiagnostico = findViewById<MaterialButton>(R.id.btnDiagnostico)

        // Consultamos si el usuario omitió o nunca hizo la encuesta previamente
        val prefsPerfil = getSharedPreferences("MiPerfilPrefs", Context.MODE_PRIVATE)
        val encuestaOmitidaOHecha = prefsPerfil.getBoolean("encuesta_omitida_o_hecha", false)

        if (encuestaOmitidaOHecha) {
            // Si la omitió, cambiamos el texto del botón para invitarlo a hacerla
            btnDiagnostico.text = "Realizar encuesta de diagnóstico"
            btnDiagnostico.setOnClickListener {
                val intent = Intent(this, DiagnosisActivity::class.java)
                startActivity(intent)
            }
        } else {
            // Si ya la completó de forma normal, muestra sus resultados
            btnDiagnostico.text = "Resultados del diagnóstico"
            btnDiagnostico.setOnClickListener {
                val intent = Intent(this, ResultadosActivity::class.java)
                startActivity(intent)
            }
        }

        val bottomNavigationView = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_agenda -> {
                    startActivity(Intent(this, AgendaActivity::class.java))
                    true
                }
                R.id.nav_metas -> {
                    startActivity(Intent(this, MetasActivity::class.java))
                    true
                }
                R.id.nav_perfil -> {
                    startActivity(Intent(this, PerfilActivity::class.java))
                    true
                }
                else -> false
            }
        }

        val layoutComida = findViewById<LinearLayout>(R.id.layoutComida)
        layoutComida.setOnClickListener {
            val intent = Intent(this, RecetasActivity::class.java)
            startActivity(intent)
        }

        val layoutSuplemento = findViewById<LinearLayout>(R.id.layoutSuplemento)
        layoutSuplemento.setOnClickListener {
            val intent = Intent(this, SuplementosActivity::class.java)
            startActivity(intent)
        }

        val btnMasInformacion = findViewById<View>(R.id.btnMasInformacion)
        btnMasInformacion.setOnClickListener {
            val intent = Intent(this, MasInformacionActivity::class.java)
            startActivity(intent)
        }

        // Botón Agua (El puente)
        val cardWater = findViewById<MaterialCardView>(R.id.cardWater)
        cardWater.setOnClickListener {
            val intent = Intent(this, AguaActivity::class.java)
            startActivity(intent)
        }

        // 2. Obtener acceso a las preferencias
        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

        // 3. Lógica de la racha
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val hoy = dateFormat.format(Date())
        val ultimaFecha = sharedPref.getString("ultima_fecha", "")
        var racha = sharedPref.getInt("racha", 0)

        if (hoy != ultimaFecha) {
            val ayer = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -1)
            val ayerString = ayer.format(cal.time)

            if (ultimaFecha == ayerString) {
                racha++
            } else if (ultimaFecha != hoy) {
                racha = 1
            }
            sharedPref.edit().putInt("racha", racha).putString("ultima_fecha", hoy).apply()
        }

        // 4. Actualizar la UI de la racha
        val tvRacha = findViewById<TextView>(R.id.tvRacha)
        tvRacha.text = "¡Racha de $racha días!"

        val progressBar = findViewById<ProgressBar>(R.id.progressBarRacha)
        progressBar.progress = (racha % 7) * 15

        // Consejos del día
        val tvConsejo = findViewById<TextView>(R.id.tvConsejo)
        val consejos = listOf(
            "Tomar agua antes de comer ayuda a mejorar tu digestión.",
            "Caminar 10 minutos después de comer regula tu azúcar.",
            "Un buen descanso es clave para la absorción de nutrientes.",
            "La constancia es más importante que la perfección.",
            "Recuerda hacer estiramientos si pasas mucho tiempo sentada."
        )

        val diaDelAnio = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        val indice = diaDelAnio % consejos.size
        tvConsejo.text = consejos[indice]
    }

    override fun onResume() {
        super.onResume()
        cargarAguaEnDashboard()
        actualizarNombreUsuario()
    }

    private fun actualizarNombreUsuario() {
        val prefsPerfil = getSharedPreferences("MiPerfilPrefs", Context.MODE_PRIVATE)
        val nombreGuardado = prefsPerfil.getString("nombre_usuario", "Dafne") ?: "Dafne"
        tvSaludo.text = "Hola, $nombreGuardado 👋"
    }

    private fun cargarAguaEnDashboard() {
        val sharedPref = getSharedPreferences("MisDatosAgua", MODE_PRIVATE)
        val totalAguaActual = sharedPref.getInt("totalAgua", 0)
        val metaDiaria = 2000

        val tvDashboardAgua = findViewById<TextView>(R.id.tvDashboardAgua)
        tvDashboardAgua.text = "$totalAguaActual / ${metaDiaria}ml"
    }


}