package com.dafi.futurenovaept

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dafi.futurenovaept.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmActivity : AppCompatActivity(), OnAlarmUpdatedListener {
    private val db by lazy { AppDatabase.getDatabase(this) }
    private lateinit var adapter: AlarmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_alarm)

        val rvAlarmas = findViewById<RecyclerView>(R.id.rvAlarmas)
        rvAlarmas.layoutManager = LinearLayoutManager(this)

        adapter = AlarmAdapter { alarmaSeleccionada ->
            val bottomSheet = EditAlarmBottomSheet(alarmaSeleccionada, this)
            bottomSheet.show(supportFragmentManager, "EditAlarm")
        }
        rvAlarmas.adapter = adapter

        // --- NUEVA LÓGICA DE AUTO-INSERCIÓN ---
        lifecycleScope.launch(Dispatchers.IO) {
            val dao = db.alarmDao()
            // Si la tabla está vacía, insertamos datos
            if (dao.getAllAlarmasList().isEmpty()) { // Necesitas esta función abajo
                val alarmasPredeterminadas = listOf(
                    Alarma(nombre = "Despertador", hora = "07:00", activo = true, diasRepeticion = booleanArrayOf(true, true, true, true, true, false, false)),
                    Alarma(nombre = "Antes Desayuno", hora = "07:30", activo = true, diasRepeticion = booleanArrayOf(true, true, true, true, true, true, true)),
                    Alarma(nombre = "Después Desayuno", hora = "08:30", activo = true, diasRepeticion = booleanArrayOf(true, true, true, true, true, true, true)),
                    Alarma(nombre = "Antes Comida", hora = "13:00", activo = true, diasRepeticion = booleanArrayOf(true, true, true, true, true, true, true)),
                    Alarma(nombre = "Después Comida", hora = "14:00", activo = true, diasRepeticion = booleanArrayOf(true, true, true, true, true, true, true)),
                    Alarma(nombre = "Antes Cena", hora = "19:00", activo = true, diasRepeticion = booleanArrayOf(true, true, true, true, true, true, true)),
                    Alarma(nombre = "Después Cena", hora = "20:00", activo = true, diasRepeticion = booleanArrayOf(true, true, true, true, true, true, true))
                )
                alarmasPredeterminadas.forEach { dao.insert(it) }
            }
        }
        // --------------------------------------

        lifecycleScope.launch {
            db.alarmDao().getAllAlarmas().collect { listaDeAlarmas ->
                adapter.alarmas = listaDeAlarmas.toMutableList()
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onAlarmUpdated(alarmaActualizada: Alarma) {
        lifecycleScope.launch(Dispatchers.IO) {
            db.alarmDao().update(alarmaActualizada)
        }
    }
}