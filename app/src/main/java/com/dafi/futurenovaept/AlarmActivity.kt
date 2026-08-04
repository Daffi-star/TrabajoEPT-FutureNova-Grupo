package com.dafi.futurenovaept

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dafi.futurenovaept.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.provider.Settings
import android.net.Uri

class AlarmActivity : AppCompatActivity(), OnAlarmUpdatedListener {
    private val db by lazy { AppDatabase.getDatabase(this) }
    private lateinit var adapter: AlarmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_alarm)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 100)
            }
        }

        val rvAlarmas = findViewById<RecyclerView>(R.id.rvAlarmas)
        rvAlarmas.layoutManager = LinearLayoutManager(this)

        adapter = AlarmAdapter(
            alarmas = emptyList(),
            onItemClick = { alarmaSeleccionada ->
                val bottomSheet = EditAlarmBottomSheet(alarmaSeleccionada, this)
                bottomSheet.show(supportFragmentManager, "EditAlarm")
            },
            onSwitchToggled = { alarmaActualizada ->
                // 1. Guardamos en la base de datos
                lifecycleScope.launch(Dispatchers.IO) {
                    db.alarmDao().update(alarmaActualizada)
                }

                // 2. Programamos o cancelamos usando this@AlarmActivity explícitamente
                if (alarmaActualizada.activo) {
                    programarAlarmaEnSistema(this@AlarmActivity, alarmaActualizada) // <--- CAMBIO AQUÍ
                } else {
                    cancelarAlarmaDelSistema(this@AlarmActivity, alarmaActualizada) // <--- CAMBIO AQUÍ
                }
            }
        )
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

fun programarAlarmaEnSistema(context: Context, alarma: Alarma) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // 1. Verificamos si estamos en Android 12+ (API 31) o superior (como Android 15)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (!alarmManager.canScheduleExactAlarms()) {
            // Si no tiene el permiso, mandamos al usuario a los Ajustes del sistema
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = Uri.parse("package:${context.packageName}")
            }
            context.startActivity(intent)
            return // Salimos para evitar el crasheo o fallo silencioso
        }
    }

    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("ALARM_NAME", alarma.nombre)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        alarma.id,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val partes = alarma.hora.split(":")
    val hora = partes[0].toInt()
    val minuto = partes[1].toInt()

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hora)
        set(Calendar.MINUTE, minuto)
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
        e.printStackTrace()
    }
}

fun cancelarAlarmaDelSistema(context: Context, alarma: Alarma) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        alarma.id,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    alarmManager.cancel(pendingIntent)
}