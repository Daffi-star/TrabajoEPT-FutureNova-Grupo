package com.dafi.futurenovaept

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.dafi.futurenovaept.R
import com.dafi.futurenovaept.data.AppDatabase

class PerfilActivity : AppCompatActivity() {

    private lateinit var ivProfileImage: ImageView
    private lateinit var tvNombrePerfil: TextView
    private lateinit var btnEditarNombre: Button
    private lateinit var btnAcercaDe: Button

    // Contrato moderno para escoger la foto de la galería
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { selectedUri ->
            try {
                val savedPath = saveImageToInternalStorage(selectedUri)

                // Guardar ruta en SharedPreferences
                val prefs = getSharedPreferences("MiPerfilPrefs", Context.MODE_PRIVATE)
                prefs.edit().putString("ruta_foto_perfil", savedPath).apply()

                // Mostrar la imagen y quitar padding para que ocupe todo el espacio
                ivProfileImage.setImageURI(Uri.parse(savedPath))
                ivProfileImage.setPadding(0, 0, 0, 0)

                Toast.makeText(this, "¡Foto de perfil actualizada!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Error al guardar la imagen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_perfil) // Asegúrate de que coincida con el nombre de tu archivo xml

        // Vincular vistas
        ivProfileImage = findViewById(R.id.ivProfileImage)
        tvNombrePerfil = findViewById(R.id.tvNombrePerfil)
        btnEditarNombre = findViewById(R.id.btnEditarNombre)
        btnAcercaDe = findViewById(R.id.btnAcercaDe)

        // Cargar los datos guardados previamente (nombre y foto)
        cargarDatosPerfil()

        // Evento para abrir la galería al tocar la foto
        ivProfileImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        // Evento para editar el nombre con una ventana emergente
        btnEditarNombre.setOnClickListener {
            mostrarDialogoEditarNombre()
        }

        // Evento del botón Acerca de
        btnAcercaDe.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Acerca de NutriHierro")
                .setMessage("NutriHierro es una aplicación diseñada para el cuidado y control de anemia, ayudando a los estudiantes a llevar un registro saludable.")
                .setPositiveButton("Entendido", null)
                .show()
        }
    }

    override fun onResume() {
        super.onResume()
        cargarDatosPerfil()
        cargarResumenActividad() // ⬅️ Añadir aquí
    }

    private fun cargarResumenActividad() {
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            // Consultar los conteos de cada tabla
            val totalMetas = db.metaDao().getCount()
            val totalAgenda = db.agendaDao().getCount()
            val totalSuplementos = db.suplementoDao().getCount()

            // Asegúrate de tener los IDs correctos de los TextViews en tu XML de perfil
            // Ejemplo: tvMetasCount, tvAgendaCount, tvSuplementosCount
            findViewById<TextView>(R.id.tvStatMetas)?.text = totalMetas.toString()
            findViewById<TextView>(R.id.tvStatAgenda)?.text = totalAgenda.toString()
            findViewById<TextView>(R.id.tvStatSuplementos)?.text = totalSuplementos.toString()
        }
    }

    private fun cargarDatosPerfil() {
        val prefs = getSharedPreferences("MiPerfilPrefs", Context.MODE_PRIVATE)

        // Cargar Nombre
        val nombreGuardado = prefs.getString("nombre_usuario", "Estudiante")
        tvNombrePerfil.text = nombreGuardado

        // Cargar Foto
        val savedPath = prefs.getString("ruta_foto_perfil", null)
        if (savedPath != null) {
            val imgFile = File(savedPath)
            if (imgFile.exists()) {
                ivProfileImage.setImageURI(Uri.fromFile(imgFile))
                ivProfileImage.setPadding(0, 0, 0, 0)
            }
        }
    }

    private fun mostrarDialogoEditarNombre() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Nombre de Usuario")

        val input = EditText(this)
        input.hint = "Escribe tu nombre"
        builder.setView(input)

        builder.setPositiveButton("Guardar") { _, _ ->
            val nuevoNombre = input.text.toString().trim()
            if (nuevoNombre.isNotEmpty()) {
                tvNombrePerfil.text = nuevoNombre
                val prefs = getSharedPreferences("MiPerfilPrefs", Context.MODE_PRIVATE)
                prefs.edit().putString("nombre_usuario", nuevoNombre).apply()
                Toast.makeText(this, "¡Nombre actualizado con éxito!", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun saveImageToInternalStorage(uri: Uri): String {
        val inputStream = contentResolver.openInputStream(uri)
        val file = File(filesDir, "profile_picture.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        return file.absolutePath
    }
}