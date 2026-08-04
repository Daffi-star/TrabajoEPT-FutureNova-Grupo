package com.dafi.futurenovaept

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray

class PerfilActivity : AppCompatActivity() {

    private lateinit var tvNombre: TextView
    private lateinit var tvStatMetas: TextView
    private lateinit var tvStatAgenda: TextView
    private lateinit var tvStatSuplementos: TextView
    private lateinit var btnEditarNombre: Button
    private lateinit var btnAcercaDe: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_perfil)

        tvNombre = findViewById(R.id.tvNombrePerfil)
        tvStatMetas = findViewById(R.id.tvStatMetas)
        tvStatAgenda = findViewById(R.id.tvStatAgenda)
        tvStatSuplementos = findViewById(R.id.tvStatSuplementos)
        btnEditarNombre = findViewById(R.id.btnEditarNombre)
        btnAcercaDe = findViewById(R.id.btnAcercaDe)

        cargarDatosPerfil()

        btnEditarNombre.setOnClickListener {
            mostrarDialogoEditarNombre()
        }

        btnAcercaDe.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("FutureNova EPT")
                .setMessage("Aplicación de organización, salud y cuidado preventivo contra la anemia.\n\nVersión 1.0")
                .setPositiveButton("Cerrar", null)
                .show()
        }
    }

    private fun cargarDatosPerfil() {
        val prefsPerfil = getSharedPreferences("MiPerfilPrefs", Context.MODE_PRIVATE)
        val nombreGuardado = prefsPerfil.getString("nombre_usuario", "Paciente / Estudiante")
        tvNombre.text = nombreGuardado

        val prefsSuplementos = getSharedPreferences("MisSuplementosPrefs", Context.MODE_PRIVATE)
        val jsonSuplementos = prefsSuplementos.getString("lista_alarmas", null)
        val totalSuplementos = if (jsonSuplementos != null) JSONArray(jsonSuplementos).length() else 0
        tvStatSuplementos.text = totalSuplementos.toString()

        val prefsMetas = getSharedPreferences("MisMetasPrefs", Context.MODE_PRIVATE)
        val jsonMetas = prefsMetas.getString("lista_metas", null)
        tvStatMetas.text = if (jsonMetas != null) JSONArray(jsonMetas).length().toString() else "0"

        val prefsAgenda = getSharedPreferences("MiAgendaPrefs", Context.MODE_PRIVATE)
        val jsonAgenda = prefsAgenda.getString("lista_agenda", null)
        tvStatAgenda.text = if (jsonAgenda != null) JSONArray(jsonAgenda).length().toString() else "0"
    }

    private fun mostrarDialogoEditarNombre() {
        val input = EditText(this).apply {
            hint = "Escribe tu nombre"
            setPadding(40, 30, 40, 30)
        }

        AlertDialog.Builder(this)
            .setTitle("Cambiar Nombre")
            .setView(input)
            .setPositiveButton("Guardar") { _, _ ->
                val nuevoNombre = input.text.toString().trim()
                if (nuevoNombre.isNotEmpty()) {
                    val prefs = getSharedPreferences("MiPerfilPrefs", Context.MODE_PRIVATE)
                    prefs.edit().putString("nombre_usuario", nuevoNombre).apply()
                    tvNombre.text = nuevoNombre
                    Toast.makeText(this, "Nombre actualizado", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}