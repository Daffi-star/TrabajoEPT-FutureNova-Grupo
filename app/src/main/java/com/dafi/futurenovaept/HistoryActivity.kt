package com.dafi.futurenovaept

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_history)

        val btnBackHistory = findViewById<ImageView>(R.id.btnBackHistory)
        btnBackHistory.setOnClickListener { finish() }

        // Datos de prueba
        val sharedPref = getSharedPreferences("MisDatosAgua", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPref.getString("listaHistorial", null)
        val type = object : TypeToken<ArrayList<RegistroAgua>>() {}.type
        val listaReal: ArrayList<RegistroAgua> = gson.fromJson(json, type) ?: ArrayList()


        // IMPORTANTE: Asegúrate de que en activity_history.xml,
        // tu RecyclerView tenga el ID: android:id="@+id/rvHistorial"
        val rvHistorial = findViewById<RecyclerView>(R.id.tvHistorial)
        rvHistorial.layoutManager = LinearLayoutManager(this)
        rvHistorial.adapter = HistorialAdapter(listaReal.reversed()) // .reversed() pone lo último al principio
    }
}