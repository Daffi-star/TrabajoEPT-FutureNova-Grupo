package com.dafi.futurenovaept

import android.app.AlarmManager
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar

class SuplementosActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAgregar: Button
    private val listaSuplementos = mutableListOf<SuplementoAlarma>()
    private lateinit var adaptador: SuplementosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_suplementos)

        recyclerView = findViewById(R.id.recyclerSuplementos)
        btnAgregar = findViewById(R.id.btnAgregarAlarma)

        recyclerView.layoutManager = LinearLayoutManager(this)
        cargarAlarmasGuardadas()

        adaptador = SuplementosAdapter(listaSuplementos) { alarma ->
            eliminarAlarma(alarma)
        }
        recyclerView.adapter = adaptador

        btnAgregar.setOnClickListener {
            mostrarDialogoCrearAlarma()
        }
    }

    private fun mostrarDialogoCrearAlarma() {
        val inputNombre = EditText(this).apply {
            hint = "Ej. Sulfato Ferroso / Ácido Fólico"
            setPadding(40, 30, 40, 30)
        }

        val container = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(40, 20, 40, 0)
            addView(inputNombre)
        }

        AlertDialog.Builder(this)
            .setTitle("Nuevo Suplemento")
            .setMessage("Ingresa el nombre del suplemento:")
            .setView(container)
            .setPositiveButton("Elegir Hora") { _, _ ->
                val nombre = inputNombre.text.toString().trim()
                if (nombre.isNotEmpty()) {
                    abrirSelectorDeHora(nombre)
                } else {
                    Toast.makeText(this, "Escribe un nombre válido", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun abrirSelectorDeHora(nombreSuplemento: String) {
        val calendario = Calendar.getInstance()
        val horaActual = calendario.get(Calendar.HOUR_OF_DAY)
        val minutoActual = calendario.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, hourOfDay, minute ->
            val formatoAMPM = if (hourOfDay >= 12) "PM" else "AM"
            val hora12 = if (hourOfDay % 12 == 0) 12 else hourOfDay % 12
            val minutoStr = if (minute < 10) "0$minute" else "$minute"
            val horaFinal = "$hora12:$minutoStr $formatoAMPM"

            val idUnico = System.currentTimeMillis().toString()

            val nuevaAlarma = SuplementoAlarma(
                id = idUnico,
                nombre = nombreSuplemento,
                hora = horaFinal
            )

            // 1. Programar la alarma física en el sistema Android
            programarAlarmaFisica(idUnico, nombreSuplemento, hourOfDay, minute)

            // 2. Guardar en lista y preferencias
            listaSuplementos.add(nuevaAlarma)
            guardarAlarmasEnPrefs()
            adaptador.notifyDataSetChanged()
            Toast.makeText(this, "Alarma programada con éxito", Toast.LENGTH_SHORT).show()

        }, horaActual, minutoActual, false).show()
    }

    private fun programarAlarmaFisica(idStr: String, nombre: String, hourOfDay: Int, minute: Int) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, SuplementoReceiver::class.java).apply {
            putExtra("EXTRA_NOMBRE", nombre)
        }

        val requestCode = idStr.hashCode()
        val pendingIntent = android.app.PendingIntent.getBroadcast(
            this,
            requestCode,
            intent,
            android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } catch (e: SecurityException) {
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }

    private fun guardarAlarmasEnPrefs() {
        val sharedPreferences = getSharedPreferences("MisSuplementosPrefs", Context.MODE_PRIVATE)
        val jsonArray = JSONArray()
        for (item in listaSuplementos) {
            val jsonObject = JSONObject().apply {
                put("id", item.id)
                put("nombre", item.nombre)
                put("hora", item.hora)
            }
            jsonArray.put(jsonObject)
        }
        sharedPreferences.edit().putString("lista_alarmas", jsonArray.toString()).apply()
    }

    private fun cargarAlarmasGuardadas() {
        val sharedPreferences = getSharedPreferences("MisSuplementosPrefs", Context.MODE_PRIVATE)
        val jsonString = sharedPreferences.getString("lista_alarmas", null)
        if (jsonString != null) {
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                listaSuplementos.add(
                    SuplementoAlarma(
                        id = obj.getString("id"),
                        nombre = obj.getString("nombre"),
                        hora = obj.getString("hora")
                    )
                )
            }
        }
    }

    private fun eliminarAlarma(alarma: SuplementoAlarma) {
        listaSuplementos.remove(alarma)
        guardarAlarmasEnPrefs()
        adaptador.notifyDataSetChanged()
        Toast.makeText(this, "Alarma eliminada", Toast.LENGTH_SHORT).show()
    }
}