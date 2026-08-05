package com.dafi.futurenovaept

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.dafi.futurenovaept.data.AppDatabase
import com.dafi.futurenovaept.data.MetaEntity
import org.json.JSONArray
import org.json.JSONObject

class MetasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAgregar: Button
    private lateinit var progressBar: ProgressBar
    private val listaMetas = mutableListOf<MetaItem>()
    private lateinit var adaptador: MetasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_metas)

        recyclerView = findViewById(R.id.recyclerMetas)
        btnAgregar = findViewById(R.id.btnAgregarMeta)
        progressBar = findViewById(R.id.progressBarMetas)

        recyclerView.layoutManager = LinearLayoutManager(this)
        cargarMetasGuardadas()

        adaptador = MetasAdapter(
            listaMetas,
            onCheckChanged = { _, _ ->
                guardarMetasEnPrefs()
                actualizarProgreso()
            },
            onEliminarClick = { item -> eliminarMeta(item) }
        )
        recyclerView.adapter = adaptador
        actualizarProgreso()

        btnAgregar.setOnClickListener {
            mostrarDialogoCrearMeta()
        }
    }

    private fun mostrarDialogoCrearMeta() {
        val context = this
        val layout = android.widget.LinearLayout(context).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(40, 30, 40, 10)
        }

        val inputTitulo = EditText(context).apply {
            hint = "Título de la meta (Ej. Ingresar a la Universidad)"
        }

        val inputDesc = EditText(context).apply {
            hint = "Descripción breve (Ej. Estudiar 2 horas diarias)"
            setPadding(0, 20, 0, 0)
        }

        layout.addView(inputTitulo)
        layout.addView(inputDesc)

        AlertDialog.Builder(context)
            .setTitle("Nueva Meta")
            .setView(layout)
            .setPositiveButton("Guardar") { _, _ ->
                val titulo = inputTitulo.text.toString().trim()
                val desc = inputDesc.text.toString().trim()
                if (titulo.isNotEmpty()) {
                    val nuevaMeta = MetaItem(
                        id = System.currentTimeMillis().toString(),
                        titulo = titulo,
                        descripcion = desc,
                        completada = false
                    )
                    listaMetas.add(nuevaMeta)
                    guardarMetasEnPrefs()

                    // ⬅️ Guardar también en la base de datos de Room para el perfil
                    lifecycleScope.launch {
                        val db = AppDatabase.getDatabase(context)
                        db.metaDao().insertMeta(
                            MetaEntity(id = nuevaMeta.id.toLongOrNull() ?: 0L, titulo = titulo)
                        )
                    }

                    adaptador.notifyDataSetChanged()
                    actualizarProgreso()
                    Toast.makeText(context, "Meta creada con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Escribe un título válido", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun actualizarProgreso() {
        if (listaMetas.isEmpty()) {
            progressBar.progress = 0
            return
        }
        val completadas = listaMetas.count { it.completada }
        val porcentaje = (completadas * 100) / listaMetas.size
        progressBar.progress = porcentaje
    }

    private fun guardarMetasEnPrefs() {
        val sharedPreferences = getSharedPreferences("MisMetasPrefs", Context.MODE_PRIVATE)
        val jsonArray = JSONArray()
        for (item in listaMetas) {
            val jsonObject = JSONObject().apply {
                put("id", item.id)
                put("titulo", item.titulo)
                put("descripcion", item.descripcion)
                put("completada", item.completada)
            }
            jsonArray.put(jsonObject)
        }
        sharedPreferences.edit().putString("lista_metas", jsonArray.toString()).apply()
    }

    private fun cargarMetasGuardadas() {
        val sharedPreferences = getSharedPreferences("MisMetasPrefs", Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("lista_metas", null)
        if (jsonString != null) {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                listaMetas.add(
                    MetaItem(
                        id = obj.getString("id"),
                        titulo = obj.getString("titulo"),
                        descripcion = obj.getString("descripcion"),
                        completada = obj.getBoolean("completada")
                    )
                )
            }
        }
    }

    private fun eliminarMeta(item: MetaItem) {
        listaMetas.remove(item)
        guardarMetasEnPrefs()

        // ⬅️ Borrar también de la base de datos de Room
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(this@MetasActivity)
            db.metaDao().deleteMetaById(item.id)
        }

        adaptador.notifyDataSetChanged()
        actualizarProgreso()
        Toast.makeText(this, "Meta eliminada", Toast.LENGTH_SHORT).show()
    }
}