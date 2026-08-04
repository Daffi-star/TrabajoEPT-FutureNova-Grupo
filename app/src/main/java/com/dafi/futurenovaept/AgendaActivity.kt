package com.dafi.futurenovaept

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar

class AgendaActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAgregar: Button
    private val listaAgenda = mutableListOf<AgendaItem>()
    private lateinit var adaptador: AgendaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_agenda)

        recyclerView = findViewById(R.id.recyclerAgenda)
        btnAgregar = findViewById(R.id.btnAgregarAgenda)

        recyclerView.layoutManager = LinearLayoutManager(this)
        cargarAgendaGuardada()

        adaptador = AgendaAdapter(
            listaAgenda,
            onCheckChanged = { _, _ -> guardarAgendaEnPrefs() },
            onEliminarClick = { item -> eliminarItem(item) }
        )
        recyclerView.adapter = adaptador

        btnAgregar.setOnClickListener {
            mostrarDialogoCrearTarea()
        }
    }

    private fun mostrarDialogoCrearTarea() {
        val context = this
        val layout = android.widget.LinearLayout(context).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(40, 30, 40, 10)
        }

        val inputTitulo = EditText(context).apply {
            hint = "Título (Ej. Leer ensayo de humanidades)"
        }

        val spinnerCategoria = Spinner(context).apply {
            val categorias = arrayOf("Estudio", "Examen", "Tarea", "Lectura", "Personal")
            adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, categorias)
            setPadding(0, 30, 0, 30)
        }

        layout.addView(inputTitulo)
        layout.addView(spinnerCategoria)

        AlertDialog.Builder(context)
            .setTitle("Nueva Tarea o Evento")
            .setView(layout)
            .setPositiveButton("Elegir Fecha y Hora") { _, _ ->
                val titulo = inputTitulo.text.toString().trim()
                val categoria = spinnerCategoria.selectedItem.toString()
                if (titulo.isNotEmpty()) {
                    abrirSelectorFechaHora(titulo, categoria)
                } else {
                    Toast.makeText(context, "Escribe un título válido", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun abrirSelectorFechaHora(titulo: String, categoria: String) {
        val calendario = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            TimePickerDialog(this, { _, hourOfDay, minute ->
                val formatoAMPM = if (hourOfDay >= 12) "PM" else "AM"
                val hora12 = if (hourOfDay % 12 == 0) 12 else hourOfDay % 12
                val minutoStr = if (minute < 10) "0$minute" else "$minute"
                val fechaHoraFinal = "$dayOfMonth/${month + 1}, $hora12:$minutoStr $formatoAMPM"

                val nuevoItem = AgendaItem(
                    id = System.currentTimeMillis().toString(),
                    titulo = titulo,
                    categoria = categoria,
                    fechaHora = fechaHoraFinal,
                    completada = false
                )

                listaAgenda.add(nuevoItem)
                guardarAgendaEnPrefs()
                adaptador.notifyDataSetChanged()
                Toast.makeText(this, "Agregado a la agenda con éxito", Toast.LENGTH_SHORT).show()

            }, calendario.get(Calendar.HOUR_OF_DAY), calendario.get(Calendar.MINUTE), false).show()
        }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun guardarAgendaEnPrefs() {
        val sharedPreferences = getSharedPreferences("MiAgendaPrefs", Context.MODE_PRIVATE)
        val jsonArray = JSONArray()
        for (item in listaAgenda) {
            val jsonObject = JSONObject().apply {
                put("id", item.id)
                put("titulo", item.titulo)
                put("categoria", item.categoria)
                put("fechaHora", item.fechaHora)
                put("completada", item.completada)
            }
            jsonArray.put(jsonObject)
        }
        sharedPreferences.edit().putString("lista_agenda", jsonArray.toString()).apply()
    }

    private fun cargarAgendaGuardada() {
        val sharedPreferences = getSharedPreferences("MiAgendaPrefs", Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("lista_agenda", null)
        if (jsonString != null) {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                listaAgenda.add(
                    AgendaItem(
                        id = obj.getString("id"),
                        titulo = obj.getString("titulo"),
                        categoria = obj.getString("categoria"),
                        fechaHora = obj.getString("fechaHora"),
                        completada = obj.getBoolean("completada")
                    )
                )
            }
        }
    }

    private fun eliminarItem(item: AgendaItem) {
        listaAgenda.remove(item)
        guardarAgendaEnPrefs()
        adaptador.notifyDataSetChanged()
        Toast.makeText(this, "Eliminado de la agenda", Toast.LENGTH_SHORT).show()
    }
}