package com.dafi.futurenovaept

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Configurar Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("517625011154-p4kc1dprfbdh0ecm9nfp85rmh1qdbmmt.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Tus elementos existentes
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Nuevo botón de Google (Asegúrate de agregarlo en tu activity_main.xml con este ID)
        val btnGoogleLogin = findViewById<Button>(R.id.btnGoogle)

        // Enlaces de la parte inferior del Login según tu XML
        val tvForgot = findViewById<TextView>(R.id.tvForgot)
        val tvSignUp = findViewById<TextView>(R.id.tvSignUp)

        // 1. Lógica para recuperar contraseña
        tvForgot.setOnClickListener {
            mostrarDialogoOlvideContrasena()
        }

        // 2. Lógica para registrarse rápidamente desde la misma pantalla
        tvSignUp.setOnClickListener {
            mostrarDialogoRegistro()
        }

        // Botón de Login normal (Correo / Contraseña)
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty()) {
                etEmail.error = "Ingresa tu usuario o email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                etPassword.error = "Ingresa tu contraseña"
                return@setOnClickListener
            }

            // Opcional: Si escriben un nombre en el correo, podemos guardarlo temporalmente como perfil
            val prefs = getSharedPreferences("MiPerfilPrefs", Context.MODE_PRIVATE)
            prefs.edit().putString("nombre_usuario", email.substringBefore("@")).apply()

            val intent = Intent(this, DiagnosisActivity::class.java)
            startActivity(intent)
        }

        // Botón de Google Login
        btnGoogleLogin.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account?.idToken)
            } catch (e: ApiException) {
                Toast.makeText(this, "Error de autenticación con Google: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val nombreGoogle = user?.displayName ?: "Estudiante"

                    // Guardamos automáticamente el nombre real de Google en el perfil de la app
                    val prefs = getSharedPreferences("MiPerfilPrefs", Context.MODE_PRIVATE)
                    prefs.edit().putString("nombre_usuario", nombreGoogle).apply()

                    Toast.makeText(this, "¡Bienvenida, $nombreGoogle!", Toast.LENGTH_SHORT).show()

                    // Redirigir a la siguiente pantalla (DiagnosisActivity)
                    val intent = Intent(this, DiagnosisActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Fallo al iniciar sesión con Google en Firebase", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun mostrarDialogoOlvideContrasena() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Recuperar contraseña")

        val input = EditText(this)
        input.hint = "Ingresa tu correo electrónico"
        builder.setView(input)

        builder.setPositiveButton("Enviar") { _, _ ->
            val email = input.text.toString().trim()
            if (email.isNotEmpty()) {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Correo de recuperación enviado. Revisa tu bandeja.", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Por favor, escribe un correo válido.", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun mostrarDialogoRegistro() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Crear Cuenta Nueva")

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
        }

        val inputEmail = EditText(this).apply {
            hint = "Correo electrónico"
            inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
        val inputPass = EditText(this).apply {
            hint = "Contraseña (mínimo 6 caracteres)"
            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        layout.addView(inputEmail)
        layout.addView(inputPass)
        builder.setView(layout)

        builder.setPositiveButton("Registrarse") { _, _ ->
            val email = inputEmail.text.toString().trim()
            val password = inputPass.text.toString().trim()

            if (email.isNotEmpty() && password.length >= 6) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "¡Cuenta creada e iniciada con éxito!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, DiagnosisActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Error al registrarse: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Verifica el correo y usa una contraseña de al menos 6 caracteres.", Toast.LENGTH_LONG).show()
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }
}