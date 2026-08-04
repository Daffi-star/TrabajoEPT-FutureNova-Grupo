package com.dafi.futurenovaept

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WaterStatsActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ocultar la barra superior (ActionBar) si lo deseas
        supportActionBar?.hide()

        setContentView(R.layout.activity_water_stats)

        barChart = findViewById(R.id.barChart)

        cargarDatosDesdeSharedPreferences()
    }

    private fun cargarDatosDesdeSharedPreferences() {
        val sharedPref = getSharedPreferences("MisDatosAgua", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPref.getString("listaHistorial", null)

        val type = object : TypeToken<ArrayList<RegistroAgua>>() {}.type
        val listaRegistros: ArrayList<RegistroAgua> = gson.fromJson(json, type) ?: ArrayList()

        android.util.Log.d("WaterStats", "Registros encontrados en Prefs: ${listaRegistros.size}")

        val entries = ArrayList<BarEntry>()

        // Convertimos cada registro guardado en una barra del gráfico
        listaRegistros.forEachIndexed { index, registro ->
            entries.add(BarEntry(index.toFloat(), registro.cantidad.toFloat()))
        }

        val dataSet = BarDataSet(entries, "Mililitros consumidos")
        dataSet.color = android.graphics.Color.parseColor("#2196F3") // Color azul para las barras

        val barData = BarData(dataSet)

        barChart.data = barData
        barChart.setFitBars(true)
        barChart.description.isEnabled = false
        barChart.invalidate() // Refresca y dibuja el gráfico con tus datos reales
    }
}