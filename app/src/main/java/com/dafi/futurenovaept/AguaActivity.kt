package com.dafi.futurenovaept

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AguaActivity : AppCompatActivity() {

    private var totalAgua = 0
    private var cantidadPreferida = 250
    private var metaDiaria = 2000
    private var ultimaBebida = 0
    private var numeroTazas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agua)

        // 1. CARGAR DATOS: Leemos lo que guardamos anteriormente
        val sharedPref = getSharedPreferences("MisDatosAgua", MODE_PRIVATE)
        totalAgua = sharedPref.getInt("totalAgua", 0)
        numeroTazas = sharedPref.getInt("numeroTazas", 0)
        ultimaBebida = sharedPref.getInt("ultimaBebida", 0)

        // Vincular vistas
        val btnAdd = findViewById<ImageButton>(R.id.btnAdd)
        val btnMinus = findViewById<ImageButton>(R.id.btnMinus)
        val btnDrink = findViewById<ImageButton>(R.id.btnDrink)
        val tvTotalMl = findViewById<TextView>(R.id.tvTotalMl)
        val tvMeta = findViewById<TextView>(R.id.tvMeta)
        val tvUltimaBebida = findViewById<TextView>(R.id.tvUltimaBebida)
        val tvNumeroTazas = findViewById<TextView>(R.id.tvNumeroTazas)
        val btnHistory = findViewById<ImageView>(R.id.btnHistory)
        val btnAlarms = findViewById<ImageView>(R.id.btnClock)

        // Función que actualiza la pantalla Y GUARDA los datos automáticamente
        fun actualizarUI() {
            tvTotalMl.text = "$totalAgua ml"
            tvMeta.text = "$metaDiaria ml"
            tvUltimaBebida.text = "$ultimaBebida ml"
            tvNumeroTazas.text = "$numeroTazas Tazas"

            // Llamamos a la función de guardado cada vez que algo cambia
            guardarDatos()
        }

        // Lógica de botones
        btnAdd.setOnClickListener {
            totalAgua += cantidadPreferida
            ultimaBebida = cantidadPreferida
            numeroTazas += 1
            actualizarUI()
        }

        btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        btnMinus.setOnClickListener {
            if (totalAgua >= cantidadPreferida) {
                totalAgua -= cantidadPreferida
                if (numeroTazas > 0) numeroTazas -= 1
            } else {
                totalAgua = 0
                numeroTazas = 0
            }
            actualizarUI()
        }

        // En tu AguaActivity.kt // O el ID que tengas
        btnAlarms.setOnClickListener {
            val intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
        }

        btnDrink.setOnClickListener {
            val bottomSheet = BottomSheetAgua()
            bottomSheet.listener = object : BottomSheetAgua.OnAguaSelectedListener {
                override fun onAguaSelected(cantidad: Int) {
                    cantidadPreferida = cantidad
                    totalAgua += cantidad
                    ultimaBebida = cantidad
                    numeroTazas += 1
                    actualizarUI()

                    // 1. Obtener fecha actual
                    val fechaActual = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

                    // 2. Cargar lista existente
                    val sharedPref = getSharedPreferences("MisDatosAgua", MODE_PRIVATE)
                    val gson = Gson()
                    val json = sharedPref.getString("listaHistorial", null)
                    val type = object : TypeToken<ArrayList<RegistroAgua>>() {}.type
                    val lista: ArrayList<RegistroAgua> = gson.fromJson(json, type) ?: ArrayList()

                    // 3. Agregar nuevo registro y guardar
                    lista.add(RegistroAgua(cantidad, fechaActual))
                    val editor = sharedPref.edit()
                    editor.putString("listaHistorial", gson.toJson(lista))
                    editor.apply()
                }
            }
            bottomSheet.show(supportFragmentManager, "AguaBottomSheet")
        }

        // Mostrar valores al iniciar (esto leerá los datos cargados arriba)
        actualizarUI()
    }

    // Función para guardar en el celular
    private fun guardarDatos() {
        val sharedPref = getSharedPreferences("MisDatosAgua", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("totalAgua", totalAgua)
        editor.putInt("numeroTazas", numeroTazas)
        editor.putInt("ultimaBebida", ultimaBebida)
        editor.apply()
    }
}